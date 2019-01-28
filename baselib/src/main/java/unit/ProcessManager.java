package unit;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import aidl.ProcessInterface;
import annotion.ClassId;
import bean.RequestBean;
import bean.RequestParameter;
import servicee.MyService;

/**
 * create by lei on 2019/1/28/028
 * desc:
 */
public class ProcessManager {
    Gson gson = new Gson();
    CacheCenter cacheCenter = CacheCenter.getInstance();
    private static final ProcessManager ourInstance = new ProcessManager();

    public static ProcessManager getInstance() {
        return ourInstance;
    }

    private ProcessManager() {
    }

//    主进程注册

    public void register(Class<?> clazz){
        cacheCenter.register(clazz);
    }




//    子进程访问
    ProcessInterface processInterface;

    public <T> T getObjectInstance(Class<?> clazz,Object... paramaters){
            sendReq(MyService.GET_INSTANCE,clazz,null,paramaters);
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new ProcessInvokeHander(clazz));
        return proxy;
    }

    public String sendReq(int type, Class<?> clazz, Method method,Object[] parameters){
        RequestParameter[] requestParameters = null;
        if (parameters != null && parameters.length > 0 ){
            requestParameters = new RequestParameter[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                Object parameter = parameters[i];
                String  parameterClassName = parameter.getClass().getName();
                String  parameterValue = gson.toJson(parameter);
                RequestParameter requestParameter = new RequestParameter(parameterClassName,parameterValue);
                requestParameters[i] = requestParameter;
            }

            String className = clazz.getAnnotation((ClassId.class)).value();
            String methodName = method == null ? "" : method.getName();
            RequestBean requestBean = new RequestBean(type,className,methodName,requestParameters);

            String request = gson.toJson(requestBean);
            try {
                return processInterface.send(request);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void connect(Context context){
        bind(context,"",MyService.class);
    }

    public void connect(Context context,String packName){
        bind(context,packName,MyService.class);
    }

    public void bind(Context context, String packName, Class<? extends MyService> clazz){

        Intent intent;
        if (TextUtils.isEmpty(packName)){
            intent = new Intent(context,clazz);
        }else {
            //多app通信
            intent = new Intent();
            intent.setPackage(packName);
            intent.setAction(clazz.getName());
        }

        context.bindService(intent,new ProcessConnection(),Context.BIND_AUTO_CREATE);
    }


    private class ProcessConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            processInterface = ProcessInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}

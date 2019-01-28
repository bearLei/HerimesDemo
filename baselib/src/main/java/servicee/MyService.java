package servicee;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import aidl.ProcessInterface;
import bean.RequestBean;
import bean.RequestParameter;
import unit.CacheCenter;

/**
 * create by lei on 2019/1/28/028
 * desc：处理中心
 */
public class MyService extends Service {

    public static final int GET_INSTANCE = 1;

    public static final int GET_METHOD = 2;


    Gson gson = new Gson();

    @Override
    public IBinder onBind(final Intent intent) {
       return new ProcessInterface.Stub() {
           @Override
           public String send(String request) throws RemoteException {
               Log.d("lei", "发送请求---》" + request);
               RequestBean requestBean = gson.fromJson(request, RequestBean.class);
               switch (requestBean.getType()) {
                   case GET_INSTANCE:
                       Method method = CacheCenter.getInstance().getMethod(requestBean.getClassName(), "getObjectInstance");
                       //反射
                       Object[] objects = makeParameterObject(requestBean);
                       try {
                           Object invoke = method.invoke(null, objects);
                           CacheCenter.getInstance().putObject(requestBean.getClassName(), invoke);
                       } catch (IllegalAccessException e) {
                           e.printStackTrace();
                       } catch (InvocationTargetException e) {
                           e.printStackTrace();
                       }
                       break;
                   case GET_METHOD:
                       Object userManager = CacheCenter.getInstance().getObject(requestBean.getClassName());
                       Method getPerson = CacheCenter.getInstance().getMethod(requestBean.getClassName(), requestBean.getMethodName());
                       Object[] parameter = makeParameterObject(requestBean);
                       try {
                           Object person = getPerson.invoke(userManager, parameter);
                           String data = gson.toJson(person);
                           return data;
                       } catch (IllegalAccessException e) {
                           e.printStackTrace();
                       } catch (InvocationTargetException e) {
                           e.printStackTrace();
                       }
                       break;
               }
               return null;
           }
       };
    }

    private Object[] makeParameterObject(RequestBean requestBean){
        //参数还原
        Object[] mParameters = null;
        RequestParameter[] requestParameters = requestBean.getRequestParameters();
        if (requestParameters != null && requestParameters.length > 0){
            mParameters = new Object[requestParameters.length];
            for (int i = 0; i < requestParameters.length; i++) {
                RequestParameter requestParameter = requestParameters[i];
                Class<?> classType = CacheCenter.getInstance().getClassType(requestParameter.getParameterClassName());
                mParameters[i] = gson.fromJson(requestParameter.getParameterValue(),classType);
            }
        }else {
            mParameters = new Object[0];
        }
        return mParameters;
    }

}

package unit;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import aidl.ProcessInterface;
import servicee.MyService;

/**
 * create by lei on 2019/1/28/028
 * desc:
 */
public class ProcessInvokeHander implements InvocationHandler {
    private Class aClass;
    private static final Gson gson = new Gson();
    public ProcessInvokeHander(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String respons = ProcessManager.getInstance().sendReq(MyService.GET_METHOD, aClass, method, args);
        if (!TextUtils.isEmpty(respons) && !"null".equalsIgnoreCase(respons)){
            Class<?> userClass = method.getReturnType();
            Object o = gson.fromJson(respons, userClass);
            return o;
        }
        return null;
    }
}

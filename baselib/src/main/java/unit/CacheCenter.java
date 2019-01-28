package unit;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * create by lei on 2019/1/28/028
 *  desc:缓存中心
 *  * 缓存中心的设计：缓存class,Object和method
 */
public class CacheCenter {
    private static final CacheCenter ourInstance = new CacheCenter();

    public static CacheCenter getInstance() {
        return ourInstance;
    }

    private CacheCenter() {
    }


    private HashMap<String,Class<?>> mClass = new HashMap<>();//类缓存表
    private HashMap<Class<?>,HashMap<String,Method>> mMethod = new HashMap<>();//方法缓存表
    private HashMap<String,Object> mObject = new HashMap<>();//对象缓存表


    public void putObject(String name,Object object){
        mObject.put(name,object);
    }

    public Object getObject(String name){
        return mObject.get(name);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void  register(Class<?> clazz){
        mClass.put(clazz.getName(),clazz);
        registerMethod(clazz);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void registerMethod(Class<?> clazz){
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            mMethod.putIfAbsent(clazz,new HashMap<String, Method>());
            HashMap<String, Method> stringMethodHashMap = mMethod.get(clazz);
            stringMethodHashMap.put(method.getName(),method);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Method getMethod(String className, String name){
        Class<?> classType = getClassType(className);
        if (name != null){
            mMethod.putIfAbsent(classType,new HashMap<String, Method>());
            HashMap<String, Method> stringMethodHashMap = mMethod.get(classType);
            Method method = stringMethodHashMap.get(name);
            if (method != null){
                return method;
            }
        }
        return null;
    }

    public Class<?> getClassType(String name){

        if (TextUtils.isEmpty(name)){
            return null;
        }

        Class<?> aClass = mClass.get(name);
        if (aClass == null){
            try {
                aClass = Class.forName(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return aClass;

    }




}

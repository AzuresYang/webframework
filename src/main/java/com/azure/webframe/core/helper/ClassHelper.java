package com.azure.webframe.core.helper;

import com.azure.webframe.core.annotation.Controller;
import com.azure.webframe.core.annotation.Service;
import com.azure.webframe.utils.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 28029 on 2018/4/25.
 */
public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET;
    static
    {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /*
    * 获取应用下所有类
    */
    public static Set<Class<?>> getClassSet()
    {
        return CLASS_SET;
    }
    public static Set<Class<?>> getServiceClassSet()
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET)
        {
            if(cls.isAnnotationPresent(Service.class));
                classSet.add(cls);
        }
        return classSet;
    }

    public static Set<Class<?>> getControllerClassSet()
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET)
        {
            if(cls.isAnnotationPresent(Controller.class));
            classSet.add(cls);
        }
        return classSet;
    }

    /*
    * 获取应用包名下所有的Bean类，包括Service,controller
    * */
    public static Set<Class<?>> getBeanClassSet()
    {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }



}

package com.azure.webframe.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by 28029 on 2018/4/25.
 */
public class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> cls)
    {
        Object instance;

        try {
            System.out.println("cls Name:"+cls.getName());
            instance  = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure",e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static Object invokeMethod(Object obj, Method method, Object...args)
    {
        Object result;
        try
        {
            method.setAccessible(true);
            result = method.invoke(obj,args);
        }catch (Exception e)
        {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void setField(Object obj, Field field,Object value)
    {
        try
        {
            field.setAccessible(true);
            field.set(obj,value);
        }catch(Exception e)
        {
            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }
}

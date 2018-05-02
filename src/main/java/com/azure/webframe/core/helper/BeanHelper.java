package com.azure.webframe.core.helper;

import com.azure.webframe.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 28029 on 2018/4/26.
 */
public final class BeanHelper {
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();
    static
    {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> beanCls:beanClassSet)
        {
            Object obj = ReflectionUtil.newInstance(beanCls);
            BEAN_MAP.put(beanCls,obj);
        }
    }

    public static Map<Class<?>,Object> getBeanMap()
    {
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls)
    {
        if(!BEAN_MAP.containsKey(cls))
        {
            throw new RuntimeException("can not get bean by class:"+cls);
        }
        return (T)BEAN_MAP.get(cls);
    }
}

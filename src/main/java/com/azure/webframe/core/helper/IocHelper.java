package com.azure.webframe.core.helper;

import com.azure.webframe.core.annotation.Inject;
import com.azure.webframe.utils.CollectionUtil;
import com.azure.webframe.utils.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by 28029 on 2018/4/27.
 */
public class IocHelper {
    static
    {
        //获取所有Bean类和Bean实例之间的映射关系
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap))
        {
            //遍历BeanMap
            for(Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet())
            {
                //从BeanMap中国区Bean类和Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance= beanEntry.getValue();

                //获取bean类定义的所有成员变量
                Field[] beanFields = beanClass.getFields();
                if(ArrayUtils.isNotEmpty(beanFields))
                {
                    //遍历BeanField
                    for(Field beanField :beanFields)
                    {
                        //有需要注入的地方
                        if(beanField.isAnnotationPresent(Inject.class))
                        {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            //如果这个需要注入的类型，是被管理的bean的话，允许注入
                            if(beanFieldInstance != null)
                            {
                                //通过反射初始化
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}

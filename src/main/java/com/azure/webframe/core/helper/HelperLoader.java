package com.azure.webframe.core.helper;

import com.azure.webframe.utils.ClassUtil;

/**
 * Created by 28029 on 2018/4/27.
 */
public class HelperLoader {
    public static void init()
    {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class};
        for(Class<?> cls:classList)
        {
            ClassUtil.loadClass(cls.getName(),false);
        }
    }
}

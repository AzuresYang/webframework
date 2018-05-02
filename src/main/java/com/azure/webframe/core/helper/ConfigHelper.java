package com.azure.webframe.core.helper;

import com.azure.webframe.core.configer.ConfigConstant;
import com.azure.webframe.utils.PropsUtil;

import java.util.Map;
import java.util.Properties;

/**
 * Created by 28029 on 2018/4/25.
 */
public class ConfigHelper {
    private static final Properties CONFIG_PROPS =
            PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static void main(String[] args)
    {

    }

    public static void test()
    {
        for(Map.Entry<Object,Object> entry:CONFIG_PROPS.entrySet())
        {
            String name = (String)entry.getKey();
            String value = (String)entry.getValue();
            String pVlaue = CONFIG_PROPS.getProperty(name);
            System.out.println("name:"+name+"  value:"+value+"   kValue:"+pVlaue);

        }
    }
    public static String getAppBasePackage()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_BASE_PACKAGE);
    }
    public static String getAppJSPPath()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_JSP_PATH);
    }
}

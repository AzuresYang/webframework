package com.azure.webframe.utils;

import com.sun.xml.internal.bind.v2.util.QNameMap;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 28029 on 2018/4/25.
 */
public class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);
    public static Properties loadProps(String fileName)
    {
        Properties props = null;
        InputStream is = null;
        try{
            is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is  == null)
            {
                throw new FileNotFoundException(fileName+"file is not found");
            }
            props = new Properties();
            props.load(is);
//            System.out.println("load properties:");
//            for(Map.Entry<Object,Object> entry:props.entrySet())
//            {
//                String name = (String)entry.getKey();
//                String value = (String)entry.getValue();
//                String pVlaue = props.getProperty(name);
//                System.out.println("name:"+name+"  value:"+value+"   kValue:"+pVlaue);
//
//            }
//            String key = "framework.app.base.package";
//            String re = props.getProperty(key);
//            System.out.println("re is:"+re);
            LOGGER.info("load file:"+fileName+"   successed");
        }catch (IOException e)
        {
            LOGGER.error("load file:"+fileName+" failure",e);
        }finally {
            if(is != null)
            {
                try{
                    is.close();
                }catch (IOException e)
                {
                    LOGGER.error("close input stream failure",e);
                }
            }
        }

        return props;
    }

    public static String getString(Properties props,String key)
    {
        return getString(props,key,"");
    }

    public static String getString(Properties props ,String key, String defaultValue)
    {

        String value = props.getProperty(key);
        if(value == null)
            return defaultValue;
        else
            return value;
    }

    public static int getInt(Properties props, String key ,int defaultValue)
    {
        int value = defaultValue;
        if(props.containsKey(key))
        {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    public static int getInt(Properties props, String key)
    {
        return getInt(props,key,0);
    }

    public static boolean getBoolean(Properties pros,String key, boolean defaultValue)
    {
        boolean value = defaultValue;
        if(pros.containsKey(key))
        {
            value = CastUtil.castBoolean(pros.getProperty(key));
        }
        return value;
    }
    public static boolean getBoolean(Properties pros,String key)
    {
        return getBoolean(pros,key,false);
    }

}

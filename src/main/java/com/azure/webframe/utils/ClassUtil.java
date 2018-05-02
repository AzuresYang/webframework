package com.azure.webframe.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by 28029 on 2018/4/23.
 */
public final class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
    public static void main(String[] args) {
        String packageName = "com.pojo";
       Set<Class<?>> classSet = ClassUtil.getClassSet(packageName);
       for(Class<?> cls:classSet)
       {
           System.out.println("className:"+cls.getName());
       }
    }

    public static ClassLoader getClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }
    public static Class<?> loadClass(String className, boolean isInit)
    {
       // System.out.println("className:"+className);
        Class<?> cls;
        try{
            cls = Class.forName(className,isInit,getClassLoader());
        }catch (ClassNotFoundException e)
        {
            LOGGER.error("load class failure",e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Set<Class<?>> getClassSet(String packageName)
    {
        Set<Class<?>> classSet = new HashSet<>();
        try {

            Enumeration<URL> urls = getClassLoader().getResources(
                    packageName.replace(".","/"));

            while(urls.hasMoreElements())
            {
                URL url = urls.nextElement();
               // System.out.println(url.toString());
                if(url != null)
                {
                    String protocal = url.getProtocol();
                    if(protocal.equals("file"))
                    {
                        String packagePath = url.getPath().replaceAll("%20","");
                        packagePath = URLDecoder.decode(packagePath,"UTF-8");

                       // System.out.println("path:"+packagePath+"\npackageName:"+packageName);
                        addClass(classSet,packagePath,packageName);
                    } else if(protocal.equals("jar"))
                    {
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if(jarURLConnection != null)
                        {
                            //取出每一个jar元素
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if(jarFile != null)
                            {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                //如果jar元素是一个class文件，加载之
                                while(jarEntries.hasMoreElements())
                                {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if(jarEntryName.endsWith(".class"))
                                    {
                                        String className =
                                                jarEntryName.substring(0,
                                                        jarEntryName.lastIndexOf("."))
                                                        .replaceAll("/",".");
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e)
        {
            LOGGER.error("get class set failure",e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet,String packagePath,String packageName)
    {
        //获取类文件或者是子包
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile()&&file.getName().endsWith(".class"))||file.isDirectory();
            }
        });

        for(File file:files)
        {
            //System.out.println("fileName:"+file.getName());
            String fileName = file.getName();
            try {
                fileName = URLDecoder.decode(fileName,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //这是一个类文件，加载之
            if(file.isFile())
            {
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(className != null && !StringUtils.isBlank(className))
                {
                    className = packageName+"."+className;
                }
               // System.out.println("addClass:"+className);
                doAddClass(classSet,className);
            }
            //如果是一个子包名，加载之后的类
            else
            {
                String subPackagePath = fileName;
                if(StringUtils.isBlank(packagePath))
                {
                    subPackagePath = packagePath+"/"+subPackagePath;
                }
                String subPackageName =fileName;
                if(!StringUtils.isBlank(subPackageName))
                {
                    subPackageName = packageName+"."+subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className){
        Class<?> cls = loadClass(className,false);
        System.out.println("ioc add cls:"+cls.getName());
        classSet.add(cls);
    }
}

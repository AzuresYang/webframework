package com.azure.webframe.core.helper;

import com.azure.webframe.core.annotation.Action;
import com.azure.webframe.core.annotation.Controller;
import com.azure.webframe.core.bean.Handler;
import com.azure.webframe.core.bean.Request;
import com.azure.webframe.utils.CollectionUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 28029 on 2018/4/27.
 */
public class ControllerHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHelper.class);
    private static final Map<Request,Handler> ACTION_MAP = new HashMap<>();
    static
    {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet))
        {
            //遍历这些controller
            for(Class<?> controllerClass:controllerClassSet)
            {
                Method[] methods = controllerClass.getMethods();
                if(ArrayUtils.isNotEmpty(methods))
                {
                    for(Method method:methods)
                    {
                        if(method.isAnnotationPresent(Action.class))
                        {
                            //从Action中获取URL映射
                            Action action = method.getAnnotation(Action.class);
                            //Action注释的路径
                            String mapping = action.value();
                            //协议头映射
                            if(mapping.matches("\\w+:/\\w*"))
                            {
                                String[] array = mapping.split(":");
                                if(ArrayUtils.isNotEmpty(array) && array.length == 2)
                                {
                                    //获取请求路径和请求方法
                                    String requestMehotd = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMehotd,requestPath);
                                    Handler handler = new Handler(controllerClass,method);
                                    //初始化Action Map
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                            else{
                                LOGGER.info("Action mapper has error, cannt map url correctly:"+mapping);
                            }
                        }
                    }
                }

            }
        }
    }

    public static Handler getHandler(String requestMethod ,String requestPath)
    {
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}

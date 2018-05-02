package com.azure.webframe.core;


import com.azure.webframe.core.annotation.Controller;
import com.azure.webframe.core.bean.Data;
import com.azure.webframe.core.bean.Handler;
import com.azure.webframe.core.bean.Param;
import com.azure.webframe.core.bean.View;
import com.azure.webframe.core.helper.BeanHelper;
import com.azure.webframe.core.helper.ConfigHelper;
import com.azure.webframe.core.helper.ControllerHelper;
import com.azure.webframe.core.helper.HelperLoader;
import com.azure.webframe.utils.CodecUtil;
import com.azure.webframe.utils.JsonUtils;
import com.azure.webframe.utils.ReflectionUtil;
import com.azure.webframe.utils.StreamUtil;
import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 28029 on 2018/4/27.
 */

//@WebServlet(urlPatterns="/frame/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);
    @Override
    public void init(ServletConfig servletConfig) throws ServletException
    {
        //初始化相关Helper类
        HelperLoader.init();
        //获取ServletContext对象（用于注册servlet）
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理JSP的Serlvlet
        ServletRegistration jspServlet =
                servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJSPPath()+"/*");
        //处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppJSPPath()+"*");
    }

    /*
    * 1.通过requets的路径和提交方法，获得相对应的handler.
    * 2.获取request里面的param参数，同时获取URL里面的参数,填充到paramMap里面去
    * 3.通过反射调用action方法，把paramMap作为参数传递进去
    * 4.返回值处理，如果是View的话，进行转发处理，如果是Data的话，序列化为json,写入response里面
    * */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,IOException
    {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();


        //获取Handler处理器
        Handler handler = ControllerHelper.getHandler(requestMethod,requestPath);
        if(handler == null)
        {
            LOGGER.info("cannt  map url -method:"+requestMethod+"  path:"+requestPath);
            return;
        }else
        {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while(paramNames.hasMoreElements())
            {
                String paramName = paramNames.nextElement();
                String value = request.getParameter(paramName);
                paramMap.put(paramName,value);
            }

            //获取request里面的内容,主要是取出URL里面的参数
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if(StringUtils.isNotEmpty(body))
            {
                String[] params = StringUtils.split(body,"&");
               if(ArrayUtils.isNotEmpty(params))
               {
                   for(String param:params)
                   {
                       String[] array = StringUtils.split(param,"=");
                       if(ArrayUtils.isNotEmpty(array))
                       {
                           String paramName = array[0];
                           String paramValue= array[1];
                           paramMap.put(paramName,paramValue);
                       }
                   }
               }
            }

            Param param = new Param(paramMap);

            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            for(Map.Entry<String,Object> entry:paramMap.entrySet())
            {
                String n = entry.getKey();
                String v = entry.getValue().toString();
                System.out.println("n :"+n+"   v"+v);
            }
            Object result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);

            //如果是返回View的话，就是个视图
            if(result instanceof View)
            {
                View view = (View) result;
                String path = view.getPath();
                if(StringUtils.isNotEmpty(path))
                {
                    if(path.startsWith("/"))
                    {
                        response.sendRedirect(request.getContextPath()+path);
                    }else
                    {
                        Map<String,Object> model = view.getModel();
                        for(Map.Entry<String,Object> entry: model.entrySet())
                        {
                            request.setAttribute(entry.getKey(),entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJSPPath()+path).
                                forward(request,response);
                    }
                }
            }else if(result instanceof Data)
            {
                //返回JSON数据
                Data data = (Data) result;
                Object model = data.getModel();
                if(model != null)
                {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtils.objectToJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }

        }
    }
}

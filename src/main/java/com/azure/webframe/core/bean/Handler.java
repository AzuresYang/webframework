package com.azure.webframe.core.bean;

import java.lang.reflect.Method;

/**
 * Created by 28029 on 2018/4/27.
 */
public class Handler {

    private Class<?> controllerClass;
    private Method actionMethod;

    public Handler (Class<?> controllerClass, Method actionMethod)
    {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }

    public void setActionMethod(Method actionMethod) {
        this.actionMethod = actionMethod;
    }
}

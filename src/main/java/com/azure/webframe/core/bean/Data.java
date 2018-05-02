package com.azure.webframe.core.bean;

/**
 * Created by 28029 on 2018/4/27.
 */
public class Data {
    private Object model;

    public Data(Object obj)
    {
        this.model= obj;
    }
    public Object getModel()
    {
        return this.model;
    }
}

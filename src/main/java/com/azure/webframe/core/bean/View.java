package com.azure.webframe.core.bean;

import java.util.Map;

/**
 * Created by 28029 on 2018/4/27.
 */
//视图层
public class View {

    private String path;

    /*
    * 模型数据
    * */
    private Map<String ,Object> model;

    public View(String path)
    {
        this.path = path;
    }

    public View addModel(String key,Object value)
    {
        model.put(key,value);
        return this;
    }


    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}

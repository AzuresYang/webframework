package com.azure.webframe.core.bean;

import com.azure.webframe.utils.CastUtil;

import java.util.Map;

/**
 * Created by 28029 on 2018/4/27.
 */
public class Param {
    private Map<String,Object> paramMap;
    public Param(Map<String,Object> paramMap)
    {
        this.paramMap = paramMap;
    }

    public long getLong(String name)
    {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String,Object> getMap()
    {
        return this.paramMap;
    }
}

package com.pojo;

import com.azure.webframe.core.annotation.Action;
import com.azure.webframe.core.annotation.Controller;
import com.azure.webframe.core.bean.Data;
import com.azure.webframe.core.bean.Param;
import com.azure.webframe.core.helper.ConfigHelper;

/**
 * Created by 28029 on 2018/4/28.
 */
@Controller
public class Hello {
    public static void main(String[] args) {
        System.out.println("path is :"+ConfigHelper.getAppJSPPath());
    }
    @Action("get:/hello")
    public Data sayHello(Param param)
    {
        Data data = new Data("hello web frame");
        System.out.println("hello web frame");
        return data;
    }
}

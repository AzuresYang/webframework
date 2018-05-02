package com.webservice.service.impl;

import com.webservice.service.HelloService;

import javax.jws.WebService;

/**
 * Created by 28029 on 2018/5/2.
 */
@WebService(
        serviceName = "HelloService",
        endpointInterface = "com.webservice.service.HelloService"
)
public class HelloServiceImpl implements HelloService{

    @Override
    public String sayHello(String str) {
        System.out.println("Hello :"+str);
        return "Hello"+str;
    }
}

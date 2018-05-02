package com.webservice;

import com.webservice.service.HelloService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * Created by 28029 on 2018/5/2.
 */
public class Client {
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress("http://localhost:8080/hello");
        factory.setServiceClass(HelloService.class);
        HelloService helloWorld = (HelloService) factory.create();
        System.out.println(helloWorld.sayHello("zhuwei"));
    }
}

package com.webservice.service;

import com.webservice.service.impl.HelloServiceImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.xml.ws.Endpoint;

/**
 * Created by 28029 on 2018/5/2.
 */
public class Test {
    public static void main(String[] args) {


        HelloService implementor = new HelloServiceImpl();
        String address = "http://localhost:8080/hello";
        Endpoint.publish(address, implementor);
    }
}

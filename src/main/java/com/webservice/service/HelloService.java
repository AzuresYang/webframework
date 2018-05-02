package com.webservice.service;

import javax.jws.WebService;

/**
 * Created by 28029 on 2018/5/2.
 */


@WebService
public interface  HelloService {
    String sayHello(String str);
}

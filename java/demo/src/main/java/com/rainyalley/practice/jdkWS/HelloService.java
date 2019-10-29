package com.rainyalley.practice.jdkWS;

import javax.jws.WebService;

//此注解必须
@WebService
public interface HelloService {

    String say();
}

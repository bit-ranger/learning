package com.rainyalley.practice.jdkWS;

import javax.jws.WebService;

@WebService(serviceName = "HelloService",
        portName = "HelloServicePort",
        endpointInterface = "com.rainyalley.practice.jdkWS.HelloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public String say() {
        return "helloWorld";
    }
}

package com.rainyalley.practice.jdkWS;


import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        String address = "http://localhost:8081/ws/soap/hello";
        HelloService helloService = new HelloServiceImpl();

        Endpoint.publish(address, helloService);
        System.out.println("ws is published [" + address + "?wsdl]");
    }

}

package com.rainyalley.practice.jdkWS;


import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * 生成客户端
 * wsimport http://localhost:8080/ws/soap/hello?wsdl    //通过 WSDL 地址生成 class 文件
 * jar -cf client.jar .                                 //通过 jar 命令将若干 class 文件压缩为一个 jar 包
 * rmdir /s/q demo                                      //删除生成的 class 文件（删除根目录即可）
 */
public class Client {
    //静态客户端
//    public static void main(String[] args){
////
////        HelloService_Service hss = new HelloService_Service();
////        HelloService hs = hss.getHelloServicePort();
////        System.out.println(hs.say());
//    }

    //动态代理客户端
    public static void main(String[] args){
        try {
            URL wsdl = new URL("http://localhost:8081/ws/soap/hello?wsdl");
            QName serviceName = new QName("http://jdkws.practice.sllx.org/", "HelloService");
            QName portName = new QName("http://jdkws.practice.sllx.org/", "HelloServicePort");
            Service service = Service.create(wsdl, serviceName);

            HelloService helloService = service.getPort(portName, HelloService.class);
            String result = helloService.say();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

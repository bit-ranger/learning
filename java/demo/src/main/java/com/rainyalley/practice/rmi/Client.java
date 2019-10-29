package com.rainyalley.practice.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by sllx on 7/18/15.
 * <p>
 * java.net.NoRouteToHostException: No route to host :
 * hostname can not match address, so edit hosts.
 */
public class Client {
    public static void main(String args[]) throws RemoteException, MalformedURLException, NotBoundException {
        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:1099/HelloService");
        System.out.println(helloService.sayHello());
    }
}

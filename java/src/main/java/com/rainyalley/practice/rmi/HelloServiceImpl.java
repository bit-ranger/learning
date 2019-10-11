package com.rainyalley.practice.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by sllx on 7/18/15.
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

    protected HelloServiceImpl() throws RemoteException {}

    @Override
    public String sayHello() throws RemoteException {
        return "server says. 'Hey'";
    }
}

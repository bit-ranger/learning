package com.rainyalley.practice.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by sllx on 7/18/15.
 */
public interface HelloService extends Remote {
    String sayHello() throws RemoteException;
}

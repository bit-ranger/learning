package com.rainyalley.practice.rmi;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by sllx on 7/18/15.
 */
public class RegistryBook {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Registry r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        r.rebind("HelloService", new HelloServiceImpl());
    }
}

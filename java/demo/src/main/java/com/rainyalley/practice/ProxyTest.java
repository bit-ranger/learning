package com.rainyalley.practice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Animal {
    void say();

    void jump();
}

/**
 * Created by sllx on 7/18/15.
 */
public class ProxyTest {

    public static void main(String[] args) {
        Animal animal = ProxyFactory.getProxy(new Cat());
        animal.say();
        animal.jump();
        System.out.println(Proxy.isProxyClass(animal.getClass()));
    }
}

class Cat implements Animal {
    public void say() {
        System.out.println("say");
    }

    @Override
    public void jump() {
        System.out.println("jump");
    }

}

class ObjectInvocationHandler<E> implements InvocationHandler {

    private E target;

    public ObjectInvocationHandler(E target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        System.out.println(method);
        return method.invoke(target, args);
    }
}

class ProxyFactory {
    public static <E> E getProxy(E target) {
        return (E) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new ObjectInvocationHandler<>(target));
    }
}
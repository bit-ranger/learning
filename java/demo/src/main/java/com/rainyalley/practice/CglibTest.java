package com.rainyalley.practice;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by sllx on 14-12-1.
 */
public class CglibTest {

    public static void main(String[] args) {
//	    final Person p1 = new Person();
//        Enhancer en = new Enhancer(); //声明增加类实例
//        en.setSuperclass(Person.class);  //设置被代理类字节码，CGLIB根据字节码生成被代理类的子类
//        en.setCallback(new MethodInterceptor() {
//			@Override
//			public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodproxy) throws Throwable {
//				return method.invoke(p1, args);
//			}
//        });
//
//        Person p = (Person) en.create();  //通过create方法返回Person类的代理
//        System.err.println(p.getClass());//被代理的对象
//        for (Method method : p.getClass().getDeclaredMethods()) {
//			System.out.println(method);
//		}
//        p.sayHi("Hello");


        CglibProxy.getProxy(Person.class).sayHi("hello");

//        Person p = (Person) Enhancer.create(Person.class, new MethodInterceptor() {
//			@Override
//			public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodproxy) throws Throwable {
//				return methodproxy.invokeSuper(obj, args);
//			}
//        });
//        p.sayHi("ab");
    }
}

class CglibProxy implements MethodInterceptor {
    private CglibProxy() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> clazz) {
        Enhancer en = new Enhancer();
        en.setSuperclass(clazz);
        en.setCallback(new CglibProxy());
        return (T) en.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy methodproxy) throws Throwable {
        System.out.println("执行前");
        Object o = null;
        try {
            //此处与Main类中不同,
            //使用methodproxy的invokeSuper执行父类对象obj中的方法,与使用invoke执行给定的父类对象中的方法，效果相同
            o = methodproxy.invokeSuper(obj, args);
        } catch (Exception e) {
            System.out.println("异常后");
        }
        System.out.println("return 后");
        return o;
    }
}

class Person {
    public void sayHi(String str) {
        System.out.println(str);
    }
}

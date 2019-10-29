package com.rainyalley.practice;

import java.lang.invoke.*;

public class InvokeTest {

    /**
     * 根据提供的信息找出对应的方法句柄
     * 并返回一个该方法句柄的动态调用点
     *
     * @param lookup
     * @param name
     * @param type
     * @return
     * @throws Throwable
     */
    private static CallSite bootstrap(MethodHandles.Lookup lookup, String name, MethodType type) throws Throwable {
        return new ConstantCallSite(lookup.findStatic(InvokeTest.class, name, type));
    }

    private static MethodHandle bootstrapMethodHandle() throws Throwable {
        MethodType type = MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);
        //获取bootstrap方法句柄
        return MethodHandles.lookup().findStatic(InvokeTest.class, "bootstrap", type);
    }

    private static MethodHandle testMethodInvoker() throws Throwable {
        MethodType type = MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null);
        //调用bootstrap方法生成test方法动态调用点
        CallSite cs = (CallSite) bootstrapMethodHandle().invokeWithArguments(MethodHandles.lookup(), "test", type);
        return cs.dynamicInvoker();
    }

    private static void test(String str) {
        System.out.println(str);
    }

    public static void main(String[] args) throws Throwable {
        testMethodInvoker().invokeExact("hello world");
    }
}

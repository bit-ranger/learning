package com.rainyalley.practice.rme;

import java.lang.reflect.Method;

public class JavaClassExecutor {
    public static String execute(byte[] classByte){
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUtf8Constant("java/lang/System", "com/rainyalley/practice/rme/HackSystem");
        HotswapClassLoader hcl = new HotswapClassLoader();
        Class clazz = hcl.load(modiBytes);
        try {
            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (Exception e) {
            e.printStackTrace(HackSystem.error);
        }
        return HackSystem.getBufferString();
    }
}

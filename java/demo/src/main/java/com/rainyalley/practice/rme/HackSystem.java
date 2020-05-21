package com.rainyalley.practice.rme;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class HackSystem {
    public static ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    public final static InputStream in = System.in;
    public final static PrintStream out = new PrintStream(buffer);
    public final static PrintStream error = out;


    public static String getBufferString() {
        return buffer.toString();
    }

    public static void clearBuffer() {
        buffer.reset();
    }

    public static SecurityManager getSecurityManager() {
        return System.getSecurityManager();
    }

    public static void setSecurityManager(final SecurityManager manager) {
        System.setSecurityManager(manager);
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static void arrayCopy(Object src, int srcPos, Object desc, int descPos, int length) {
        System.arraycopy(src, srcPos, desc, descPos, length);
    }

    public static int identityHashCode(Object x) {
        return System.identityHashCode(x);
    }

}

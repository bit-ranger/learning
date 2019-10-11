package com.rainyalley.practice;

/**
 * 获取当前调用者
 */
public class Caller {
    public static void main(String[] args){
        new C().tt();
        new B().tt();
    }
}

class A{

    static void t(){
        try{
            throw new RuntimeException();
        } catch (Exception e){
            StackTraceElement[] ste =  e.getStackTrace();
            String cn = ste[1].getClassName();
            String mn = ste[1].getMethodName();
            System.out.println(String.format("%s.%s",cn,mn));
        }

    }

}

class B{

    void tt(){
        A.t();
    }

}

class C{
    void tt(){
        A.t();
    }
}
package com.rainyalley.practice.rme;

public class HotswapClassLoader extends ClassLoader{
    public HotswapClassLoader(){
        super(HotswapClassLoader.class.getClassLoader());
    }

    public Class load(byte[] bytes){
        return defineClass(null, bytes, 0, bytes.length);
    }
}

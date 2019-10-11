package com.rainyalley.practice;


/**
 * java8类型推测
 */
public class TypeInfer {
    public static void main(String[] args) {

        final Value< String > value = new Value<>();

        //value.setV(Value.<String>defV()); //以前的写法

        //Value.defV()返回类型被推测为String
        value.setV(Value.defV());
        System.out.println(value.getV());
    }
}

class Value< T > {

    private T o;

    public static <T>  T defV() {
        //若T不为Object,将会出现类型转换异常
        return (T)new Object();
    }

    public void setV(T o){
        this.o = o;
    }

    public T getV(){
        return o;
    }
}
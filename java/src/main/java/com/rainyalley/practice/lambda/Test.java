package com.rainyalley.practice.lambda;

/**
 * 表示为函数接口，可写可不写，只有标记功能
 */
@FunctionalInterface
interface Test{

    void test(Runnable r);

    //三种默认函数接口
    //Function,Consumer,Predicate

    //可以含有默认实现,不影响函数接口
    default boolean dfm(Object str){
        System.out.println("Test dfm");
        return false;
    }

    //继承而来的方法不影响函数接口
    @Override
    boolean equals(Object obj);

// default 方法不能重写Object中的方法
//    default int hashCode(){
//        return 0;
//    }

    //静态方法不影响函数接口,且只能通过接口名调用
    static void test(){
        System.out.println("可以定义静态方法");
    }
}
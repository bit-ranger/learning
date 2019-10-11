package com.rainyalley.practice.lambda;

/**
 * Created by zhangbin on 14-9-4.
 */
public class ExeImpl implements com.rainyalley.practice.lambda.Executable, Test {


    @Override
    public void execute() {

    }

    @Override
    public void test(Runnable r) {

    }


    /**
     * 从多个接口或父类中继承了相同的方法，必须显式重写，否则编译不通过
     */
    @Override
    public boolean dfm(Object str){
        //指定一个实现
        return Test.super.dfm(str);
    }
}

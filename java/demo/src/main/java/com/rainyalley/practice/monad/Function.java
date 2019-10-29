package com.rainyalley.practice.monad;

/**
 * 函数
 */
public interface Function {

    /**
     * @return 返回值类型
     */
    Class getReturnType();

    /**
     * @return 形参类型列表
     */
    Class[] getParameterTypes();

    /**
     * 调用
     *
     * @param args 实参列表
     * @return 函数返回值
     */
    Object call(Object[] args);
}

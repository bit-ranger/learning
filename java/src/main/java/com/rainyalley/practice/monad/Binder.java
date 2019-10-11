package com.rainyalley.practice.monad;

/**
 * 绑定器
 * 为一个对象绑定一个动作，生成一个创建器
 */
public interface Binder {
    Creator bind(Object obj);
}

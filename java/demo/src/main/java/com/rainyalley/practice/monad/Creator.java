package com.rainyalley.practice.monad;

/**
 * 对象创建器
 */
public interface Creator {

    /**
     * @return 组件生成对象的类型
     * 如果返回null, 则表示该组件动态决定类型
     */
    Class getType();

    /**
     * @param dep 依赖
     * @return 生成的对象
     */
    Object create(Dependency dep);
}

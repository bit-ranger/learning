package com.rainyalley.practice.monad;

/**
 * 由{@link Creator }调用，来解决依赖
 */
public interface Dependency {
    Object getArgument(int i, Class type);

    Object getProperty(Object key, Class type);
}

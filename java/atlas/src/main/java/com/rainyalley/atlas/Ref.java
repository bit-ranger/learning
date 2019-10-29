package com.rainyalley.atlas;

public interface Ref<T> {

    long getAddress();

    T getTarget(Storage storage);

    long store(Storage storage);


}

package com.huifu.race.sort;

public interface LineConverter<T> {

    T convert(String line);

    String toString(T t);
}

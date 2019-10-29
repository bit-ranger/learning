package com.rainyalley.practice.repeatingAnnotations;

import com.rainyalley.practice.repeatingAnnotations.annotation.Filter;

/**
 * 重复注解
 */
public class RepeatableTest {

    public static void main(String[] args) {
        //java8 提供的新方法，用于获取重复的注解
        for (Filter filter : Filterable.class.getAnnotationsByType(Filter.class)) {
            System.out.println(filter.value());
        }
    }

    /**
     * 测试
     */
    @Filter("filter1")
    @Filter("filter2")
    public interface Filterable {
    }
}

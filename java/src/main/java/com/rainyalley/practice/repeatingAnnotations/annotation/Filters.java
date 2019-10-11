package com.rainyalley.practice.repeatingAnnotations.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 存放@Filter注解的数组
 * 该注解必须为public
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
public @interface Filters {
    Filter[] value();
}

package com.rainyalley.practice.repeatingAnnotations.annotation;

import java.lang.annotation.*;

/**
 * @Repeatable( Filters.class )
 * 表示该注解可重复使用，注解内容存放于Filters中
 */
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Repeatable( Filters.class )
public @interface Filter {
    String value();
};
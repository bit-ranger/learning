package com.rainyalley.practice.newFeatureIn8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

/**
 * java8 扩展注解，可以再几乎所有位置添加注解
 */
public class AnnotationEX {

    public static void main(String[] args) {
        final TestAEX<String> holder = new @Anno TestAEX<>();
        @Anno Collection<@Anno String> strings = new ArrayList<>();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface Anno {
        String value() default "";
    }

    @Anno
    public @interface Annos {
        Anno[] value() default {@Anno};
    }

    @Annos(value = {@Anno, @Anno("B")})
    @Anno
    public static class TestAEX<@Anno T> extends @Anno Object {
        public void method() throws @Anno Exception {
        }

    }
}

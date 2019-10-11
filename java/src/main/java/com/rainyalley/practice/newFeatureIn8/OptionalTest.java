package com.rainyalley.practice.newFeatureIn8;


import java.util.Optional;

/**
 * Optional 容器处理空指针
 */
public class OptionalTest {
    public static void main(String[] args){

        //将String对象装入Optional容器
        Optional< String > stringOptional = Optional.ofNullable( null );

        //判断容器中的String对象是否不为
        System.out.println(stringOptional.isPresent() );

        //orElseGet通过lambda产生一个默认值
        System.out.println(stringOptional.orElseGet( () -> "[default string]" ) );

        //map对String对象进行转化，然后返回一个新的Optional实例，
        // 此处s为null，所以未转换直接返回了1个新Optional实例，
        // orElse直接产生一个默认值
        System.out.println(stringOptional.map( p -> "[" + p + "]" ).orElse( "Hello Optionl" ) );

    }
}

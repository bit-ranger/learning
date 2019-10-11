package com.rainyalley.practice.lambda;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by sllx on 14-9-3.
 */
public class TestLambda {

    //递归方法必须为成员级别，无法定义在局部变量中
//    private static Consumer<Integer> recurrence = i -> {
//        System.out.println(i);
//        if(i > 0){
//            recurrence.accept(i--);
//        } else {
//            return;
//        }
//    };

    public static void main(String[] args) {

        /** 1 f表示该表达式在被调用时传入的参数,->后面是方法的具体实现*/
        test(f -> {
            System.out.println("测试Test");
            f.run();
        });


        /** 2 lambda表达式必须先声明为一个类型,才能当做对象使用*/
        // 一个lambda表达式就是一个匿名方法,可以有多个目标类型,只要方法匹配就行
        Test t = (f -> {});
        t.equals(null);


        /** 3 静态调用*/
        Test.test();


        /** 4 数据处理，管道模式*/
        ArrayList<Color> list = new ArrayList();
        list.add(Color.BLACK);
        list.add(Color.WHITE);
        list.add(Color.WHITE);
        //1.并行生成Stream
        // 2.对每个对象进行处理(Function,lambda可以为表达式,该表达式的值自动作为返回值),将处理后的对象的集合生成新的Stream
        // 3.过滤(Predicate)
        // 4.去重
        // 5.使用指定的集合收集对象
        // 6.迭代(Consumer)
        //collect方法是"急"(eager)的，而前面方法都是"懒"(lazy)的，每个元素依次通过这些方法贯通执行.其他的eager方法还有forEach，toArray，reduce
        list.parallelStream().map(f -> f.toString()).filter(f -> f.equals(Color.WHITE.toString())).distinct().collect(Collectors.toList()).forEach(f -> System.out.println(f));


        /** 5 Stream.reduce作用是方法用来产生单一的一个最终结果，此处自定义的方法为累加，x表示当前对象之前所有对象累加的结果，y表示当前对象*/
        String s = list.parallelStream().map(f -> f.toString()).reduce((x,y) -> x + " | " + y).get();
        System.out.println(s);


        /** 6 嵌套*/
        Function<String, Consumer<String>> function = f -> {
            System.out.println("调用Consumer之前");
            //关于f值的传递问题：在apply执行后,该方法中的f会变为apply方法的参数,无需再传递参数
            return c -> System.out.println(f + c);
        };
        function.apply("hello").accept("-world!");
        Function<Predicate,Executable> function1 = p -> p.test("a") ? (() -> System.out.println("true")) : (() -> System.out.println("false"));
        function1.apply(p -> p.equals("dfg")).execute();


        /** 7 递归*/
        //recurrence.accept(3);


        /** 8 捕获*/
        //默认为effectively final 无需显式声明为final，但是依旧不可修改
        int tmp = 0;
        Consumer<?> consumer = p -> System.out.println(tmp);


        /** 9 方法引用 当方法调用者需要一个实现时，可将调用参数类型中的方法或构造器引用为lambda,引用的方法的返回值必须符合函数接口的要求
         *              若引用的方法是静态方法，则调用参数将作为静态方法的参数，若引用的方法是无参实例方法，则调用者参数将作为实例，且实例方法不可有参数
         *              若引用的方法是有参实例方法，调用者参数将作为参数，实例由客户端提供
         * */
        list.parallelStream().map(Color::toString).filter(f -> f.equals(Color.WHITE.toString())).distinct().collect(Collectors.toList()).forEach(System.out::println);
        UnaryOperator<String> u = String::toString;
        System.out.println(u.apply("方法引用"));
        Function<Point,Boolean> f =  new Point()::equals;
        System.out.println(f.apply(new Point()));
//        super::methName //引用某个对象的父类方法
//        TypeName[]::new //引用一个数组的构造器


        /**默认方法，如果两个接口中存在完全相同的默认方法，那么实现类必须重写此方法*/
        new ExeImpl().equals("");


        /**生成器函数 如果没有limit(5)，那么这条语句会永远地执行下去。也就是说这个生成器是无穷的。这种调用叫做终结操作，或者短路（short-circuiting）操作。*/
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
    }

    static void test(Test test){
        test.test(() ->  System.out.println("测试Runnable"));
    }
}

package com.rainyalley.practice.jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by sllx on 2015/11/24.
 */
public class BTrace {
    public int add(int a, int b){
        return a+b;
    }

    public static void main(String[] args) throws Exception{
        BTrace bTrace = new BTrace();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10; i++) {
            reader.readLine();
            int a = (int)Math.round(Math.random() * 1000);
            int b = (int)Math.round(Math.random() * 1000);
            System.out.println(bTrace.add(a,b));
        }
    }


//    /* BTrace Script Template */
//    import com.sun.btrace.annotations.*;
//    import static com.sun.btrace.BTraceUtils.*;
//
//    @BTrace
//    public class TracingScript {
//        /* put your code here */
//        @OnMethod(
//                clazz="com.rainyalley.practice.jvm.BTrace",
//                method="add",
//                location=@Location(Kind.RETURN)
//        )
//        public static void func(@Self com.rainyalley.practice.jvm.BTrace instance, int a, int b, @Return int result){
//            println("调用堆栈: ");
//            jstack();
//            println(strcat("方法参数A: ", str(a)));
//            println(strcat("方法参数B: ", str(b)));
//            println(strcat("方法结果A: ", str(result)));
//        }
//
//    }
}

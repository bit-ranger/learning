package com.rainyalley.practice;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sllx on 3/15/15.
 */
public class GenericTypeTest {

    interface CustomCompable<E> extends Comparable<E>{

    }

    static class TestType<T> extends Number implements CustomCompable<TestType>{

        public List<String> field = new ArrayList<>();

        public T generic;

        @Override
        public int compareTo(TestType o) {

            return 0;
        }

        @Override
        public int intValue() {
            return 0;
        }

        @Override
        public long longValue() {
            return 0;
        }

        @Override
        public float floatValue() {
            return 0;
        }

        @Override
        public double doubleValue() {
            return 0;
        }
    }

    public static <T extends Comparable<T>> T max(List<? extends T> list){

        Iterator<? extends T> i = list.iterator();
        T result = i.next();
        while (i.hasNext()){
            T t = i.next();
            if(t.compareTo(result)>0){
                result = t;
            }
        }

        return result;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //List<TestType> testTypeList = new ArrayList<>();
        //TestType t = max(testTypeList);

        //class
        Field field = TestType.class.getField("field");
        System.out.println(field.getGenericType());
        System.out.println(field.getType());
        Field generic = TestType.class.getField("generic");
        System.out.println(generic.getGenericType());
        System.out.println(generic.getType());

        //object erasure
        TestType t = new TestType();
        System.out.println(t.field.getClass());

        Object f = field.get(t);
        f.getClass().getMethod("add",Object.class).invoke(f,1);
        System.out.println(f);
    }
}

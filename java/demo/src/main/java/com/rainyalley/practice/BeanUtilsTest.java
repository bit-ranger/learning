package com.rainyalley.practice;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

/**
 * Created by sllx on 5/13/15.
 */
public class BeanUtilsTest {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        Student std1 = new Student("Li Lei", 18);
//        Student std2 = new Student("Han Mei Mei", 19);
//        Student std3 = new Student("Li Lei", 17);
//
//        List<Student> list = Arrays.asList(std1, std2, std3);
//
//        ComparatorChain comparatorChain = new ComparatorChain(
//                Arrays.asList(
//                        ComparatorUtils.reversedComparator(new BeanComparator<>("name")), new BeanComparator<>("age")));
//        Collections.sort(list, comparatorChain);
//
//        System.out.println(list);
//        Arrays.asList(BeanUtils.getArrayProperty(std1, "name")).forEach(
//                p -> System.out.println(p)
//        );


        if (new Predicate() {
            @Override
            public boolean test(Object o) {
                System.out.print("hello ");
                return false;
            }
        }.test(null)) {
            System.out.println("hello ");
        } else {
            System.out.println("world");
        }

    }

    public static class Student {
        private String name;
        private int age;

        Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "name:" + name + ",age:" + age;
        }
    }

}

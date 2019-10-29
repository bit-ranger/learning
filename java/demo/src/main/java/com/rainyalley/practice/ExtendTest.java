package com.rainyalley.practice;


class Base {
    private int i = 2;

    public Base() {
        this.display();
    }

    public void display() {
        System.out.println(i);
    }
}

class Derived extends Base {
    private int i = 22;

    public Derived() {
        i = 222;
    }

    public void display() {
        System.out.println(i);
    }
}

public class ExtendTest {

    public static void main(String[] args) {
        new Derived();
    }

    /*
     *	以上代码输出为0
     *	分析: new Derived ();时需要先执行Base 类的构造方法,该构造方法中调用了display()方法,
     *	而该方法已被重写,执行的操作为打印Derived 类中的i,而此时Derived 类还没有初始化,Derived 当中的i也没有初始化,打印结果为0.
     */
}



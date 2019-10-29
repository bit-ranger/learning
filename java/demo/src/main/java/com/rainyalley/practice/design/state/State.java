package com.rainyalley.practice.design.state;

/**
 * Created by sllx on 7/30/15.
 */
public enum State {

    A {
        @Override
        void doSthA() {
            System.out.println("already did A, change to Stat B");
            context.setState(B);
        }

        @Override
        void doSthB() {
            System.out.println("only can A");
        }
    },

    B {
        @Override
        void doSthA() {
            System.out.println("only can B");
        }

        @Override
        void doSthB() {
            System.out.println("already did B, change to Stat A");
        }
    };

    protected Context context;

    abstract void doSthA();

    abstract void doSthB();

    public void setContext(Context context) {
        this.context = context;
    }
}

package com.rainyalley.practice.design.state;

/**
 * Created by sllx on 7/30/15.
 */
public class Context {

    private State state = State.A;

    {
        state.setContext(this);
    }


    public void doSthA() {
        state.doSthA();
    }

    public void doSthB() {
        state.doSthB();
    }


    void setState(State state) {
        this.state = state;
    }
}

package com.rainyalley.practice.monad.component;

import com.rainyalley.practice.monad.Creator;
import com.rainyalley.practice.monad.Dependency;
import com.rainyalley.practice.monad.Function;

public class FunctionCreator implements Creator {

    private Function function;

    public FunctionCreator(Function function) {
        this.function = function;
    }

    @Override
    public Class getType() {
        return function.getReturnType();
    }

    @Override
    public Object create(Dependency dep) {
        Class[] types = function.getParameterTypes();
        Object[] args = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            Class tp = types[i];
            args[i] = dep.getArgument(i, tp);
        }
        return function.call(args);
    }
}

package com.rainyalley.practice.monad.component;

import com.rainyalley.practice.monad.Creator;
import com.rainyalley.practice.monad.Dependency;

public class ValueCreator implements Creator {

    private Object object;

    public ValueCreator(Object object) {
        this.object = object;
    }

    @Override
    public Class getType() {
        return object == null ? null : object.getClass();
    }

    @Override
    public Object create(Dependency dep) {
        return object;
    }
}

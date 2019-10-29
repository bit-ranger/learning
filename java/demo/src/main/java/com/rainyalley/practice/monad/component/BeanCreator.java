package com.rainyalley.practice.monad.component;

import com.rainyalley.practice.monad.Creator;
import com.rainyalley.practice.monad.Dependency;

public class BeanCreator implements Creator {

    private Class type;

    public BeanCreator(Class type) {
        this.type = type;
    }

    @Override
    public Class getType() {
        return type;
    }

    @Override
    public Object create(Dependency dep) {
        Object object = createInstance();
        setJavaBeans(object, dep);
        return object;
    }

    private Object createInstance() {
        return new Object();
    }

    private void setJavaBeans(Object object, Dependency dependency) {

    }
}

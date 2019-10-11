package com.rainyalley.practice.monad.component;

import com.rainyalley.practice.monad.Binder;
import com.rainyalley.practice.monad.Creator;
import com.rainyalley.practice.monad.Dependency;


public class BoundCreator implements Creator {

    private Creator creator;

    private Binder binder;

    public BoundCreator(Creator creator, Binder binder){
        this.creator = creator;
        this.binder = binder;
    }

    @Override
    public Class getType() {
        return null;
    }

    @Override
    public Object create(Dependency dep) {
        return binder.bind(creator.create(dep)).create(dep);
    }
}

package com.rainyalley.practice.monad.component;

import com.rainyalley.practice.monad.Creator;
import com.rainyalley.practice.monad.Dependency;
import com.rainyalley.practice.monad.Mapper;

public class MapperCreator implements Creator {

    private Creator origin;
    private Mapper mapper;

    public MapperCreator(Creator origin, Mapper mapper){
        this.origin = origin;
        this.mapper = mapper;
    }

    @Override
    public Class getType() {
        return null;
    }

    @Override
    public Object create(Dependency dep) {
        return mapper.map(origin.create(dep));
    }
}

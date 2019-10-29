package com.rainyalley.practice;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * 异构的MAP
 */
public class HeterogeneousMap {
    public static void main(String[] args) throws ClassNotFoundException {
        Favorites f = new Favorites();
        f.put(String.class, "");

        Class<?> anno = Class.forName("java.lang.annotation.Annotation");
        Class<? extends Annotation> a = anno.asSubclass(Annotation.class);
    }

    static class Favorites {
        private Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();

        public <T extends String> void put(Class<T> type, T instance) {
            favorites.put(type, type.cast(instance));
        }

        public <T extends String> T get(Class<T> type) {
            return type.cast(favorites.get(type));
        }
    }
}

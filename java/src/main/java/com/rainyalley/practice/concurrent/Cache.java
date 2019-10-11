package com.rainyalley.practice.concurrent;

import java.util.concurrent.*;

public class Cache<K,V> {
    private ConcurrentMap<K,FutureTask<V>> map = new ConcurrentHashMap<>();
    private Executor executor = Executors.newFixedThreadPool(8);

    static class Task<V> implements Callable<V>{
        @Override
        public V call() throws Exception {
            return null;
        }
    }

    public V get(final K key) throws ExecutionException, InterruptedException {
        FutureTask<V> ft = map.get(key);
        if (ft == null) {
            ft = new FutureTask(new Task());
            FutureTask<V> old = map.putIfAbsent(key, ft);
            if (old == null){
                executor.execute(ft);
            }else{
                ft = old;
            }
        }
        return ft.get();
    }
}

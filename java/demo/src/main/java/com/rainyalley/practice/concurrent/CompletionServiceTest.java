package com.rainyalley.practice.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by sllx on 6/27/15.
 */
public class CompletionServiceTest {

    <V> V solve(Executor e, Collection<Callable<V>> tasks) throws InterruptedException, ExecutionException {
        CompletionService<V> ecs = new ExecutorCompletionService<>(e);
        List<Future<V>> futures = new ArrayList<>();
        V result = null;

        try {
            tasks.forEach(p -> futures.add(ecs.submit(p)));
            for (int i = 0; i < tasks.size(); ++i) {
                V r = ecs.take().get();
                if (r != null) {
                    result = r;
                    break;
                }
            }
        } finally {
            for (Future<V> f : futures) {
                f.cancel(true);
            }
        }

        return result;
    }
}

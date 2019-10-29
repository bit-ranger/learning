package com.rainyalley.jss;

public interface Lock {

    boolean hasLock(String entityId, double time);

    boolean tryLock(String entityId, double time);

    boolean lock(String entityId, double time, long timeout);

    boolean unLock(String entityId, double time);

    double time(String entityId);
}

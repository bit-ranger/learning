package com.rainyalley.atlas;

public interface Storage {

    boolean lock();

    boolean isLocked();

    boolean unLock();

    /**
     *
     * @param data
     * @return address
     */
    long write(byte[] data);

    /**
     *
     * @param address
     * @return data
     */
    byte[] read(long address);

    long getRootAddress();


}

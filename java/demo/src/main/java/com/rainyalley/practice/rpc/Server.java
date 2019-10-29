package com.rainyalley.practice.rpc;

public interface Server {
    public void stop();

    public void start();

    public void register(Class interfaceDefiner, Class impl);

    public void call(Invocation invo);

    public boolean isRunning();

    public int getPort();
}

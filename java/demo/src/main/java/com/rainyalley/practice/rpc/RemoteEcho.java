package com.rainyalley.practice.rpc;

public class RemoteEcho implements Echo {
    public String echo(String echo) {
        return "from remote:" + echo;
    }
}

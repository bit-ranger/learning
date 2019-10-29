package com.rainyalley.practice.rpc;


public class MainClient {
    public static void main(String[] args) {
        Echo echo = RPC.getProxy(Echo.class, "127.0.0.1", 20382);

        System.out.println(echo.echo("hello,hello"));
        System.out.println(echo.echo("hellow,rod"));
        System.out.println(echo.echo("hellow,rod"));
        System.out.println(echo.echo("hellow,rod"));
        System.out.println(echo.echo("hellow,rod"));
        System.out.println(echo.echo("hellow,rod"));
    }
}

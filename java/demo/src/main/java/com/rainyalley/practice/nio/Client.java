package com.rainyalley.practice.nio;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        SocketChannel sc = SocketChannel.open();
        Socket s = sc.socket();
        InetSocketAddress add = new InetSocketAddress(Integer.parseInt(args[0]));
        s.connect(add);

        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);

        sc.write(ByteBuffer.wrap("hello".getBytes()));

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> ki = keys.iterator();
            while (ki.hasNext()) {
                SelectionKey k = ki.next();

                if ((k.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (true) {
                        buffer.clear();

                        int r = sc.read(buffer);

                        if (r <= 0) {
                            break;
                        }
                    }

                    System.out.println(new String(buffer.array()));
                }
            }
        }
    }
}

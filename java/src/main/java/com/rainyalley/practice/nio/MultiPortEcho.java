package com.rainyalley.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiPortEcho
{
    private int ports[];
    private ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );

    public MultiPortEcho( int ports[] ) throws IOException {
        this.ports = ports;

        go();
    }

    private void go() throws IOException {
        // Create a new selector
        Selector selector = Selector.open();

        // Open a listener on each port, and register each one
        // with the selector
        for (int i=0; i<ports.length; ++i) {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking( false );
            ServerSocket ss = ssc.socket();
            InetSocketAddress address = new InetSocketAddress( ports[i] );
            ss.bind( address );

            //关注新连接事件
            SelectionKey key = ssc.register( selector, SelectionKey.OP_ACCEPT );

            System.out.println( "Going to listen on "+ports[i] );
        }

        while (true) {
            int num = selector.select();

            //selectKeys是逐渐累加的,不会自动移除Key
            Set selectedKeys = selector.selectedKeys();
            Iterator it = selectedKeys.iterator();

            while (it.hasNext()) {
                SelectionKey key = (SelectionKey)it.next();

                if ((key.readyOps() & SelectionKey.OP_ACCEPT)
                        == SelectionKey.OP_ACCEPT) {
                    //这个channel就是go()中注册的channel,
                    //其中指定ServerSocketChannel关注OP_ACCEPT,
                    //此key为OP_ACCEPT类型，所以channel必为ServerSocketChannel
                    ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                    // Accept the new connection
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking( false );

                    // Add the new connection to the selector
                    SelectionKey newKey = sc.register( selector, SelectionKey.OP_READ );
                    it.remove();

                    System.out.println( "Got connection from "+sc );
                } else if ((key.readyOps() & SelectionKey.OP_READ)
                        == SelectionKey.OP_READ) {
                    // Read the data
                    SocketChannel sc = (SocketChannel)key.channel();

                    boolean isEof = true;

                    while (true) {
                        echoBuffer.clear();

                        int r = sc.read( echoBuffer );

                        if (r<=0) {
                            break;
                        }

                        isEof = isEOF(echoBuffer);

                        echoBuffer.flip();

                        sc.write( echoBuffer );
                    }

                    if(isEof){

                        sc.close();
                    }

                    it.remove();
                }

            }
        }
    }

    private boolean isEOF(ByteBuffer buffer){
        byte[] end = "\r\n".getBytes();
        boolean isEof = true;
        for (int i = 1; i <= end.length; i++) {

            isEof &=  buffer.get(buffer.position() - i) == end[end.length - i];
        }

        return isEof;
    }

    public static void main( String args[] ) throws Exception {
        if (args.length<=0) {
            System.err.println( "Usage: java MultiPortEcho port [port port ...]" );
            System.exit( 1 );
        }

        int ports[] = new int[args.length];

        for (int i=0; i<args.length; ++i) {
            ports[i] = Integer.parseInt( args[i] );
        }

        new MultiPortEcho( ports );
    }


}

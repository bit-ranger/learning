package com.rainyalley.practice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sllx on 7/27/15.
 */
public class HttpPrint {
    private ServerSocket serverSocket;

    public HttpPrint() throws IOException {
        serverSocket = new ServerSocket(8080);
    }

    public void show() throws IOException{
        while(true){
            Socket socket = serverSocket.accept();
            byte[] buf = new byte[1024];
            InputStream is =  socket.getInputStream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int n = 0;
            while ((n = is.read(buf)) > -1){
                os.write(buf,0,n);
            }
            os.close();
            is.close();
            socket.close();

            System.out.println(os);
        }
    }

    public static void main(String[] args) throws IOException {
        new HttpPrint().show();
    }
}

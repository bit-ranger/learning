package com.rainyalley.practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by sllx on 15-2-4.
 */
public class RuntimeTest {
    public static void main(String[] args) {
        Process p = null;
        try {
           p  = Runtime.getRuntime().exec("cmd /c java -version");
           OutputStream out = p.getOutputStream();
            InputStream in = p.getInputStream();
            byte[] buf = new byte[1024];
            int n = in.read(buf);
            System.out.println(new String(Arrays.copyOfRange(buf,0,n)));

//            out.write("asd".getBytes());
//            out.flush();
//            n = in.read(buf);
//            System.out.println(new String(Arrays.copyOfRange(buf,0,n)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(p!=null){
                p.destroy();
            }
        }
    }
}

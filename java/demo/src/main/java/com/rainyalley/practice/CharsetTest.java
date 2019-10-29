package com.rainyalley.practice;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.function.Function;

/**
 * Created by sllx on 14-9-9.
 */
public class CharsetTest {
    public static void main(String[] args) throws UnsupportedEncodingException {

        Function<byte[], char[]> toChars = p -> {
            Charset cs = Charset.forName("utf-8");
            ByteBuffer bf = ByteBuffer.allocate(p.length);
            bf.put(p);
            bf.flip();
            CharBuffer cb = cs.decode(bf);
            return cb.array();
        };

        char[] chars = toChars.apply(new byte[]{-27, -68, -96, -28, -72, -119});

        System.out.println(chars);

        String str = new String("湼".getBytes("utf-8"), "utf8");
        System.out.println(str);
        String strConvert = new String(str.getBytes("iso8859-1"), "utf-8");
        System.out.println(strConvert);
    }
}

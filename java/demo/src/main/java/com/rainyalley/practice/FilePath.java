package com.rainyalley.practice;

import java.io.File;

public class FilePath {

    public static void main(String[] args) {
        String path1 = FilePath.class.getResource("/").getPath();
        String path2 = FilePath.class.getResource("").getPath();
        Object path3 = FilePath.class.getClassLoader().getResource("/");
        String path4 = FilePath.class.getClassLoader().getResource("").getPath();
        String path5 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String path6 = ClassLoader.getSystemResource("").getPath();
        String path7 = new File("").getAbsolutePath();
        String path8 = System.getProperty("user.dir");
        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path3);
        System.out.println(path4);
        System.out.println(path5);
        System.out.println(path6);
        System.out.println(path7);
        System.out.println(path8);
    }
}

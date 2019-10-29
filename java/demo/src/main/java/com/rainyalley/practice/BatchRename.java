package com.rainyalley.practice;

import java.io.File;

public class BatchRename {

    public static void main(String[] args) throws Exception {
        String prefix = "20130319";
        String suffix = ".zip";
        File dir = new File("/var/logs");
        int i = 0;
        for (File file : dir.listFiles()) {
            file.renameTo(new File(dir.getPath() + File.separator + prefix + i + suffix));
            i++;
        }
    }
}

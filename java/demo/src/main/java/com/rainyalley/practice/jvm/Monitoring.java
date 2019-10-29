package com.rainyalley.practice.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动参数
 * -Xms100m -Xmx100m -XX:+UseSerialGC
 */
public class Monitoring {
    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            //稍作延迟，令监视曲线的变化更明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
    }

    private static class OOMObject {
        private byte[] placeHolder = new byte[64 * 1024];
    }
}

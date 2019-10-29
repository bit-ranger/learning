package com.rainyalley.practice.rme;

class ByteUtils {
    public static int bytesToInt(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = ((int) b[i]) & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

    public static byte[] intToBytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) ((value >> i * 8) & 0xff);
        }
        return b;
    }

    public static String bytesToString(byte[] b, int start, int len) {
        return new String(b, start, len);
    }

    public static byte[] stringToBytes(String str) {
        return str.getBytes();
    }

    public static byte[] bytesReplace(byte[] origin, int offset, int len, byte[] replace) {
        byte[] newBytes = new byte[origin.length + replace.length - len];
        System.arraycopy(origin, 0, newBytes, 0, offset);//origin头部数据
        System.arraycopy(replace, 0, newBytes, offset, replace.length);//replace数据
        System.arraycopy(origin, offset + len, newBytes, offset + replace.length, origin.length - offset - len);//origin尾部数据
        return newBytes;
    }
}

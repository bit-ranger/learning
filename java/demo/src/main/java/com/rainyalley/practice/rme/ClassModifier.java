package com.rainyalley.practice.rme;

public class ClassModifier {
    /**
     * 常量池的起始偏移
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    /**
     * CONSTANT_Utf8_info 的tag标志
     */
    private static final int CONSTANT_Utf8_info = 1;

    /**
     * 常量池中11种常量所占的宽度, CONSTANT_Utf8_info除外，因为它不是定长的
     */
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};

    private static final int U1 = 1;
    private static final int U2 = 2;

    private byte[] classBytes;

    public ClassModifier(byte[] classBytes) {
        this.classBytes = classBytes;

    }

    /**
     * 修改常量池中CONSTANT_Utf8_info常量的内容
     */
    public byte[] modifyUtf8Constant(String before, String after) {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + U2;
        for (int i = 0; i < cpc; i++) {
            int tag = ByteUtils.bytesToInt(classBytes, offset, U1);
            if (tag == CONSTANT_Utf8_info) {
                int len = ByteUtils.bytesToInt(classBytes, offset + U1, U2);
                offset += U1 + U2;
                String str = ByteUtils.bytesToString(classBytes, offset, len);
                if (str.equalsIgnoreCase(before)) {
                    byte[] afterBytes = ByteUtils.stringToBytes(after);
                    byte[] afterLen = ByteUtils.intToBytes(after.length(), U2);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset - U2, U2, afterLen);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset, len, afterBytes);
                } else {
                    offset += len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classBytes;
    }

    private int getConstantPoolCount() {
        return ByteUtils.bytesToInt(classBytes, CONSTANT_POOL_COUNT_INDEX, U2);
    }


}

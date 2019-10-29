package com.rainyalley.atlas;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class AvlTreeNode {

    private static Charset charset = Charset.forName("UTF-8");

    public static AvlTreeNode toNode(byte[] nodeBytes){
        ByteBuffer buffer = ByteBuffer.wrap(nodeBytes);
        byte type = buffer.get();
        long value = buffer.getLong();
        long height = buffer.getLong();
        long left = buffer.getLong();
        long right = buffer.getLong();
        byte[] keyBytes = new byte[nodeBytes.length - 33];
        buffer.get(keyBytes);
        String key = new String(keyBytes, charset);
        AvlTreeNode node = new AvlTreeNode(key, value, null);
        node.setLeft(left);
        node.setRight(right);
        node.setHeight(height);
        return node;
    }

    private String key;

    private long left;

    private long right;

    private long value;

    private long height = 1;

    private AvlTreeNode parent;

    /**
     * 自身的地址，自身不需要保存此信息
     */
    private long address;


    public AvlTreeNode(String key, long value, AvlTreeNode parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
    }

    public byte[] toBytes(){
        byte[] keyBytes = key.getBytes(charset);
        ByteBuffer buffer = ByteBuffer.allocate(33 + keyBytes.length);
        buffer.put((byte) 1);
        buffer.putLong(value);
        buffer.putLong(height);
        buffer.putLong(left);
        buffer.putLong(right);
        buffer.put(keyBytes);
        return buffer.array();
    }

    public String getKey() {
        return key;
    }

    public long getLeft() {
        return left;
    }

    public long getRight() {
        return right;
    }

    public long getValue() {
        return value;
    }

    public long getHeight() {
        return height;
    }




    public void setKey(String key) {
        this.key = key;
    }

    public void setLeft(long left) {
        this.left = left;
    }

    public void setRight(long right) {
        this.right = right;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public AvlTreeNode getParent() {
        return parent;
    }

    public void setParent(AvlTreeNode parent) {
        this.parent = parent;
    }

    public long getAddress() {
        return address;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"@class\":\"com.rainyalley.atlas.AvlTreeNode\",");
        sb.append("\"@super\":\"").append(super.toString()).append("\",");
        sb.append("\"key\":\"")
                .append(key)
                .append("\"");
        sb.append(",\"left\":\"")
                .append(left)
                .append("\"");
        sb.append(",\"right\":\"")
                .append(right)
                .append("\"");
        sb.append(",\"value\":\"")
                .append(value)
                .append("\"");
        sb.append(",\"height\":\"")
                .append(height)
                .append("\"");
        sb.append(",\"parent\":\"")
                .append(parent)
                .append("\"");
        sb.append(",\"address\":\"")
                .append(address)
                .append("\"");
        sb.append("}");
        return sb.toString();
    }
}

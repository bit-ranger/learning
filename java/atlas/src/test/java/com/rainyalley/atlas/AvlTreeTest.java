package com.rainyalley.atlas;

import junit.framework.TestCase;

import java.nio.charset.Charset;

public class AvlTreeTest extends TestCase {

    private Charset charset = Charset.forName("UTF-8");

    private Storage valueStorage = new StorageFileImpl("/var/AvlTreeValue.data");
    private Storage treeStorage = new StorageFileImpl("/var/AvlTreeIdx.data");
    private AvlTree avlTree = new AvlTree(treeStorage);

    public void testGet() {
    }

    public void testInsert() {
        long  valAddress = valueStorage.write("world1".getBytes(charset));
        AvlTreeNode root = null;

        for (int i = 0; i < 5; i++) {
            root = avlTree.insert(root, "hello" + i, valAddress);
        }

    }

    public void testDelete() {
    }
}
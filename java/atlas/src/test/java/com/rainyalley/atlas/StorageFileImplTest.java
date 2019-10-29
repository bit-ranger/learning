package com.rainyalley.atlas;


import org.junit.Test;

import java.nio.charset.Charset;

public class StorageFileImplTest {

    private Charset charset = Charset.forName("UTF-8");

    private StorageFileImpl storageFile = new StorageFileImpl("/var/StorageFileImplTest.data");

    public void testLock() {
    }

    public void testIsLocked() {
    }

    public void testUnLock() {
    }

    @Test
    public void testWrite() {
        long idx1 = storageFile.write("test".getBytes(charset));
        long idx2 = storageFile.write("hello world".getBytes(charset));

    }

    @Test
    public void testRead() {
        byte[] bytes = storageFile.read(0);
        String value = new String(bytes, charset);
    }

    public void testGetRootAddress() {
    }
}
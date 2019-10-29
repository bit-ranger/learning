package com.rainyalley.atlas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class StorageFileImpl implements Storage {


    private RandomAccessFile rac;


    public StorageFileImpl(String filePath) {
        File file = new File(filePath);

        try {
            rac = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean lock() {
        return false;
    }

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public boolean unLock() {
        return false;
    }

    @Override
    public long write(byte[] data) {
        try {
            long ptr = rac.length();
            rac.seek(ptr);
            rac.writeInt(data.length);
            rac.write(data);
            return ptr;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public byte[] read(long address) {
        try {
            rac.seek(address);
            int length = rac.readInt();
            byte[] bytes = new byte[length];
            rac.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public long getRootAddress() {
        return 0;
    }
}

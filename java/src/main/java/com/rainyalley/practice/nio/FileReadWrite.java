package com.rainyalley.practice.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class FileReadWrite {

    private static final String filePath = "writeSomeBytes.txt";

    public void read() throws Exception{
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel channel =  fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
    }

    public void write() throws Exception{
        FileOutputStream fos = new FileOutputStream(filePath);
        FileChannel channel = fos.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        byte[] message = "some bytes".getBytes();
        for (int i = 0; i < message.length; i++) {
            buffer.put(message[i]);
        }
        buffer.flip();

        channel.write(buffer);
    }

    public void copyFile() throws Exception{
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel fci = fis.getChannel();

        FileOutputStream fos = new FileOutputStream(filePath + ".cpy");
        FileChannel fco = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            int r = fci.read(buffer);
            if(r == -1){
                break;
            }
            buffer.flip(); //设limit为position, 设position为0, 即从buffer向channel写的模式
            fco.write(buffer);
            buffer.clear();//设limit为capacity, 设position为0
        }

    }

    public void fileMap() throws Exception{
        FileChannel channel = new FileInputStream(filePath).getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, 1024);
    }

    public void gatherAndSplit() throws Exception{
        FileChannel channelI = new FileInputStream(filePath).getChannel();
        ByteBuffer bf1 = ByteBuffer.allocate(512);
        ByteBuffer bf2 = ByteBuffer.allocate(512);

        ByteBuffer[] bfs = new ByteBuffer[]{bf1, bf2};

        channelI.read(bfs);

        FileChannel channelO = new FileOutputStream(filePath).getChannel();
        channelO.write(bfs);
    }

    public void fileLock() throws Exception {
        //要获得一个排它锁，必须以写方式打开文件
        RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
        FileChannel fc = raf.getChannel();
        FileLock lock = fc.lock(0, 1024, false);
        lock.release();

        //可移植性建议
        //只使用排它锁。
        //将所有的锁视为劝告式的（advisory）。
    }

    public void charset() throws Exception{
        String inputFile = "samplein.txt";
        String outputFile = "sampleout.txt";

        RandomAccessFile inf = new RandomAccessFile( inputFile, "r" );
        RandomAccessFile outf = new RandomAccessFile( outputFile, "rw" );
        long inputLength = new File( inputFile ).length();

        FileChannel inc = inf.getChannel();
        FileChannel outc = outf.getChannel();

        MappedByteBuffer inputData =
                inc.map( FileChannel.MapMode.READ_ONLY, 0, inputLength );

        Charset latin1 = Charset.forName( "ISO-8859-1" );
        CharsetDecoder decoder = latin1.newDecoder();
        CharsetEncoder encoder = latin1.newEncoder();

        CharBuffer cb = decoder.decode( inputData );

        // Process char data here

        ByteBuffer outputData = encoder.encode( cb );

        outc.write( outputData );

        inf.close();
        outf.close();
    }


}

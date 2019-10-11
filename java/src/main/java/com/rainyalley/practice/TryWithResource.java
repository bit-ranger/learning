package com.rainyalley.practice;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by sllx on 2015-06-16.
 */
public class TryWithResource {

    @Test
    public void test(){

        try(TestAutoClose tac = new TestAutoClose()){
            tac.doSth();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

class TestAutoClose implements Closeable{

    void doSth(){
        System.out.println("do some thing");
    }

    @Override
    public void close() throws IOException {
        System.out.println("auto close");
    }
}
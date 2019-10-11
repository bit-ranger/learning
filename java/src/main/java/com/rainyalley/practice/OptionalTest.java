package com.rainyalley.practice;

import com.google.common.base.Optional;

public class OptionalTest {

    private static Optional<String> mayReturnNull(){
        return Optional.absent();
    }

    public static void main(String[] args){
        Optional<String> string = mayReturnNull();

        if(string.isPresent()){
            System.out.println(string.toString());
        }
    }

}

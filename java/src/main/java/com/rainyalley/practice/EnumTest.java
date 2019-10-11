package com.rainyalley.practice;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * Created by sllx on 3/15/15.
 */
public enum EnumTest {

    O;

    public int order(){
        EnumSet<EnumTest> es = EnumSet.of(EnumTest.O);
        EnumMap<EnumTest, Object> em = new EnumMap<EnumTest, Object>(EnumTest.class);
        EnumTest.class.getEnumConstants();
        return this.ordinal();
    }


}


package com.rainyalley.practice;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Intersection {
    public static void main(String[] args) throws Exception {
        BufferedReader brBc = new BufferedReader(new FileReader(new File("C:/Users/since/Downloads/bc_buyer_id.txt")));
        BufferedReader brSi = new BufferedReader(new FileReader(new File("C:/Users/since/Downloads/sign_user_record.txt")));
        BufferedWriter intersectionBw = new BufferedWriter(new FileWriter(new File("C:/Users/since/Downloads/intersection.txt")));

        List<String> bcIds = new ArrayList<>();
        List<String> siIds = new ArrayList<>();

        String str = null;
        while (true) {
            str = brBc.readLine();
            if (str != null) {
                bcIds.add(str);
            } else {
                break;
            }
        }
        brBc.close();
        System.out.println("bcIds.size:" + bcIds.size());

        while (true) {
            str = brSi.readLine();
            if (str != null) {
                String[] ids = str.split(",");
                for (String id : ids) {
                    siIds.add(id);
                }
            } else {
                break;
            }
        }
        brSi.close();
        System.out.println("siIds.size:" + siIds.size());


        Set<String> intersection = new HashSet<>();
        intersection.clear();
        intersection.addAll(bcIds);
        intersection.retainAll(siIds);
        for (String s : intersection) {
            intersectionBw.write(s + "\r\n");
        }
        System.out.println("intersection.size:" + intersection.size());

    }
}
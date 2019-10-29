package com.rainyalley.practice.algorithm;

/*
  字符串匹配
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringMatch {
    private int[] skip;//保存字符对应的截取长度，index为char值，value为截取长度
    private int p;
    private String str;
    private String key;

    public StringMatch(String key) {
        skip = new int[256];
        this.key = key;

        for (int k = 0; k <= 255; k++) {
            //假设遇见所有字符都要截取key的长度
            skip[k] = key.length();
        }

        //对key中存在的字符做特殊处理，
        // 如：遇到key中的第1个字符则截取key长度，遇见key中的第2个字符则截取key长度-1，存在重复字符取更小值(即用后者覆盖前者)
        for (int k = 0; k < key.length() - 1; k++) {
            skip[key.charAt(k)] = key.length() - k - 1;
        }

    }

    public static void main(String[] args)
            throws IOException {
        BufferedReader bufReader =
                new BufferedReader(
                        new InputStreamReader(System.in));

        System.out.print("請輸入字串：");
        String str = bufReader.readLine();
        System.out.print("請輸入搜尋關鍵字：");
        String key = bufReader.readLine();

        StringMatch strMatch = new StringMatch(key);
        strMatch.search(str);

        while (strMatch.hasNext()) {
            System.out.println(strMatch.next());
        }
    }

    public void search(String str) {
        this.str = str;
        p = search(key.length() - 1, str, key);
    }

    private int search(int p, String input, String key) {
        //对字符串迭代截取，找子串
        while (p < input.length()) {
            String tmp = input.substring(
                    p - key.length() + 1, p + 1);

            if (tmp.equals(key))  // 比較兩字串是否相同
                return p - key.length() + 1;
            p += skip[input.charAt(p)];
        }

        return -1;
    }

    public boolean hasNext() {
        return (p != -1);
    }

    public String next() {
        String tmp = str.substring(p);
        p = search(p + key.length() + 1, str, key);
        return tmp;
    }
}
package com.rainyalley.practice;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupDev2 {


    private void group() throws Exception{
        Charset charset = Charset.forName("UTF-8");
        Path pathR = Paths.get("/Doc/存档/data/mebDev2.txt");
        BufferedReader reader = Files.newBufferedReader(pathR, charset);
        Path pathW = Paths.get("/Doc/存档/data/mebDev2G.csv");
        BufferedWriter writer = Files.newBufferedWriter(pathW, charset);

        int sizePerGroup = 5;

        List<String> ignore = Arrays.asList("黄超","程志斌","柳丽丽","徐晋","金登超","郭欣宇","殷作飞","张斌","裔隽");

        List<String> nameList =  reader.lines().filter(l -> !ignore.contains(StringUtils.trim(l))).collect(Collectors.toList());
        Collections.shuffle(nameList);

        IntStream.range(0, nameList.size())
                .boxed()
                .collect(Collectors.groupingBy(index -> index / sizePerGroup))
                .forEach((gi, group) -> {
                    List<String> nameInGroup = group.stream().map(nameList::get).collect(Collectors.toList());
                    StringBuilder row = new StringBuilder().append(gi + 1);
                    nameInGroup.forEach(name -> {
                        row.append(",").append(name);
                    });
                    try {
                        String line = row.toString();
                        System.out.println(line);
                        writer.write(line);
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        writer.flush();
        writer.close();
    }


    public static void main(String[] args) throws Exception{
        new GroupDev2().group();
    }
}

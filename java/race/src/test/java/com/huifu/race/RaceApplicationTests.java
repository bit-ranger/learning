package com.huifu.race;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

@Ignore
@RunWith(SpringRunner.class)
public class RaceApplicationTests {

    @Test
    public void contextLoads() throws Exception{

        Random random = new Random();
        int numbers = 20000000;
        File dest = new File("D:\\Download\\source.csv");
        if(dest.exists()){
            dest.delete();
        }

        String[] sex = new String[]{"男", "女"};

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(dest))){
            for (int i = 0; i < numbers; i++) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        Math.abs(random.nextInt()),
                        "姓名" + Math.abs(random.nextInt()),
                        Math.abs(random.nextInt()) % 30,
                        sex[Math.abs(random.nextInt()) % 2],
                        "职业" + Math.abs(random.nextInt()) % 20,
                        Math.abs(random.nextInt()),
                        Math.abs(random.nextInt()),
                        "地区" + Math.abs(random.nextInt()) % 500,
                        Math.abs(random.nextInt()),
                        Math.abs(random.nextInt())
                        );
                bw.write(line);
                bw.newLine();
            }
        }

    }

}

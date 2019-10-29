package com.rainyalley.practice;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SqlGenerator {

    public static void main(String[] args) throws Exception {
        new SqlGenerator().csvToSql();
    }

    public void csvToSql() throws Exception {
        Charset charset = Charset.forName("UTF-8");

        Path pathR = Paths.get("/Doc/data/data.csv");
        Path pathW = Paths.get("/Doc/data/data.sql");

        BufferedReader reader = Files.newBufferedReader(pathR, charset);
        BufferedWriter writer = Files.newBufferedWriter(pathW, charset);

        reader.readLine();

        String line = null;
        int num = 0;
        while ((line = reader.readLine()) != null) {

            String[] ds = StringUtils.split(line, ",");
            String sql = buildSql(ds[0], ds[1]);
            writer.write(sql);
            writer.newLine();
            num++;

            if (num % 100 == 0) {
                writer.write("commit;");
                writer.newLine();
            }
        }

        if (num % 100 != 0) {
            writer.write("commit;");
            writer.newLine();
        }

        writer.flush();
        reader.close();
        writer.close();

        System.out.println(String.format("total:%s", num));
    }

    private String buildSql(String USR_MP, String CUST_ID) {
        String sql = String.format("insert into USR_MP_INFO(MER_CUST_ID,CUST_ID,USR_MP,OPEN_DATE,OPEN_TIME)" +
                        " select '8000080000044664','%s','%s','20171221', '000000'" +
                        " from dual" +
                        " WHERE not exists (select USR_MP_INFO.CUST_ID from USR_MP_INFO where USR_MP_INFO.CUST_ID = '%s' AND USR_MP_INFO.MER_CUST_ID = '8000080000044664');",
                CUST_ID,
                USR_MP,
                CUST_ID
        );

        return sql;

    }
}

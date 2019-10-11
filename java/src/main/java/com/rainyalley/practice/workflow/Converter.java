package com.rainyalley.practice.workflow;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converter {

    private static String text = "" +
            "+--------+------+------------+---------------+----------+-----------+----------------+---------------+--------+--------------+--------+---------------------+---------------------+\n" +
            "| id     | type | product_id | pay_frozen_id | user_id  | trade_num | frozen_num_bak | processed_num | price  | total_amount | status | create_time         | update_time         |\n" +
            "+--------+------+------------+---------------+----------+-----------+----------------+---------------+--------+--------------+--------+---------------------+---------------------+\n" +
            "| 554088 |    1 |          2 |     339139256 | 37806316 |         2 |              0 |             0 | 165700 |       331565 |      3 | 2016-05-12 10:40:54 | 2016-05-12 10:44:36 |\n" +
            "| 554091 |    1 |          2 |     339139265 | 37806316 |         2 |              0 |             2 | 165888 |       331941 |      2 | 2016-05-12 10:45:03 | 2016-05-12 14:40:01 |\n" +
            "| 554178 |    2 |          2 |          NULL | 37806316 |         1 |              0 |             1 | 165601 |       165601 |      2 | 2016-05-12 12:11:57 | 2016-05-12 12:11:59 |\n" +
            "| 554496 |    2 |          2 |          NULL | 37806316 |         1 |              0 |             1 | 165765 |       165765 |      2 | 2016-05-12 14:46:32 | 2016-05-12 14:46:32 |\n" +
            "+--------+------+------------+---------------+----------+-----------+----------------+---------------+--------+--------------+--------+---------------------+---------------------+\n" +
            "";

    private int headRowIndex = 1;

    public  ResultSet convert(String text){

        String[] rows = StringUtils.split(text, "\n");

        ResultSet resultSet = new ResultSet();
        List<Row> rsr = new ArrayList<>();
        resultSet.setRows(rsr);
        for (int i=0; i<rows.length; i++) {
            String row = rows[i];
            if(!StringUtils.startsWith(row, "|")){
                continue;
            }

            String[] values = StringUtils.split(row, "|");

            if(i == headRowIndex){
                resultSet.setColumns(Arrays.asList(values));
                continue;
            }

            Row r = new Row();
            r.setValues(Arrays.asList(values));
            rsr.add(r);
        }

        return resultSet;
    }

    public static void main(String[] args){
        Converter converter = new Converter();
        ResultSet resultSet = converter.convert(text);
        System.out.println("columns");
        resultSet.getColumns().forEach(s -> System.out.print(s + ","));
        System.out.println();
        System.out.println("rows");
        resultSet.getRows().forEach( p -> {
            p.getValues().forEach(v -> System.out.print(v + ","));
            System.out.println();
        });
    }
}

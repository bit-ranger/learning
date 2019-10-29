package com.rainyalley.practice.workflow.entity;

import com.rainyalley.practice.workflow.Converter;

public class BeanMapper {

    private Converter converter = new Converter();

//    public <T> List<T> map(String text, Class<T> type){
//        ResultSet resultSet = converter.convert(text);
//        return map(resultSet, type);
//    }
//
//    private <T> T map(ResultSet resultSet, Class<T> type){
//        try {
//            T instance = type.newInstance();
//            for (int i = 0; i < resultSet.getColumns().size(); i++) {
//                String column = resultSet.getColumns().get(i);
//                String value = resultSet.getRows()
//            }
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

}

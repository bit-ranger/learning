package com.rainyalley.practice;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author zhangbin
 * @date 2020-05-26
 */




public class Hmby {


    public static void main(String[] args) throws Exception{
        Hmby hmby = new Hmby();
        hmby.compute();
    }

    private void compute() throws Exception{
        InputStream is = new FileInputStream(new File(
                "C:\\Users\\bitranger\\Downloads\\hmby\\generalinfo.xml"));
        String xml = IOUtils.toString(is);
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSONArray json = (JSONArray)xmlSerializer.read(xml);

        for (Object o : json) {
            JSONObject jo = (JSONObject) o;
//            ablity(jo);
            cof(jo);
        }

        FileWriter writer = new FileWriter(new File(
                "C:\\Users\\bitranger\\Downloads\\hmby\\generalinfo_out.xml"));

        toXml(json, writer, "general");
        writer.close();
    }

    private void ablity(JSONObject jo){

        if (!jo.containsKey("ability")){
            return;
        }
        String ability = jo.getString("ability");
        String[] split = StringUtils.split(ability, ",");
        String na = Stream.of(split).mapToInt(Integer::valueOf).map(i -> {
            int range = i-80;
            if(i >= 80){
                return Math.min(200, (int)(80 * Math.pow(1.03, range)));
            } else{
                return Math.max(1, (int)(80 * Math.pow(1.03, range)));
            }
        }).mapToObj(String::valueOf).collect(Collectors.joining(","));
        jo.put("ability", na);
    }

    private  void cof(JSONObject jo){
        if (!jo.containsKey("cof")){
            return;
        }
        JSONObject cof = jo.getJSONObject("cof");

        if(!cof.containsKey("#text")){
            return;
        }

        String[] split = StringUtils.split(cof.getString("#text"), ",");

        String na = IntStream.range(0, split.length)
                .mapToObj(i -> {
                    if(i % 2 == 0){
                        return split[i];
                    }else{
                        return String.valueOf(Integer.parseInt(split[i]) * 4);
                    }
                })
                .collect(Collectors.joining(","));
        cof.put("#text", na);
    }


    private static void toXml(JSONArray json, Writer writer, String nodeName) throws IOException {
        for (Object o : json) {
            writer.write(String.format("<%s", nodeName));
            for (Map.Entry<String,String> entry : attribute((JSONObject) o).entrySet()) {
                writer.write(String.format(" %s=\"%s\"", entry.getKey(), entry.getValue()));
            }
            writer.write(">");
            toXml((JSONObject) o, writer);
            writer.write(String.format("\n</%s> ", nodeName));
            writer.write("\n");
        }
    }

    private static LinkedHashMap<String,String> attribute(JSONObject jo){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        for (Object o1 : jo.keySet()) {
            String k = o1.toString();
            if (k.startsWith("@")){
                String v = jo.getString(k);
                map.put(k.substring(1),v);
            }
        }
        return map;
    }

    private static LinkedHashMap<String,Object> children(JSONObject jo){
        LinkedHashMap<String,Object> map = new LinkedHashMap<>();
        for (Object o1 : jo.keySet()) {
            String k = o1.toString();
            if (!k.startsWith("@")){
                Object v = jo.get(k);
                map.put(k, v);
            }
        }
        return map;
    }

    private static void toXml(JSONObject jo, Writer writer) throws IOException {
        for (Map.Entry<String,Object> entry : children(jo).entrySet()) {
            String k = entry.getKey();
            Object value = jo.get(k);

            if(k.startsWith("#")){
                writer.write(String.format("%s", value));
                continue;
            }

            writer.write("\n");

            if(value instanceof JSONObject){
                if(((JSONObject) value).isEmpty()){
                    writer.write(String.format("<%s/>", k));
                } else {
                    writer.write(String.format("<%s", k));
                    for (Map.Entry<String,String> ae : attribute((JSONObject) value).entrySet()) {
                        writer.write(String.format(" %s=\"%s\"", ae.getKey(), ae.getValue()));
                    }
                    writer.write(">");
                    toXml((JSONObject)value, writer);
                    writer.write(String.format("</%s>", k));
                }
            }
            else if(value instanceof JSONArray){
                writer.write(String.format("<%s>", k));
                toXml((JSONArray) value, writer, k);
                writer.write(String.format("</%s>", k));
            }
            else{
                writer.write(String.format("<%s>%s</%s>", k, value, k));
            }

        }


    }
}

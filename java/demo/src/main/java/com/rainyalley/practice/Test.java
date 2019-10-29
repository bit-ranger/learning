package com.rainyalley.practice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Test {

    private static final String searchTradeUrl = "http://bcbo.qbao.com/ba/trader/searchTrade.html";
    private final static Logger LOGGER = new Logger();

    public static void main(String[] args) {
        echoTrade(655384);

    }

    private static void echoTrade(int tradeId) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("tradeId", String.valueOf(tradeId));

        String resp = doPost(searchTradeUrl, param, "UTF-8");
        JSONObject object = JSONObject.parseObject(resp);
        JSONArray array = object.getJSONArray("data");
        for (Object o : array) {
            JSONObject jo = (JSONObject) o;
            for (Object v : jo.values()) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }

    public static String doPost(String reqUrl, Map<String, String> parameters, String recvEncoding) {
        HttpURLConnection url_con = null;
        BufferedReader in = null;
        InputStreamReader isr = null;
        StringBuilder sb = new StringBuilder();
        String vchartset = recvEncoding.equals("") ? "UTF-8" : recvEncoding;
        try {
            StringBuffer params = new StringBuffer();
            for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry<?, ?> element = (Map.Entry<?, ?>) iter.next();
                if (element.getValue() == null) {
                    continue;
                }

                params.append(element.getKey().toString());
                params.append("=");
                params.append(URLEncoder.encode(element.getValue().toString(), vchartset));
                params.append("&");
            }

            if (params.length() > 0) {
                params = params.deleteCharAt(params.length() - 1);
            }

            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            url_con.setConnectTimeout(Integer.parseInt("10000"));
            url_con.setReadTimeout(Integer.parseInt("60000"));
            url_con.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();

            int code = url_con.getResponseCode();
            if (code != Integer.parseInt("200")) {
                LOGGER.error("与[" + reqUrl + "]通信过程中发生异常,ERROR:" + code);
                return "ERROR" + code;
            }
            isr = new InputStreamReader(url_con.getInputStream(), "utf-8");
            in = new BufferedReader(isr);
            String s = in.readLine();
            while (s != null) {
                sb.append(s);
                s = in.readLine();
            }
        } catch (IOException e) {
            LOGGER.error("与[" + reqUrl + "]通信过程中发生异常,堆栈信息如下", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e2) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (Exception e2) {
                }
            }
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        LOGGER.info("与[" + reqUrl + "]通信过程 参数：" + parameters + "，响应值 " + (sb.toString()));
        return sb.toString();
    }

    private static class Logger {
        void info(String message) {
            System.out.println(message);
        }

        void error(String message) {
            System.err.println(message);
        }

        void error(String message, Throwable t) {
            System.err.println(message);
            t.printStackTrace();
        }
    }
}


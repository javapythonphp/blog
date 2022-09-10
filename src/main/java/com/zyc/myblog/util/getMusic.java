package com.zyc.myblog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;

public class getMusic {

    public static String loadJson (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"utf-8"));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }


    public static void main(String[] args) {
        String url = "https://www.moe123.net/api/images?skip=0&nextToken=undefined";
        String content = loadJson(url);

        Map<String,Object> map1 = (Map)JSON.parse(content);
        List<Map<String,Object>> list = (List<Map<String, Object>>) map1.get("items");
        for(Map<String,Object> map:list){
            System.out.println(map.get("img_url"));
        }
    }


}

package com.taotao.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.APIService;
import com.taotao.web.bean.TaoTaoNews;

@Service
public class IndexService {

    @Autowired
    private APIService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${TAOTAO_MANAGE_URL}")
    private String TAOTAO_MANAGE_URL;

    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;

    @Value("${INDEX_AD2_URL}")
    private String INDEX_AD2_URL;

    @Value("${INDEX_AD3_URL}")
    private String INDEX_AD3_URL;

    /**
     * 实现大广告
     * 
     * @return
     */

    public String queryAd1() {
        String url = TAOTAO_MANAGE_URL + INDEX_AD1_URL;

        try {
            String jsonData = this.apiService.doGet(url); // 只需要判断是否为null或者"",不需要判断是否为空格，使用isEmpty效率更高
            // if
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            } else {
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                ArrayNode rows = (ArrayNode) jsonNode.get("rows");
                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                for (JsonNode row : rows) {
                    Map<String, Object> map = new LinkedHashMap<String, Object>();
                    map.put("srcB", row.get("pic").asText());
                    map.put("height", 240);
                    map.put("alt", row.get("title").asText());
                    map.put("width", 670);
                    map.put("src", row.get("pic").asText());
                    map.put("widthB", 550);
                    map.put("href", row.get("url").asText());
                    map.put("heightB", 240);
                    result.add(map);
                }

                return MAPPER.writeValueAsString(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public String queryAd1() {
//        String url = TAOTAO_MANAGE_URL + INDEX_AD1_URL;
//        try {
//            String jsonData = this.apiService.doGet(url);
//            // 只需要判断是否为null或者"",不需要判断是否为空格，使用isEmpty效率更高
//            if (StringUtils.isEmpty(jsonData)) {
//                return null;
//            } else {
//
//                EasyUIResult easyUIResult = EasyUIResult.formatToList(jsonData, Content.class);
//                @SuppressWarnings("unchecked")
//                List<Content> contentList = (List<Content>) easyUIResult.getRows();
//                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//                for (Content content : contentList) {
//                    Map<String, Object> map = new LinkedHashMap<String, Object>();
//                    map.put("srcB", content.getPic());
//                    map.put("height", 240);
//                    map.put("alt", content.getTitle());
//                    map.put("width", 670);
//                    map.put("src", content.getPic());
//                    map.put("widthB", 550);
//                    map.put("href", content.getUrl());
//                    map.put("heightB", 240);
//                    result.add(map);
//                }
//
//                return MAPPER.writeValueAsString(result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 实现右侧小广告
     * 
     * @return
     */
    public String queryAd2() {
        String url = TAOTAO_MANAGE_URL + INDEX_AD2_URL;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            } else {
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                ArrayNode rows = (ArrayNode) jsonNode.get("rows");
                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                for (JsonNode row : rows) {
                    Map<String, Object> map = new LinkedHashMap<String, Object>();
                    map.put("width", 310);
                    map.put("height", 70);
                    map.put("src", row.get("pic").asText());
                    map.put("href", row.get("url").asText());
                    map.put("alt", row.get("title").asText());
                    map.put("widthB", 210);
                    map.put("heightB", 70);
                    map.put("srcB", row.get("pic").asText());
                    result.add(map);
                }
                return MAPPER.writeValueAsString(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实现淘淘快报
     * 
     * @return
     */
    public List<TaoTaoNews> queryTaoTaoNews() {
        String url = TAOTAO_MANAGE_URL + INDEX_AD3_URL;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            } else {
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                ArrayNode rows = (ArrayNode) jsonNode.get("rows");
                List<TaoTaoNews> result = new ArrayList<TaoTaoNews>();
                for (JsonNode row : rows) {
                    TaoTaoNews taoTaoNews = new TaoTaoNews(row.get("title").asText(), row.get("url").asText());
                    result.add(taoTaoNews);
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}

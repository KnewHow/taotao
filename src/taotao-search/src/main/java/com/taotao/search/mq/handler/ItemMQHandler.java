package com.taotao.search.mq.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;

public class ItemMQHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @Autowired
    private SolrServer solrServer;
    
    @Autowired
    private ItemService itemService;


    public void execute(String msg) {
        try {
            JsonNode jsonNode = MAPPER.readTree(msg);
            Long itemId = jsonNode.get("itemId").asLong();
            String type = jsonNode.get("type").asText();
            if(StringUtils.equals(type, "update")||StringUtils.equals(type, "insert")){
                Item item = this.itemService.queryItemById(itemId);
                this.solrServer.addBean(item);
                this.solrServer.commit();
            }else if(StringUtils.equals(type, "delete")){
                this.solrServer.deleteById(itemId.toString());
                this.solrServer.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

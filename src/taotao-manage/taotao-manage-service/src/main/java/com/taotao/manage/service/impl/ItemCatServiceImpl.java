package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.taotao.common.bean.ItemCatData;
import com.taotao.common.bean.ItemCatResult;
import com.taotao.common.service.RedisService;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.BaseService;
import com.taotao.manage.service.ItemCatService;

@Service
public class ItemCatServiceImpl extends BaseService<ItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${redis.taotao.manage.item.cat.all.maxtime}")
    private  Integer SECONDS;

    private static final String KEY = "TAOTAO_MANAGE_ITEM_CAT_ALL";

    @Override
    public List<ItemCat> queryItemCatListByParent(Long parentId) {
        ItemCat record = new ItemCat();
        record.setParentId(parentId);
        return this.queryListByWhere(record);
    }

    /**
     * 实现父类的抽象方法
     */
    @Override
    public Mapper<ItemCat> getMapper() {
        return this.itemCatMapper;
    }

    @Override
    public ItemCat queryItemCatById(Long itemCatId) {
        return super.queryById(itemCatId);
    }

    /**
     * 把从数据库中查询出来的数据，变成json格式的树形数据
     */
    @Override
    public ItemCatResult queryAllToTree() {
        ItemCatResult itemCatResult = new ItemCatResult();
        List<ItemCat> itemCats = super.queryAll();
        //缓存逻辑不能影响业务逻辑，无论缓存逻辑怎么出异常，都不能影响业务逻辑
        try {
            // 先从缓存中命中，如果命中，直接返回，如果没有命中，程序继续往下执行
            String cacheData = this.redisService.get(KEY);
            if (StringUtils.isNotEmpty(cacheData)) {
                return MAPPER.readValue(cacheData, ItemCatResult.class);
            }
        } catch (Exception e) {//使用Exception可以接受所有的异常
            e.printStackTrace();
        }
        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
        for (ItemCat itemCat : itemCats) {
            if (!itemCatMap.containsKey(itemCat.getParentId())) {
                itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "<a/>");
            itemCatResult.getItemCats().add(itemCatData);
            if (!itemCat.getIsParent()) {
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatDatas2 = new ArrayList<ItemCatData>();
            itemCatData.setItems(itemCatDatas2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData itemCatData2 = new ItemCatData();
                itemCatData2.setName(itemCat2.getName());
                itemCatData2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatDatas2.add(itemCatData2);
                if (itemCat2.getIsParent()) {
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatDatas3 = new ArrayList<String>();
                    itemCatData2.setItems(itemCatDatas3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatDatas3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                    }
                }
            }
            if (itemCatResult.getItemCats().size() >= 14) {
                break;
            }
        }

        try {
            this.redisService.set(KEY, MAPPER.writeValueAsString(itemCatResult),SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemCatResult;
    }
}

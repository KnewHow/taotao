package cn.itcast.solr.test;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.solrj.pojo.Item;

public class ItemDataTest {

    private HttpSolrServer httpSolrServer;

    /**
     * 初始化httpSolrServlet的参数
     */
    @Before
    public void setUp() {
        // http://solr.taotao.com/#/taotao --界面地址
        String url = "http://solr.taotao.com/taotao";// 服务地址

        // 定义httpSolrServlet
        httpSolrServer = new HttpSolrServer(url);

        // 设置响应解析器为xml，但是最好是json，但是solr不支持json
        httpSolrServer.setParser(new XMLResponseParser());

        // 设置重试次数，最大为1
        httpSolrServer.setMaxRetries(1);

        // 建立连接的最长时间
        httpSolrServer.setConnectionTimeout(500);
    }

    @Test
    public void testInsert() throws Exception {
        Item item = new Item();
        item.setCid(1L);
        item.setId(999L);
        item.setImage("image");
        item.setPrice(100L);
        item.setSellPoint("很好啊，赶紧来买吧.");
        item.setStatus(1);
        item.setTitle("飞利浦 老人手机 (X2560) 深情蓝 移动联通2G手机 双卡双待");
        this.httpSolrServer.addBean(item);
        this.httpSolrServer.commit();
    }

    @Test
    public void testUpdate() throws Exception {
        Item item = new Item();
        item.setCid(1L);
        item.setId(999L);
        item.setImage("image");
        item.setPrice(100L);
        item.setSellPoint("很好啊，赶紧来买吧. 豪啊");
        item.setStatus(1);
        item.setTitle("飞利浦 老人手机 (X2560) 深情蓝 移动联通2G手机 双卡双待");

        this.httpSolrServer.addBean(item);
        this.httpSolrServer.commit();
    }

    @Test
    public void testDelete() throws Exception {
        this.httpSolrServer.deleteById("999");
        this.httpSolrServer.commit();
    }

    @Test
    public void testQuery() throws Exception {
        int rows = 1;
        int page = 2;

        String keywords = "手机";

        // 构造搜索条件
        SolrQuery solrQuery = new SolrQuery();

        // 设置分页
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
        solrQuery.setRows(rows);

        // 是否需要高亮
        boolean isHighlighting = !StringUtils.equals("*", keywords) && StringUtils.isNotEmpty(keywords);

        if (isHighlighting) {
            // 设置高亮
            solrQuery.setHighlight(true); // 开启高亮组件
            solrQuery.addHighlightField("title");// 高亮字段
            solrQuery.setHighlightSimplePre("<em>");// 标记，高亮关键字前缀
            solrQuery.setHighlightSimplePost("</em>");// 后缀
        }
        
        //执行查询
        QueryResponse queryResponse = this.httpSolrServer.query(solrQuery);
        
        List<Item> items = queryResponse.getBeans(Item.class);
        
        if (isHighlighting) {
            // 将高亮的标题数据写回到数据对象中
            Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
            for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
                for (Item item : items) {
                    if (!highlighting.getKey().equals(item.getId().toString())) {
                        continue;
                    }
                    item.setTitle(StringUtils.join(highlighting.getValue().get("title"), ""));
                    break;
                }
            }
        }
        
        for (Item item : items) {
            System.out.println(item);
        }
    }

        

}

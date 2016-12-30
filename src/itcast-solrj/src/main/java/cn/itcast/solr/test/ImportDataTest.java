package cn.itcast.solr.test;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.junit.Test;

import cn.itcast.solrj.pojo.Item;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImportDataTest {

    private HttpSolrServer httpSolrServer;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    
    @Test
    public void testInport() throws Exception {
        
        String url1 = "http://solr.taotao.com/taotao";// 服务地址

        // 定义httpSolrServlet
        httpSolrServer = new HttpSolrServer(url1);
        
        String url = "http://manage.taotao.com:81/rest/item?page={page}&rows=100";
        int page = 1;
        int pageSize = 0;
        do {
            String u = StringUtils.replace(url, "{page}", "" + page);
            String jsonData = doGet(u);
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            String rowStr = jsonNode.get("rows").toString();
            List<Item> items = MAPPER.readValue(rowStr, MAPPER.getTypeFactory().constructParametricType(List.class, Item.class));
            page++;
            pageSize=items.size();
            this.httpSolrServer.addBeans(items);
            this.httpSolrServer.commit();
        } while (pageSize==100);
    }

    private String doGet(String url) throws Exception {
        // 创建Httpclient对象，相当于打开浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建http GET请求，相当于在浏览器地址栏中输入请求链接地址
        HttpGet httpGet = new HttpGet(url);

        //
        CloseableHttpResponse response = null;
        try {
            // 执行请求,相当于按下回车键
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return content;
            }
        } finally {
            // 关闭资源
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }

        return null;

    }
}

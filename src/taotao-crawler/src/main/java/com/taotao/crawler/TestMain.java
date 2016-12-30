package com.taotao.crawler;

import javax.print.Doc;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestMain {

    public static final String BASE_URL = "https://list.jd.com/list.html?cat=9987,653,655&page={page}&go=0&trans=1&JL=6_0_0&ms=6#J_main";

    public static void main(String[] args) throws Exception {
        String url = StringUtils.replace(BASE_URL, "{page}", 1 + "");
        String html = doGet(url);
        Document document = Jsoup.parse(html);
        String pageText = document.select("#J_topPage").text();
        System.out.println(pageText);
        String[] strs = pageText.split("\\D+");
        Integer totalPage = Integer.parseInt(strs[1]);

        for (int i = 1; i <= totalPage; i++) {
            String u = StringUtils.replace(BASE_URL, "{page}", String.valueOf(i));
            String content = doGet(u);
            Document root = Jsoup.parse(content);
            Elements lis = root.select("#plist li");
            for (Element li : lis) {
                Element div = li.child(0);
                //System.out.println(div.attr("data-sku"));
                Long id = 0L;
                if(!StringUtils.isEmpty(div.attr("data-sku"))){
                     id= Long.valueOf(div.attr("data-sku"));
                }
                String image = li.select(".p-img img").attr("data-lazy-img");
                System.out.println(id+":"+image);
            }
            break;
        }
    }

    public static String doGet(String url) throws Exception {

        // 创建httpClient对象，相当于打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建 http Get请求，相当于在浏览器地址栏输入地址
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse httpResponse = null;

        try {
            // 执行http请求，相当于按下回车键
            httpResponse = httpClient.execute(httpGet);
            // 如果响应状态码为200，即响应成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 把响应的内容变成字符串的形式
                String content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                return content;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后释放资源
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpClient.close();
        }
        return null;

    }
}

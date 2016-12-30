package cn.itcast.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DoGET {

    public static void main(String[] args) throws Exception {

        // 创建Httpclient对象，相当于打开浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建http GET请求，相当于在浏览器地址栏中输入请求链接地址
        HttpGet httpGet = new HttpGet("http://manage.taotao.com:81/rest/content?categoryId=3&page=1&rows=6");

        //
        CloseableHttpResponse response = null;
        try {
            // 执行请求,相当于按下回车键
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("内容长度："+content);
            }
        } finally {
            //关闭资源
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }

    }

}

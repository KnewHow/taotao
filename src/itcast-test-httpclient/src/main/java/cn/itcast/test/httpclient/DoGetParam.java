package cn.itcast.test.httpclient;

import java.net.URI;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DoGetParam {

    public static void main(String[] args) throws Exception {
        //创建HttpClient对象，相当于打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        CloseableHttpResponse httpResponse = null;
        
       try {
        URI uri = new URIBuilder("http://manage.taotao.com:81/rest/content").setParameter("categoryId", "3")
                    .setParameter("page", "1").setParameter("rows", "20").build();
        //打印出URI
        System.out.println(uri);
        
        //创建get请求 httpGet请求对象。想当于在浏览器地址栏输入请求地址
        HttpGet httpGet = new HttpGet(uri);
        
        //执行请求，相当于子按下回车键
        httpResponse =  httpClient.execute(httpGet);
        //判断状态码是否为200
        if(httpResponse.getStatusLine().getStatusCode()==200){
            //以字符串的形式获取返回的内容
            String content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            System.out.println(content);
        }
        
    } catch (Exception e) {
        e.printStackTrace();
    }finally{
        //是否资源
        if(httpResponse!=null){
            httpResponse.close();
        }
        httpClient.close();
    }
           
    }
}

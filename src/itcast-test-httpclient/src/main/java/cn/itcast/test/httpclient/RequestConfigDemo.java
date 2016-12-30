package cn.itcast.test.httpclient;

import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RequestConfigDemo {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        HttpGet httpGet = new HttpGet("http://www.taotao.com:8081/");
        
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)//创建连接的最大时间
                .setConnectionRequestTimeout(500)//从连接池中获取连接的最长时间
                .setSocketTimeout(10*1000)//数据传输的最长时间
                .setStaleConnectionCheckEnabled(true).build();//提交请求前，测试请求是否可用。true为测试，false为不测试
        httpGet.setConfig(requestConfig);
        
        CloseableHttpResponse httpResponse = null;
        
        try {
            httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                String content = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
                System.out.println(content); 
            }
        } catch (Exception e) {
           e.printStackTrace();
        }finally{
            if(httpResponse!=null){
                httpResponse.close();
            }
            httpClient.close();
        }
    }
}

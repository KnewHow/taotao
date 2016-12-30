package cn.itcast.test.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 带有参数的post请求
 * @author Administrator
 *
 */
public class DoPostParam {

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        HttpPost httpPost = new HttpPost("http://www.taotao.com:8083/");
        
        //设置参数
        List<NameValuePair> params = new ArrayList<NameValuePair>(0);
        params.add(new BasicNameValuePair("id", "1"));
        //构造一个form表单的实体
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params);
        //将实体映射到post请求中
        httpPost.setEntity(encodedFormEntity);
        
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
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

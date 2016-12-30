package cn.itcast.test.httpclient;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 无参数的post请求
 * @author Administrator
 *
 */
public class DoPost {

    public static void main(String[] args) throws Exception {
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        HttpPost httpPost = new HttpPost("http://www.taotao.com:8083/");
        
        CloseableHttpResponse httpResponse = null;
        
        try {
            httpResponse =  httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                String content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                System.out.println(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpClient.close();
        }
                
                
    }
}

package cn.itcast.test.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * 在请求地址的是，每次都打开或者关闭浏览器，这样做会很影响效率
 * 我们可以使用类似数据库连接池的方法，来动态的管理请求
 * @author Administrator
 *
 */
public class HttpConnectManager {
    
    public static void main(String[] args) throws Exception {
        //创建连接管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        cm.setMaxTotal(200);
        //设置每个主机的并发数
        cm.setDefaultMaxPerRoute(20);
        
        doGet(cm);
        doGet(cm);
    }

    public static void doGet(HttpClientConnectionManager cm) throws Exception{
        //从连接管理器中获取连接
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        
        HttpGet httpGet = new HttpGet("http://www.taotao.com:8083/");
        
        CloseableHttpResponse httpResponse = null;
        
        try {
            httpResponse = httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200){
                String content = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
                System.out.println("内容长度"+content.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(httpResponse!=null){
                httpResponse.close();
            }
            //不能关闭浏览器对象，因为如果关闭浏览器，那么内置的连接管理器对象就消失
            //httpClient.close();
        }
    }
}

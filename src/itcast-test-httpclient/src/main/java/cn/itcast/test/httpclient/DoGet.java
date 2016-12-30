package cn.itcast.test.httpclient;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DoGet {

    public static void main(String[] args) throws Exception {
        
        //创建httpClient对象，相当于打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建 http Get请求，相当于在浏览器地址栏输入地址
        HttpGet httpGet = new HttpGet("http://manage.taotao.com:81/rest/content?categoryId=3&page=1&rows=6");
        
        CloseableHttpResponse httpResponse = null;
        
        try {
            //执行http请求，相当于按下回车键
            httpResponse = httpClient.execute(httpGet);
            //如果响应状态码为200，即响应成功
            if(httpResponse.getStatusLine().getStatusCode()==200){
                //把响应的内容变成字符串的形式
                String content = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                System.out.println(content);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //最后释放资源
            if(httpResponse!=null){
                httpResponse.close();
            }
            httpClient.close();
        }
        
    }
}

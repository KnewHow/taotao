package com.taotao.common.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.httpclient.HttpResult;

@Service
public class APIService implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired(required = false)
    private RequestConfig requestConfig;

    /**
     * 无参的get请求，如果请求成功，返回请求数据，否则返回null
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url) throws ClientProtocolException, IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = this.getHttpClient().execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            }
        } catch (Exception e) {

        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
        return null;
    }

    /**
     * 带有参数的get请求，请求成功，返回string类型的结果，否则就返回null
     * 
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url, Map<String, String> params) throws URISyntaxException,
            ClientProtocolException, IOException {
        URIBuilder builder = new URIBuilder(url);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return doGet(builder.toString());
    }

    /**
     * post请求，返回状态码和响应的数据
     * 
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> params) throws ClientProtocolException,
            IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (params != null) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 构造一个form表单的实体
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(parameters);
            // 将实体映射到post请求中
            httpPost.setEntity(encodedFormEntity);
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = this.getHttpClient().execute(httpPost);
            return new HttpResult(httpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(
                    httpResponse.getEntity(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
        return null;
    }

    /**
     * 发送post的json请求
     * @param url
     * @param json
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPostJson(String url, String json) throws ClientProtocolException,
            IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (json != null) {
            // 构造一个form表单的实体
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 将实体映射到post请求中
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = this.getHttpClient().execute(httpPost);
            return new HttpResult(httpResponse.getStatusLine().getStatusCode(), EntityUtils.toString(
                    httpResponse.getEntity(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
        return null;
    }

    /**
     * 这样可以保证HttpClient是多例的，每次从工厂创建
     * 
     * @return
     */
    private CloseableHttpClient getHttpClient() {
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}

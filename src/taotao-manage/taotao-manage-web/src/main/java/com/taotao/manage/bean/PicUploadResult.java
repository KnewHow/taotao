package com.taotao.manage.bean;
/**
 * 返回给页面上传组件返回的图片数据
 * @author Administrator
 *
 */
public class PicUploadResult {

    //用户给上传组件判断是上传失败还是成功0:表示成功，1：表示失败
    private Integer error;
    
    //图片的url地址
    private String url;
    
    //图片的宽度
    private String width;
    
    //图片的高度
    private String height;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "PicUploadResult [error=" + error + ", url=" + url + ", width=" + width + ", height=" + height
                + "]";
    }
    
}

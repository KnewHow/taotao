package com.taotao.practice.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * cookie工具类
 * @author Administrator
 *
 */
public class CookieUtils {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CookieUtils.class);

    private static final String DEFAULT_ENCODESTRING = "UTF-8";

    
    /**
     * 得到cookie的值，不编码
     * @param request
     * @param cookieName
     * @return
     */
    public String getCookieValue(HttpServletRequest request, String cookieName){
        return getCookieValuse(request, cookieName, false);
    }
    
    /**
     * 指定使用默认的编码方式根据cookieName或者cookieValue
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param isEncode
     * @return
     */
    public static  String getCookieValuse(HttpServletRequest request, String cookieName,
            boolean isEncode) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || StringUtils.isEmpty(cookieName)) {
            return null;
        }
        String cookieValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isEncode) {
                        cookieValue = URLDecoder.decode(cookieList[i].getValue(), DEFAULT_ENCODESTRING);
                    } else {
                        cookieValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Cookie Encode Error.", e);
            e.printStackTrace();
        }
        return cookieValue;
    }

    /**
     * 指定使用指定的编码方式根据cookieName或者cookieValue
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param encodeString
     * @return
     */
    public static final String getCookieValuse(HttpServletRequest request, String cookieName,
            String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || StringUtils.isEmpty(cookieName) || StringUtils.isBlank(encodeString)) {
            return null;
        }
        String cookieValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    cookieValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Cookie Encode Error.", e);
            e.printStackTrace();
        }
        return cookieValue;
    }

    /**
     * 设置cookie的值，不设置有效时间，也不进行编码
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }

    /**
     * 设置cookie的值，指定生效时间但不编码
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
    }

    /**
     * 设置cookie的值，不设置生效时间但是进行编码
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param isEncode
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }

    /**
     * 设置cookie的值，指定生效时间和是否使用默认编码
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param isEncode
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
    }

    /**
     * 设置cookie的值，指定生效时间和编码
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param encodeString
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
    }

    /**
     * 删除名称为cookieName的cookie
     * 
     * @param request
     * @param response
     * @param cookieName
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
            String cookieName) {
        doSetCookie(request, response, cookieName, "", -1, false);
    }

    /**
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage cookie的最大生存周期
     * @param isEncode 是否进行默认编码
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
            String cookieName, String cookieValue, int cookieMaxage, boolean isEncode) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncode) {
                cookieValue = URLEncoder.encode(cookieValue, DEFAULT_ENCODESTRING);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage > 0) {
                cookie.setMaxAge(cookieMaxage);
            }
            if (request != null) {
                // 实现cookie的跨域共享
                cookie.setDomain(getDomainName(request));
            }
            // 使得cookie在同一个应用服务和其他webApp共享
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("Cookie Encode Error.", e);
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage cookie的最大生存周期
     * @param encodeString cookie的编码方式
     */
    private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response,
            String cookieName, String cookieValue, int cookieMaxage, String encodeString) {
        try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (!StringUtils.isBlank(encodeString)) {
                cookieValue = URLEncoder.encode(cookieValue, encodeString);
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if (cookieMaxage > 0) {
                cookie.setMaxAge(cookieMaxage);
            }
            if (request != null) {
                // 实现cookie的跨域共享
                cookie.setDomain(getDomainName(request));
            }
            // 使得cookie在同一个应用服务和其他webApp共享
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("Cookie Encode Error.", e);
        }
    }

    /**
     * 根据request来获取域名
     * 
     * @param request
     * @return
     */
    private static final String getDomainName(HttpServletRequest request) {

        String domainName = null;

        String serverName = request.getRequestURL().toString();
        int splitNumber = 7;
        if (serverName.contains("https")) {
            splitNumber = 8;
        }

        if (serverName == null || StringUtils.isBlank(serverName)) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(splitNumber);
            final int endIndex = serverName.indexOf("/");
            serverName = serverName.substring(0, endIndex);
            final String[] domains = StringUtils.split(serverName, ".");
            int len = domains.length;
            if (len > 3) {
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            domainName.split(":");
            String[] array = StringUtils.split(domainName, ":");
            domainName = array[0];
        }
        return domainName;
    }

}

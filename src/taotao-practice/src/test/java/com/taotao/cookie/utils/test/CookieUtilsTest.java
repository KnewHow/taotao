package com.taotao.cookie.utils.test;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class CookieUtilsTest {

    @Test
    public void test_1() {
        String url = "http://items.alitrip.com/item.htm?spm=181.7091613.174892.2.ijViR8&id=43989664525&t_trace_id=0ab0f933fefe0ab0f933ccdac1acf2493t1dd7&scm=20140633.10055.0.0.43989664525";
        String domain = getDomainName(url);
        System.out.println(domain);
    }

    private static final String getDomainName(String url) {
        String domainName = null;

        String serverName = url;
        int splitNumber = 7;
        if (StringUtils.contains(serverName, "https")) {
            splitNumber = 8;
        }
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(splitNumber);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }
}

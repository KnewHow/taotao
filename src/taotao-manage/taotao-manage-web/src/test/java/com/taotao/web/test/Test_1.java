package com.taotao.web.test;

import java.util.Date;

import org.junit.Test;

public class Test_1 {

    @Test
    public void test_1() throws InterruptedException{
        Date nowDate = new Date();
        System.out.println(nowDate.toString());
        Thread.sleep(4000);
        System.out.println(nowDate.toString());
        System.out.println(new Date().toString());
    }
}

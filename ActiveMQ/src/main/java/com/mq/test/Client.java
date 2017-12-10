package com.mq.test;

import org.apache.commons.httpclient.HttpClient;

import org.junit.Test;

import com.mq.thread.Sender;



/**
 * Created by Administrator on 2017/1/5.
 */

public class Client {

    @Test
    public void test() {
        
        HttpClient httpClient = new HttpClient();
        Thread t=new Thread(new Sender(httpClient));
        t.start();
        
    }

}

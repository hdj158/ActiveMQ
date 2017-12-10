package com.mq.thread;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class Sender implements Runnable {

	public static AtomicInteger count = new AtomicInteger(0);
    
    private HttpClient httpClient;

    public Sender(HttpClient client) {
        httpClient = client;
        
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
            System.out.println(Thread.currentThread().getName()+"---Send message-"+count.getAndIncrement());
            PostMethod post = new PostMethod("http://localhost:8088/ActiveMQ/login.jsp");
            NameValuePair[] data = { 
            		new NameValuePair("userName", "123"),
            		new NameValuePair("userBill", "123") ,
            		new NameValuePair("password", "123"),
            		new NameValuePair("mail", "13350780038@qq.com")};
            post.setRequestBody(data);
 
            httpClient.executeMethod(post);
            System.out.println(Thread.currentThread().getName()+"---Send message Success-"+count.getAndIncrement());

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}

package com.mq.controller;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocketTest {
	 private static final String GUEST_PREFIX = "Guest"; 
	 
	 private static final AtomicInteger connectionIds = new AtomicInteger(0); 
	 
	 private static int onlineCount = 0;
	
	 //private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();

	 private static ConcurrentHashMap<String, WebSocketTest> webSocketmap = new ConcurrentHashMap<String, WebSocketTest>();
	 
	 private String userno = "";
	 
	 private Session session;

	 private static HttpSession httpSession;  
	 
	 public WebSocketTest() {  
		 userno = GUEST_PREFIX + connectionIds.getAndIncrement();  
	    }  
	 @OnOpen
	 public void onOpen(Session session, EndpointConfig config){
	          this.session = session;
	          
	          httpSession = (HttpSession) config.getUserProperties().get(  
	                  HttpSession.class.getName());
	          
	            System.out.println(httpSession.getAttributeNames());
	        	 
	         
	         //webSocketSet.add(this);     //加入set中
	         webSocketmap.put(userno, this);
	          addOnlineCount();           //在线数加1
	          System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	      }
	 @OnClose
	 public void onClose(){
	         // webSocketSet.remove(this);  //从set中删除
	          webSocketmap.remove(userno);
	          subOnlineCount();           //在线数减1
	          System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	      }
	  
	      /**
	       * 收到客户端消息后调用的方法
	       * @param message 客户端发送过来的消息
	       * @param session 可选的参数
	       */
	      @OnMessage
	      public void onMessage(String message, Session session) {
	         System.out.println("来自客户端的消息:"+session +"-----"+ message);
	         if (getOnlineCount()>1) {
	             sendAll(message);
	         } else {
	             //给指定的人发消息
	             sendToUser(message);
	         }
	         
	         
	     }
	  
	     /**
	      * 发生错误时调用
	       * @param session
	       * @param error
	       */
	      @OnError
	      public void onError(Session session, Throwable error){
	          System.out.println("发生错误");
	        error.printStackTrace();
	      }
	      
	      /**
	       * 给所有人发消息
	       * @param message
	       */
	      private void sendAll(String message) {
	    	  
	          String sendMessage = message.split("[|]")[0];
	          //遍历HashMap
	          for (String key : webSocketmap.keySet()) {
	              try {
	                  //判断接收用户是否是当前发消息的用户
	                  if (!userno.equals(key)) {
	                	  webSocketmap.get(key).sendMessage("用户" + userno + "发来消息：" + " <br/> " + sendMessage);
	                      System.out.println("key = " + key);
	                  }
	              } catch (IOException e) {
	                  e.printStackTrace();
	              }
	          }
	      }
	      /**
	       * 给指定的人发送消息
	       * @param message
	       */
	      private void sendToUser(String message) {
	          String sendUserno = message.split("[|]")[1];
	          String sendMessage = message.split("[|]")[0];
	          try {
	              if (webSocketmap.get(sendUserno) != null) {
	            	  webSocketmap.get(sendUserno).sendMessage("用户" + userno + "发来消息：" + " <br/> " + sendMessage);
	              } else {
	                  System.out.println("当前用户不在线");
	              }
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	      }
	      public void sendMessage(String message) throws IOException {
	          this.session.getBasicRemote().sendText(message);
	          //this.session.getAsyncRemote().sendText(message);
	      }
	    	   
	    	       public static synchronized int getOnlineCount() {
	    	           return onlineCount;
	    	       }
	    	   
	    	       public static synchronized void addOnlineCount() {
	    	           WebSocketTest.onlineCount++;
	    	       }
	    	   
	    	       public static synchronized void subOnlineCount() {
	    	           WebSocketTest.onlineCount--;
	    	       }
}

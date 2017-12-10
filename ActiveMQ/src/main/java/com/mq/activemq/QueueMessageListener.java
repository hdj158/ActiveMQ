package com.mq.activemq;

import javax.annotation.Resource;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.mq.beans.User;
import com.mq.mail.MailSend;




public class QueueMessageListener implements MessageListener {

	@Resource
	private MailSend mail;
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if(message instanceof MapMessage){  
            try {  
            	MapMessage map=(MapMessage) message;  
                System.out.println("QueueMessageListener监听到了消息："+map);  
                User user=new User();
                user.setUserName(map.getString("userName"));
                user.setUserBill(map.getString("userBill"));
                user.setPassword(map.getString("password"));
                user.setMail(map.getString("mail"));
                System.out.println(user);
                mail.sendbean(user);
            } catch (Exception e) {  
            	 System.out.println("QueueMessageListener接收消息时发生异常："+e);       
            }  
              
        }  
	}

}

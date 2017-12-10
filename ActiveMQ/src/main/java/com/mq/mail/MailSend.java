package com.mq.mail;

import javax.annotation.Resource;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.mq.beans.User;





@Component  
public class MailSend {  
	@Resource
    private JavaMailSenderImpl email;  
	@Resource
    private SimpleMailMessage message;  
       
    //发送邮件  
    public void sendbean(User user){   
    	System.out.println("发送邮件中！！！！");
    	try {
    		message.setTo(user.getMail());  
            message.setSubject("队列测试邮件");  
            message.setText("您好"+user.getUserName()+"!  您的账号为:"+user.getUserBill()+"密码:"+user.getPassword()+"。请妥善保管您的账号和密码!");  
            email.send(message);
            System.out.println("发送邮件成功！！！！");
    	} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
       
    }  
}  


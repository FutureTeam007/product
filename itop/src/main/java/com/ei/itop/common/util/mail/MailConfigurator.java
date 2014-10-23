package com.ei.itop.common.util.mail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件配置类
 * @author vintin
 * @Configuration
 */
public class MailConfigurator {

	//从配置文件中读取相应的邮件配置属性
    private @Value("${account1.host}") String emailHost;
    private @Value("${account1.username}") String userName;
    private @Value("${account1.password}") String password;
    private @Value("${account1.smtp.auth}") String mailAuth;
    
    //JavaMailSender用来发送邮件的类
    public @Bean JavaMailSender  mailSender(){
       JavaMailSenderImpl ms = new JavaMailSenderImpl();
       ms.setHost(emailHost);
       ms.setUsername(userName);
       ms.setPassword(password);
       Properties pp = new Properties();
       pp.setProperty("mail.smtp.auth", mailAuth);
       ms.setJavaMailProperties(pp);
       return ms;   
    }  
	
}

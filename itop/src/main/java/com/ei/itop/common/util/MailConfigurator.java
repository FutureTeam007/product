package com.ei.itop.common.util;

import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.ailk.dazzle.util.sec.Encrypt;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;

/**
 * 邮件配置类
 * @author vintin
 * @Configuration
 */
@Configuration
public class MailConfigurator {

	@Resource(name = "mailSendConfig")
	Map<String,String> mailSendConfig;
	
    public @Bean JavaMailSender  mailSender(){
       JavaMailSenderImpl ms = new JavaMailSenderImpl();
       ms.setHost(mailSendConfig.get("mail.host"));
       ms.setPort(VarTypeConvertUtils.string2Integer(mailSendConfig.get("mail.port"),25));
       ms.setUsername(mailSendConfig.get("mail.username"));
       ms.setPassword(Encrypt.decrypt(mailSendConfig.get("mail.password")));
       Properties pp = new Properties();
       pp.setProperty("mail.smtp.auth", mailSendConfig.get("mail.smtp.auth"));
       ms.setJavaMailProperties(pp);
       return ms;
    }  
	
}

package com.example.Licence.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.Licence.Management.entity.EmailStructure;

@Service
public class EmailService {

	@Value("$(spring.mail.username)")
	String fromMail;
	
	@Autowired
	private JavaMailSender mailSender;
	

	
	public void sendEmail(String toEmail ,
			              String subject,
			              String body) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("sivabaskarn2003@gmail.com");
		mailMessage.setTo(toEmail);
		mailMessage.setText(body);
		mailMessage.setSubject(subject);
		
		mailSender.send(mailMessage);
		}

	public void sendMail(String mail, EmailStructure emailStructure) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setFrom(fromMail);
		mailMessage.setSubject(emailStructure.getSubject());
		mailMessage.setText(emailStructure.getMessage());
		mailMessage.setTo(mail);
		
		mailSender.send(mailMessage);
	}
}

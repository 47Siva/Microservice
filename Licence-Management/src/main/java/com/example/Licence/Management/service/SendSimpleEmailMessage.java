package com.example.Licence.Management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
public class SendSimpleEmailMessage {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void simpleMailsend(String toEmail ,
			              String subject,
			              String body) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(toEmail);
		mailMessage.setText(body);
		mailMessage.setSubject(subject);
		
		javaMailSender.send(mailMessage);
	}
}

package com.example.Licence.Management.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
public class SendSimpleEmailMessage {

	@Autowired
	private JavaMailSender javaMailSender;

	public void simpleMailSend(String toEmail, String subject, String body) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(toEmail);
		mailMessage.setText(body);
		mailMessage.setSubject(subject);

		javaMailSender.send(mailMessage);
	}

	public void MimeMessageSend(String toEmail, String subject, String body, MimeMessage message,String attachment)
			throws MessagingException {
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(toEmail);
		helper.setSubject(subject);
		helper.setText(body);
		FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));

		helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

		javaMailSender.send(message);
	}
}

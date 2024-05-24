package com.example.Licence.Management.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Licence.Management.dto.EmailStructure;
import com.example.Licence.Management.service.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/mail")
public class EmailController {

	@Autowired
	EmailService emailService;
	
    @GetMapping("/sendemail")
    public String sendEmail(@RequestParam String toEmail, @RequestParam String subject, @RequestParam String body) {
        emailService.sendEmail(toEmail, subject, body);
        return "Email sent successfully!";
    }
    
    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody EmailStructure emailStructure) {
    	emailService.sendMail(mail,emailStructure);
		return "Successfully sent the mail..!";
    	
    }

    @PostMapping("/send")
    public String sendMail(
            @RequestParam("toEmail") String toEmail,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam("attachment") String attachment) throws MessagingException {
        emailService.mailsend(toEmail, subject, body, attachment);
        
        return "Successfully sent the mail..!";
    }
}

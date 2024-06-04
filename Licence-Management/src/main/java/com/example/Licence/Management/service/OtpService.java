package com.example.Licence.Management.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Licence.Management.dto.OtpResponse;
import com.example.Licence.Management.dto.ValidateOtpRequest;
import com.example.Licence.Management.entity.Otp;
import com.example.Licence.Management.repository.OtpRepository;

@Service
public class OtpService {

	@Autowired
	OtpRepository otpRepository;
	
	@Autowired
	private SendSimpleEmailMessage sendSimpleEmailMessage;

	private final SecureRandom secureRandom = new SecureRandom();

	public int generateOtp() {
		return 100000 + secureRandom.nextInt(900000); // Generate a 6-digit OTP
	}

	public ResponseEntity<?> sendOtp( String email) {
		try {
	        int otpCode = generateOtp();
			LocalTime expiryTime =  LocalTime.now().plusMinutes(1);
			 Optional<Otp> findbyemail = otpRepository.findByEmail(email);
			 if (findbyemail.isPresent()) {
		        	Otp OTP = findbyemail.get();
		        	OTP.setOtpCode(String.valueOf(otpCode));
		        	OTP.setExpiryTime(expiryTime);
		        	otpRepository.save(OTP);
		        }else {
			  Otp otp = Otp.builder()
					  .email(email)
					  .expiryTime(expiryTime)
					  .otpCode(String.valueOf(otpCode)).build();
		        otpRepository.save(otp);
		        }
			String subject = "Your OTP Code";
			String body = "Your OTP is: " + otpCode;
			sendSimpleEmailMessage.simpleMailSend(email,subject,body);
		
			OtpResponse response = OtpResponse.builder().message("OTP sent successfully").otp(otpCode).build();
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			throw new RuntimeException("Failed to send OTP..! Please Enter a Valid Email");
		}
	}
    
    public ResponseEntity<?> otpvalidate(ValidateOtpRequest validateOtpRequest){
    	
    	String otpCode =String.valueOf(validateOtpRequest.getOtp());
    	String email = validateOtpRequest.getEmail();
    	Optional<Otp> Otp = otpRepository.findByOtpCodeAndEmail(otpCode,email);
    	 
    	 if (Otp.isPresent()) {
             Otp otp = Otp.get();
             if (otp.getExpiryTime().isAfter(LocalTime.now())) {
                 return ResponseEntity.ok("OTP is valid.");
             } else {
                 return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("OTP is expired.");
             }
         } else {
             return ResponseEntity.badRequest().body("Incorrect OTP.");
         }
    }
    
    
}

package com.example.Licence.Management.service;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.Licence.Management.dto.OtpDetails;
import com.example.Licence.Management.dto.OtpResponse;
import com.example.Licence.Management.dto.ValidateOtpRequest;

@Service
public class OtpService {

	@Autowired
	private JavaMailSender javaMailSender;

	private final SecureRandom secureRandom = new SecureRandom();
    private final ConcurrentHashMap<String, OtpDetails> otpStorage = new ConcurrentHashMap<>();
	private static final long OTP_VALID_DURATION = 1 * 60 * 1000; // 5 minutes in milliseconds

	public int generateOtp() {
		return 100000 + secureRandom.nextInt(900000); // Generate a 6-digit OTP
	}

	public ResponseEntity<?> sendOtp(String email, int otp) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("Your OTP Code");
			message.setText("Your OTP is: " + otp);
			javaMailSender.send(message);
			
            OtpDetails otpDetails = new OtpDetails(otp, System.currentTimeMillis());
            otpStorage.put(email, otpDetails);

			OtpResponse response = OtpResponse.builder().message("OTP sent successfully").otp(otp).build();
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			throw new RuntimeException("Failed to send OTP..! Please Check your Email");
		}
	}
	

	   public boolean isvalidateOtp(String email, int otp) {
	        OtpDetails otpDetails = otpStorage.get(email);

	        if (otpDetails == null) {
	            return false; // OTP not found
	        }

	        if (System.currentTimeMillis() - otpDetails.getTimestamp() > OTP_VALID_DURATION) {
	            otpStorage.remove(email); // OTP expired
	            return false; // OTP expired
	        }

	        if (otpDetails.getOtp() != otp) {
	            return false; // Invalid OTP
	        }

	        otpStorage.remove(email); // OTP is valid, remove it after use
	        return true;
	    }
    
    public ResponseEntity<?> otpvalidate(ValidateOtpRequest validateOtpRequest){
        String email = validateOtpRequest.getEmail();
        int otp = validateOtpRequest.getOtp();
        
        if (email == null || otp == 0) {
            OtpResponse response = OtpResponse.builder()
                    .message("Invalid Request..! Please Enter a Valid Email and OTP")
                    .otp(0)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
        
        boolean isValid = isvalidateOtp(email, otp);

        if (isValid) {
            OtpResponse response = OtpResponse.builder()
                    .message("OTP is valid")
                    .otp(otp)
                    .build();
            return ResponseEntity.ok(response);
        } else {
            OtpResponse response = OtpResponse.builder()
                    .message("OTP is invalid or expired")
                    .otp(0)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}

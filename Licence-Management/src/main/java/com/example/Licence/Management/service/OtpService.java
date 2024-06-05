package com.example.Licence.Management.service;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Licence.Management.common.PasswordUtil;
import com.example.Licence.Management.dto.OtpRequest;
import com.example.Licence.Management.dto.OtpResponse;
import com.example.Licence.Management.dto.ResetPasswordRequest;
import com.example.Licence.Management.dto.ValidateOtpRequest;
import com.example.Licence.Management.entity.Otp;
import com.example.Licence.Management.entity.User;
import com.example.Licence.Management.repository.OtpRepository;
import com.example.Licence.Management.repository.UserRepository;

@Service
public class OtpService {

	@Autowired
	OtpRepository otpRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private SendSimpleEmailMessage sendSimpleEmailMessage;
	
	private final SecureRandom secureRandom = new SecureRandom();

	public int generateOtp() {
		return 100000 + secureRandom.nextInt(900000); // Generate a 6-digit OTP
	}

	public ResponseEntity<?> sendOtp( OtpRequest request) {
		try {
	        int otpCode = generateOtp();
			LocalTime expiryTime =  LocalTime.now().plusMinutes(1);
			 Optional<Otp> findbyemail = otpRepository.findByEmail(request.getEmail());
			 if (findbyemail.isPresent()) {
		        	Otp OTP = findbyemail.get();
		        	OTP.setOtpCode(String.valueOf(otpCode));
		        	OTP.setExpiryTime(expiryTime);
		        	otpRepository.save(OTP);
		        }else {
			  Otp otp = Otp.builder()
					  .email(request.getEmail())
					  .expiryTime(expiryTime)
					  .otpCode(String.valueOf(otpCode)).build();
		        otpRepository.save(otp);
		        
		        User user = User.builder()
		        		.email(request.getEmail())
		        		.password(PasswordUtil.getEncryptedPassword(request.getPassword()))
		        		.usrerName(request.getUserName())
		        		.build();
		        userRepository.save(user);
		        }
			String subject = "Your OTP Code";
			String body = "Your OTP is: " + otpCode;
			sendSimpleEmailMessage.simpleMailSend(request.getEmail(),subject,body);
		
			OtpResponse response = OtpResponse.builder().message("OTP sent successfully").otp(otpCode).build();
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			throw new RuntimeException("Failed to send OTP..! TryAgin Later");
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

	public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}

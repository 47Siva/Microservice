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

	public ResponseEntity<?> sendOtp(OtpRequest request) {
//		try {
			int otpCode = generateOtp();
			LocalTime expiryTime = LocalTime.now().plusMinutes(1);
			Optional<Otp> findbyemail = otpRepository.findByEmail(request.getEmail());
			Optional<User> useremail = userRepository.findByEmail(request.getEmail());
			if (findbyemail.isPresent()) {
				Otp OTP = findbyemail.get();
				OTP.setOtpCode(String.valueOf(otpCode));
				OTP.setExpiryTime(expiryTime);
				otpRepository.save(OTP);
			}else {
				Otp otp = Otp.builder().email(request.getEmail()).expiryTime(expiryTime).otpCode(String.valueOf(otpCode))
						.build();
				otpRepository.save(otp);
			}
			if(useremail.isPresent()) {
				User user = useremail.get();
				user.setEmail(request.getEmail());
				user.setUserName(request.getUserName());
				user.setPassword(request.getPassword());
			}else {
				User user = User.builder().email(request.getEmail()).password(PasswordUtil.getEncryptedPassword(request.getPassword()))
						.userName(request.getUserName()).build();
				userRepository.save(user);
			}
			String subject = "Your OTP Code";
			String body = "Your OTP is: " + otpCode;
			sendSimpleEmailMessage.simpleMailSend(request.getEmail(), subject, body);

			OtpResponse response = OtpResponse.builder().message("OTP sent successfully").otp(otpCode).build();
			return ResponseEntity.ok(response);
//		} catch (RuntimeException e) {
//			throw new RuntimeException("Failed to send OTP..! TryAgin Later");
//		}
	}

	public ResponseEntity<?> otpvalidate(ValidateOtpRequest validateOtpRequest) {
		try {
			String otpCode = String.valueOf(validateOtpRequest.getOtp());
			String email = validateOtpRequest.getEmail();
			Optional<Otp> Otp = otpRepository.findByOtpCodeAndEmail(otpCode, email);

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
		} catch (RuntimeException e) {
			throw new RuntimeException("Failed to validate OTP..! Try again later");
		}
	}

	public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
//		try {
			// Step 1: Verify user details and send OTP
			if (request.getStep().equals("REQUEST_OTP")) {
				Optional<Otp> findbyemail = otpRepository.findByEmail(request.getEmail());
				Optional<User> userOptional = userRepository.findByEmailAndUserName(request.getEmail(),
						request.getUserName());
				
				if (userOptional.isPresent()) {
					int otpCode = generateOtp();
					LocalTime expiryTime = LocalTime.now().plusMinutes(1); // 5 minutes expiry time
					if (findbyemail.isPresent()) {
						Otp OTP = findbyemail.get();
						OTP.setOtpCode(String.valueOf(otpCode));
						OTP.setExpiryTime(expiryTime);
						otpRepository.save(OTP);
					}else {
						ResponseEntity.badRequest().body("User Email is not valid please enter the rejester Email");
					}
					String email = request.getEmail();
					String subject = "Your OTP Code";
					String body = "Your OTP is: " + otpCode;
					sendSimpleEmailMessage.simpleMailSend(email, subject, body);

					OtpResponse response = OtpResponse.builder().message("OTP sent successfully").otp(otpCode).build();
					return ResponseEntity.ok(response);
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
				}
			}
			// Step 2: Validate OTP
			else if (request.getStep().equals("VALIDATE_OTP")) {
				String otpCode = String.valueOf(request.getOtp());
				String email = request.getEmail();
				Optional<Otp> otpOptional = otpRepository.findByOtpCodeAndEmail(otpCode, email);

				if (otpOptional.isPresent()) {
					Otp otp = otpOptional.get();
					if (otp.getExpiryTime().isAfter(LocalTime.now())) {
						return ResponseEntity.ok("OTP is valid.");
					} else {
						return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("OTP is expired.");
					}
				} else {
					return ResponseEntity.badRequest().body("Incorrect OTP.");
				}
			}
			// Step 3: Reset Password
			else if (request.getStep().equals("RESET_PASSWORD")) {
				String otpCode = String.valueOf(request.getOtp());
				String email = request.getEmail();
				Optional<Otp> otpOptional = otpRepository.findByOtpCodeAndEmail(otpCode, email);

				if (otpOptional.isPresent()) {
					Otp otp = otpOptional.get();
					if (otp.getExpiryTime().isAfter(LocalTime.now())) {
						Optional<User> userOptional = userRepository.findByEmail(email);
						if (userOptional.isPresent()) {
							User user = userOptional.get();
							user.setPassword(PasswordUtil.getEncryptedPassword(request.getNewPassword()));
							userRepository.save(user);

							String subject = "Reset Password";
							String body = "Password reset successfully.";
							sendSimpleEmailMessage.simpleMailSend(email, subject, body);

							return ResponseEntity.ok("Password reset successfully.");
						} else {
							return ResponseEntity.badRequest().body("User not found.");
						}
					} else {
						return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("OTP is expired.");
					}
				} else {
					return ResponseEntity.badRequest().body("Incorrect OTP.");
				}
			} else {
				return ResponseEntity.badRequest().body("Invalid step.");
			}
//		} catch (RuntimeException e) {
//			throw new RuntimeException("Failed to process request..! Try again later");
//		}
	}

}

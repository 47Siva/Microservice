package com.example.Licence.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Licence.Management.dto.OtpRequest;
import com.example.Licence.Management.dto.ResetPasswordRequest;
import com.example.Licence.Management.dto.ValidateOtpRequest;
import com.example.Licence.Management.service.OtpService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/otp")
public class OtpController {


    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequest request) {
        return  otpService.sendOtp(request);
    }
    
    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestBody ValidateOtpRequest validateOtpRequest) {

        return otpService.otpvalidate(validateOtpRequest);
    }
    
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPossword(@RequestBody ResetPasswordRequest request){
    	return otpService.resetPassword(request);
    }
}

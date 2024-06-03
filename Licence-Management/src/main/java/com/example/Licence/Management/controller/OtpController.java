package com.example.Licence.Management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Licence.Management.dto.OtpRequest;
import com.example.Licence.Management.dto.OtpResponse;
import com.example.Licence.Management.dto.ValidateOtpRequest;
import com.example.Licence.Management.service.OtpService;

@RestController
@RequestMapping("api/otp")
public class OtpController {


    @Autowired
    private OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequest otpRequest) {
        int otp = otpService.generateOtp();
        return  otpService.sendOtp(otpRequest.getEmail(), otp);
    }
    
    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestBody ValidateOtpRequest validateOtpRequest) {

        return otpService.otpvalidate(validateOtpRequest);
    }
}

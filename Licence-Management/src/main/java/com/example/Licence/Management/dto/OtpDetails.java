package com.example.Licence.Management.dto;

import lombok.Data;

@Data
public class OtpDetails {

    private int otp;
    private long timestamp;
   
    public OtpDetails(int otp, long timestamp) {
        this.otp = otp;
        this.timestamp = timestamp;
    }

}

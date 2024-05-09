package com.example.Licence.Management.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class LicenceKeyConfig {
	
	public String generateLicenceKey(UUID id ,String mailId , String companyName) {
        String data = id + mailId + companyName + System.currentTimeMillis(); // Combine company name with the current time
        String hash = LicenceKeyConfig.getSHA256(data); // Utilize the utility class for hashing 
        String rawUuid = hash.substring(0, 32);  // Ensure you have enough characters in the hash
        UUID uuid = UUID.fromString(
            rawUuid.substring(0, 8) + "-" +
            rawUuid.substring(8, 12) + "-" +
            rawUuid.substring(12, 16) + "-" +
            rawUuid.substring(16, 20) + "-" +
            rawUuid.substring(20, 32)
        );

        // Step 4: Return the UUID in uppercase
        return uuid.toString().toUpperCase();
    }
	

    public static String getSHA256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException  e) {
            throw new RuntimeException("Error creating SHA-256 hash", e);
        }
    }
}

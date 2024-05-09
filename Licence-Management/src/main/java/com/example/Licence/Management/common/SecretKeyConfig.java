package com.example.Licence.Management.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class SecretKeyConfig {
	
	    private static final String SECRET_KEY = "this is secret";
	    private static final String SALT = "12345678"; // You should use a more secure salt and store it securely
	    private static final int ITERATION_COUNT = 65536;
	    private static final int KEY_LENGTH = 256;
	    private static final String ALGORITHM = "AES/GCM/NoPadding";
	    private static final int GCM_TAG_LENGTH = 128; // 128-bit tag length
    
	    public SecretKey getSecretKey() throws Exception {
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
	        SecretKey tmp = factory.generateSecret(spec);
	        return new SecretKeySpec(tmp.getEncoded(), "AES");
	    }
	    
	    public  String convertSecretKeyToString(SecretKey secretKey) {
	        byte[] rawData = secretKey.getEncoded();
	        return Base64.getEncoder().encodeToString(rawData);
	    }

	    public SecretKey convertStringToSecretKey(String encodedKey) {
	        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
	        return new SecretKeySpec(decodedKey, "AES");
	    }
    
	    public String encryptlicenceKey(String data , SecretKey key) throws Exception {
	        
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, SALT.getBytes())); // Using salt as IV (not recommended for production)
	        byte[] encrypted = cipher.doFinal(data.getBytes());
	        return Base64.getEncoder().encodeToString(encrypted);
	    }
	    
	    public String decryptLicenceKey(String encryptedData,SecretKey key) throws Exception {
	        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, SALT.getBytes("UTF-8"))); // Using salt as IV
	        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	        return new String(original);
	    }
	    
	    public String encryptObject(Serializable object, SecretKey key) throws Exception {
	        // Serialize the object to a byte array
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream out = new ObjectOutputStream(bos);
	        out.writeObject(object);
	        out.flush();
	        byte[] dataToEncrypt = bos.toByteArray();

	        // Encrypt the data
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, SALT.getBytes("UTF-8")));
	        byte[] encryptedData = cipher.doFinal(dataToEncrypt);

	        // Return Base64 encoded string of encrypted data
	        return Base64.getEncoder().encodeToString(encryptedData);
	    }
	    
	    public Object decryptObject(String encryptedData, SecretKey key) throws Exception {
	        // Decode the data from Base64
	        byte[] dataToDecrypt = Base64.getDecoder().decode(encryptedData);

	        // Initialize cipher for AES/GCM/NoPadding
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, SALT.getBytes("UTF-8")));

	        // Decrypt the data
	        byte[] decryptedBytes = cipher.doFinal(dataToDecrypt);

	        // Deserialize the object
	        ByteArrayInputStream bis = new ByteArrayInputStream(decryptedBytes);
	        ObjectInputStream ois = new ObjectInputStream(bis);
	        Object object = ois.readObject();
	        ois.close();
	        return object;
	    }
	    
}

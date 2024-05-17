package com.example.MainServices.config;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class DecryptDataConfig {

	private static final String ALGORITHM = "AES/GCM/NoPadding";
	private static final int GCM_TAG_LENGTH = 128; // 128-bit tag length
	private static final String SALT = "12345678"; // You should use a more secure salt and store it securely

	public SecretKey convertStringToSecretKey(String encodedKey) {
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		return new SecretKeySpec(decodedKey, "AES");
	}

	public String decryptLicenceKey(String encryptedData, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_LENGTH, SALT.getBytes("UTF-8"))); // Using salt as IV
		byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
		return new String(original);
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

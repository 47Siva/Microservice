package com.example.Licence.Management.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Licence.Management.common.LicenceKeyConfig;
import com.example.Licence.Management.common.SecretKeyConfig;
import com.example.Licence.Management.dto.DecryptDto;
import com.example.Licence.Management.dto.EncryptDataDto;
import com.example.Licence.Management.dto.LicenceKeyDto;
import com.example.Licence.Management.entity.Licence;
import com.example.Licence.Management.enumuration.ExpiredStatus;
import com.example.Licence.Management.enumuration.Status;
import com.example.Licence.Management.repository.LicenceRepository;



@Service
public class AdminService {

	@Autowired
	private LicenceRepository licenceRepository;

	@Autowired
	private LicenceKeyConfig key;

	@Autowired
	private SecretKeyConfig secretKeyConfig;
	

	public ResponseEntity<?> getDecryptlicencekey(LicenceKeyDto licenceKeyDto) throws Exception {

		SecretKey key = secretKeyConfig.convertStringToSecretKey(licenceKeyDto.getSecretKey());

		String decryptLicenseKey = secretKeyConfig.decryptLicenceKey(licenceKeyDto.getLicenceKey(), key);

		return ResponseEntity.ok(decryptLicenseKey);
	}
	
	public ResponseEntity<?> getDecryptDataAndUpdateLicenceStatus(EncryptDataDto dataDto) throws Exception {
		SecretKey key = secretKeyConfig.convertStringToSecretKey(dataDto.getSecretKey());
	    Object decryptData =secretKeyConfig.decryptObject(dataDto.getEncrptData(), key);
		
		Optional<Licence> licencekey = licenceRepository.findByLicenceKey(((DecryptDto) decryptData).getLicenceKey());
		Map<String ,Object> response = new HashMap<String ,Object>();
		if(licencekey.isPresent()) {
			Licence licence = licencekey.get();
			if(licence.getLicenceKey().equals(((DecryptDto) decryptData).getLicenceKey())) {
				licence.setStatus(Status.APPROVED);
				licence.setExpiredStatus(ExpiredStatus.NOT_EXPIRED);
			}
			response.put("Status", licence.getStatus());
			response.put("DecryptData", decryptData);
			licenceRepository.save(licence);
			return ResponseEntity.ok(response);
		}else {
			return ResponseEntity.badRequest().body("Licence was not valid");
		}
	}

	public ResponseEntity<?> updateLicence(UUID id ,String licencekey) {
		Optional<Licence> obj = licenceRepository.findById(id);
		if(obj.isPresent()) {
			Licence licence = obj.get();
			if(licence.getStatus().equals(Status.APPROVED) || licence.getExpiredStatus().equals(ExpiredStatus.NOT_EXPIRED)
					|| licence.getLicenceKey().equals(licencekey)) {
				licence.setActiveationDate(new SimpleDateFormat("yyy-MM-dd.HH.mm.ss").format(new Date()));
				licence.setExpiryDate(LocalDate.now().plusYears(1).toString());
				licence.setGracePeriod("30 days");
			}
			licenceRepository.save(licence);
			return ResponseEntity.ok(licence);
		}else{
		return ResponseEntity.badRequest().body("Faild to get licence pleace check you Id");
		}
	}

}

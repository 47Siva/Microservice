package com.example.Licence.Management.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Licence.Management.common.LicenceKeyConfig;
import com.example.Licence.Management.common.SecretKeyConfig;
import com.example.Licence.Management.dto.DecryptDto;
import com.example.Licence.Management.dto.EncryptDataDto;
import com.example.Licence.Management.dto.LicenceDto;
import com.example.Licence.Management.dto.LicenceKeyDto;
import com.example.Licence.Management.dto.LicenceResponseDto;
import com.example.Licence.Management.entity.Licence;
import com.example.Licence.Management.enumuration.Status;
import com.example.Licence.Management.repository.LicenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LicenceService {

	@Autowired
	private LicenceRepository licenceRepository;
	
	@Autowired
	private LicenceKeyConfig key;
	
	@Autowired
	private SecretKeyConfig secretKeyConfig;

	public ResponseEntity<?> createLicence(LicenceResponseDto licenceRequestDto) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(licenceRequestDto.getCompanyName() != null) {
			
			Licence licence = Licence.builder()
					          .companyName(licenceRequestDto.getCompanyName())
					          .companyAddress(licenceRequestDto.getCompanyAddress())
					          .contactNumber(licenceRequestDto.getContactNumber())
					          .mailId(licenceRequestDto.getMailId())
					          .status(Status.ACTIVE).build();
			licenceRepository.save(licence);
			
			LicenceResponseDto licencedto = LicenceResponseDto.builder()
					                     .companyName(licence.getCompanyName())
					                     .companyAddress(licence.getCompanyAddress())
					                     .contactNumber(licence.getContactNumber())
					                     .mailId(licence.getMailId())
					                     .status(licence.getStatus())
					                     .licenceKey("Not generated")
					                     .build();
			
			response.put("Data", licencedto);
			response.put("Message", "Successfully created");
			response.put("TimeStamp", new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			return ResponseEntity.ok(response);
		}else {
			
			response.put("Error", HttpStatus.BAD_REQUEST.toString());
			response.put("message","Invalid pay load");
			response.put("TimeStamp", new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			return ResponseEntity.badRequest().body(response);
		}
	}
	

	public ResponseEntity<?> getLicence(UUID id) {
		Optional<Licence> licence = licenceRepository.findById(id);
		Map<String, Object> response = new HashMap<String, Object>();

		if(licence.isPresent()) {
			Licence licenceObj = licence.get();
			LicenceDto dto = LicenceDto.builder()
					         .activeationDate(licenceObj.getActiveationDate())
					         .companyAddress(licenceObj.getCompanyAddress())
					         .companyName(licenceObj.getCompanyName())
					         .contactNumber(licenceObj.getContactNumber())
					         .expiredStatus(licenceObj.getExpiredStatus())
					         .expiryDate(licenceObj.getExpiryDate())
					         .graceperiod(licenceObj.getGracePeriod())
					         .id(licenceObj.getId())
					         .mailId(licenceObj.getMailId())
					         .licenceKey(licenceObj.getLicenceKey())
					         .status(licenceObj.getStatus())
					         .build();
			response.put("Data", dto);              
			response.put("TimeStamp", new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			return ResponseEntity.ok(response);
		}else {
			response.put("Error" , HttpStatus.BAD_REQUEST.toString());
			response.put("TimeStampnew",new SimpleDateFormat("yyy-MM-dd.HH.mm.ss").format(new Date()));
			response.put("Message","Failed to get details please check your ID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}	

	public ResponseEntity<?> generateLicenceKey(UUID id) {
		
		Optional<Licence> obj = licenceRepository.findById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		
		if( obj.isPresent()) {
			Licence licence = obj.get();
			String licenceKey = key.generateLicenceKey(licence.getId(),licence.getMailId(),licence.getCompanyName());
			licence.setLicenceKey(licenceKey);
			licence.setStatus(Status.REQUEST);
			licenceRepository.save(licence);
	
			response.put("Licencekey", obj.get().getLicenceKey());              
			response.put("Message", "licenceKey was generated");
			response.put("TimeStamp", new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			return ResponseEntity.ok(response);
		}else {
			response.put("Error" , HttpStatus.BAD_REQUEST.toString());
			response.put("TimeStampnew",new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			response.put("Message","Failed to generate LicenceKey please check your ID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	

	public ResponseEntity<?> getLicenceEntity(UUID id) {
		Optional<Licence> obj =licenceRepository.findById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		if(obj.isPresent()) {
			
			Licence licence = obj.get();
			DecryptDto responsedto = DecryptDto.builder()
    				                         .licenceKey(licence.getLicenceKey())
    				                         .mailId(licence.getMailId()).build();
			
			response.put("Data", responsedto);              
			response.put("TimeStamp", new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			return ResponseEntity.ok(response);
		}else {
			response.put("Error" , HttpStatus.BAD_REQUEST.toString());
			response.put("TimeStampnew",new SimpleDateFormat("yyy-MM-dd.HH.mm.ss").format(new Date()));
			response.put("Message","Failed to get details please check your ID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	
	}

	public ResponseEntity<?> getEncryptLicenceKey(UUID id) throws Exception {
		Optional<Licence> obj = licenceRepository.findById(id);
		Map<String, Object> response = new HashMap<String, Object>();
		if( obj.isPresent()) {
			
			SecretKey Key = secretKeyConfig.getSecretKey();   
			String encryptedLicenseKey = secretKeyConfig.encryptlicenceKey(obj.get().getLicenceKey(),Key);
			String secretkey = secretKeyConfig.convertSecretKeyToString(Key);
			                                     
			LicenceKeyDto keyResponse = LicenceKeyDto.builder() 
					                         .licenceKey(encryptedLicenseKey)
					                         .secretKey(secretkey).build();
			
			response.put("Data", keyResponse);
			response.put("TimeStamp",new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			response.put("Status", HttpStatus.OK.toString());
			return ResponseEntity.ok(response);
		}else {
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("Message","Failed to encrypt data please check your ID");
			response.put("TimeStamp", new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
    public ResponseEntity<?> getEncryptData(UUID id) throws Exception {
    	Optional<Licence> obj = licenceRepository.findById(id);
    	Map<String, Object> response = new HashMap<String , Object>();
    	
    	if(obj.isPresent()) {
    		SecretKey key = secretKeyConfig.getSecretKey();
    		
    		Licence licence = obj.get();
    		DecryptDto responsedto = DecryptDto.builder()
    				                         .licenceKey(licence.getLicenceKey())
    				                         .mailId(licence.getMailId()).build();
    		
    		String encryptedData = secretKeyConfig.encryptObject(responsedto, key);
    		String secrtkey = secretKeyConfig.convertSecretKeyToString(key);
    		
    		EncryptDataDto dataResponse = EncryptDataDto.builder()
    				                          .encrptData(encryptedData)
    				                          .secretKey(secrtkey).build();
    		
    		response.put("Data", dataResponse);
			response.put("TimeStamp",new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			response.put("Status", HttpStatus.OK.toString());
			return ResponseEntity.ok(response);
		}else {
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("Message","Failed to encrypt data please check your ID");
			response.put("TimeStamp", new SimpleDateFormat("yyy.MM.dd.HH.mm.ss").format(new Date()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}


	public ResponseEntity<?> getAllLicence() {
		
		List<Licence> allLicence = licenceRepository.findAll();
		return ResponseEntity.ok(allLicence);
	}


	public ResponseEntity<?> getLicenceByid(UUID id) {
		Optional<Licence> licence = licenceRepository.findById(id);
		Licence licenceObj = licence.get();
		LicenceDto dto = LicenceDto.builder()
				         .activeationDate(licenceObj.getActiveationDate())
				         .companyAddress(licenceObj.getCompanyAddress())
					     .companyName(licenceObj.getCompanyName())
				         .contactNumber(licenceObj.getContactNumber())
				         .expiredStatus(licenceObj.getExpiredStatus())
				         .expiryDate(licenceObj.getExpiryDate())
				         .graceperiod(licenceObj.getGracePeriod())
				         .id(licenceObj.getId())
				         .mailId(licenceObj.getMailId())
				         .licenceKey(licenceObj.getLicenceKey())
				         .status(licenceObj.getStatus())
				         .build();
		return ResponseEntity.ok(dto);
	}

}

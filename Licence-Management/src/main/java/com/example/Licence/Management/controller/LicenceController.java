package com.example.Licence.Management.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Licence.Management.dto.LicenceDto;
import com.example.Licence.Management.dto.LicenceResponseDto;
import com.example.Licence.Management.entity.Licence;
import com.example.Licence.Management.service.LicenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/licence")
@Tag(name = "Licence_Controller")
public class LicenceController {

	@Autowired
	private LicenceService licenceService;

	@Operation(description = "POST End Point", 
			   summary = "This is a Licence Create API"
	)
	@PostMapping("/create")
	public ResponseEntity<?> createLicence(@RequestBody LicenceResponseDto licencedto) {

		return licenceService.createLicence(licencedto);
	}

	@Operation(description = "GET End Point", 
			   summary = "This is a Licence Get API"
	)
	@GetMapping("/getlicence/{id}")
	public ResponseEntity<?> getlicence(@PathVariable UUID id) {
		return licenceService.getLicence(id);
	}

	@Operation(description = "GET End Point", 
			   summary = "This is a Licence Generate API"
	)
	@GetMapping("/generatelicencekey/{id}")
	public ResponseEntity<?> generateLicenceKey(@PathVariable UUID id) {
		return licenceService.generateLicenceKey(id);
	}

	@Operation(description = "GET End Point", 
			   summary = "This is a Licence Get API"
	)
	@GetMapping("/getLicence/{id}")
	public ResponseEntity<?> getlicenceEntity(@PathVariable UUID id) {
		return licenceService.getLicenceEntity(id);
	}

	@Operation(description = "GET End Point", 
			   summary = "This is an Encrypt Licence Key Get API"
	)
	@GetMapping("/getenlicencekey/{id}")
	public ResponseEntity<?> getEncryptLicenceById(@PathVariable UUID id) throws Exception {
		return licenceService.getEncryptLicenceKey(id);
	}

	@Operation(description = "GET End Point", 
			   summary = "This is an Encrypt Data Get API"
	)
	@GetMapping("/getencryptdata/{id}")
	public ResponseEntity<?> getencryptData(@PathVariable UUID id, @RequestParam String toemail,
			@RequestParam String subject) throws Exception {
		return licenceService.getEncryptData(id, toemail, subject);
	}
	
	@Operation(description = "GET End Point", 
			   summary = "This is a Send End To End Encrypt Data API"
	)
	@GetMapping("/sendencryptdatatoemail/{id}")
	public ResponseEntity<?> sendencryptdatatoemail(@PathVariable UUID id, @RequestParam String toemail,
			@RequestParam String subject) throws Exception {
		return licenceService.sendencryptdatatoemail(id, toemail, subject);
	}

	@Operation(description = "GET End Point", 
			   summary = "This is an ALL Licence Get API"
	)
	@GetMapping("/getalllicence")
	public ResponseEntity<?> getlistLicence() {
		return licenceService.getAllLicence();
	}

	@Operation(description = "GET End Point", 
			   summary = "This is a Licence Get API"
	)
	@GetMapping("/getlicencebyid/{id}")
	public ResponseEntity<?> getlicencebyid(@PathVariable UUID id) {
		return licenceService.getLicenceByid(id);
	}

	
	// status update api
	@Operation(description = "POST End Point", 
			   summary = "This is a Licence Status Update API"
	)
	@PostMapping("/statusupdate")
	public ResponseEntity<?> statusUpdate(@RequestBody LicenceDto licenceDto) {
		return licenceService.statusupdate(licenceDto);
	}

	// activationdate update api
	@Operation(description = "POST End Point", 
			   summary = "This is a Licence Activation Date Update API"
	)
	@PostMapping("/activationdateupdate")
	public ResponseEntity<?> activationdateupdate(@RequestBody LicenceDto licence) {
		return licenceService.activationdateupdate(licence);
	}

	// expiredStatus update api
	@Operation(description = "POST End Point", 
			   summary = "This is a Licence Expired Status Update API"
	)
	@PostMapping("/expiredStatusupdate")
	public ResponseEntity<?> expiredStatusUpdate(@RequestBody LicenceDto licence) {
		return licenceService.expiredStatusUpdate(licence);
	}

	// expireddate update api
	@Operation(description = "POST End Point", 
			   summary = "This is a Licence Expired Date Update API"
	)
	@PostMapping("/expireddateupdate")
	public ResponseEntity<?> expireddateUpdate(@RequestBody LicenceDto licence) {
		return licenceService.expireddateUpdate(licence);
	}

	// graceperiod update api
	@Operation(description = "POST End Point", 
			   summary = "This is a Licence Grace Period Update API"
	)
	@PostMapping("/graceperiodupdate")
	public ResponseEntity<?> gracePeriodUpdate(@RequestBody LicenceDto licence) {
		return licenceService.gracePeriodUpdate(licence);
	}
	
	@Operation(description = "GET End Point", 
			   summary = "This is a Licence Get (Using find By Email) API"
	)
	@GetMapping("/findByemail/{email}")
	public ResponseEntity<?> findbyEmail(@PathVariable String email){
		return licenceService.findbymailId(email);
	}
}

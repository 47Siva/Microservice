package com.example.Licence.Management.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("api/licence")
public class LicenceController {

	@Autowired
	private LicenceService licenceService;

	@PostMapping("/create")
	public ResponseEntity<?> createLicence(@RequestBody LicenceResponseDto licencedto) {

		return licenceService.createLicence(licencedto);
	}

	@GetMapping("/getlicence/{id}")
	public ResponseEntity<?> getlicence(@PathVariable UUID id) {
		return licenceService.getLicence(id);
	}

	@GetMapping("/generatelicencekey/{id}")
	public ResponseEntity<?> generateLicenceKey(@PathVariable UUID id) {
		return licenceService.generateLicenceKey(id);
	}

	@GetMapping("/getLicence/{id}")
	public ResponseEntity<?> getlicenceEntity(@PathVariable UUID id) {
		return licenceService.getLicenceEntity(id);
	}

	@GetMapping("/getenlicencekey/{id}")
	public ResponseEntity<?> getEncryptLicenceById(@PathVariable UUID id) throws Exception {
		return licenceService.getEncryptLicenceKey(id);
	}

	@GetMapping("/getencryptdata/{id}")
	public ResponseEntity<?> getencryptData(@PathVariable UUID id, @RequestParam String toemail,
			@RequestParam String subject) throws Exception {
		return licenceService.getEncryptData(id, toemail, subject);
	}

	@GetMapping("/getalllicence")
	public ResponseEntity<?> getlistLicence() {
		return licenceService.getAllLicence();
	}

	@GetMapping("/getlicencebyid/{id}")
	public ResponseEntity<?> getlicencebyid(@PathVariable UUID id) {
		return licenceService.getLicenceByid(id);
	}

	// status update api
	@PutMapping("/statusupdate")
	public ResponseEntity<?> statusUpdate(@RequestBody LicenceDto licenceDto) {
		return licenceService.statusupdate(licenceDto);
	}

	// activationdate update api
	@PutMapping("/activationdateupdate")
	public ResponseEntity<?> activationdateupdate(@RequestBody Licence licence) {
		return licenceService.activationdateupdate(licence);
	}

	// expiredStatus update api
	@PutMapping("/expiredStatusupdate")
	public ResponseEntity<?> expiredStatusUpdate(@RequestBody Licence licence) {
		return licenceService.expiredStatusUpdate(licence);
	}

	// expireddate update api
	@PutMapping("/expireddateupdate")
	public ResponseEntity<?> expireddateUpdate(@RequestBody Licence licence) {
		return licenceService.expireddateUpdate(licence);
	}

	// graceperiod update api
	@PutMapping("/graceperiodupdate")
	public ResponseEntity<?> gracePeriodUpdate(@PathVariable Licence licence) {
		return licenceService.gracePeriodUpdate(licence);
	}
	
	@GetMapping("/findByemail/{email}")
	public ResponseEntity<?> findbyEmail(@PathVariable String email){
		return licenceService.findbymailId(email);
	}
}

package com.example.MainServices.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MainServices.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	//return string
	@GetMapping("grtlistlicence")
	public ResponseEntity<?> getListLicence(){

		return adminService.getListLicence();
	}
	
    // using rest templet
	@GetMapping("/getlistoflicence")
	public ResponseEntity<?> getListOfLicence(){
		return adminService.getListOfLicence();
	}
	
	//using rest templet
	@GetMapping("/getalllicence")
	public ResponseEntity<?> getAllLicence(){
		return adminService.getAllLicence();
	}
	
	//return string
	@GetMapping("/getlicencebyid/{id}")
	public ResponseEntity<?> getLicenceById(@PathVariable UUID id ) throws JsonMappingException, JsonProcessingException{
		return adminService.getbyLicenceId(id);
	}
	
	//using rest templet
	@GetMapping("/getlicence/{id}")
	public ResponseEntity<?> getByLicenceId(@PathVariable ("id") UUID id){		
		return adminService.getByLicenceId(id);
	}

	
	@GetMapping("/getenencryptlicencekey/{id}")
	public ResponseEntity<?> getEncryptLicence(@PathVariable ("id") UUID id) throws JsonMappingException, JsonProcessingException{
		return adminService.getEncryptLicence(id);
	}
	
}

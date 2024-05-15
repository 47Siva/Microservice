package com.example.MainServices.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MainServices.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("grtlistlicence")
	public ResponseEntity<?> getListLicence(){

		return adminService.getListLicence();
	}
	
	@GetMapping("/getlistoflicence")
	public ResponseEntity<?> getListOfLicence(){
		return adminService.getListOfLicence();
	}
	
	@GetMapping("/getalllicence")
	public ResponseEntity<?> getAllLicence(){
		return adminService.getAllLicence();
	}
	
	@GetMapping("/getlicencebyid/{id}")
	public ResponseEntity<?> getLicenceById(@PathVariable UUID id ){
		return adminService.getbyLicenceId(id);
	}
	
	@GetMapping("/getlicence/{id}")
	public ResponseEntity<?> getByLicenceId(@PathVariable ("id") UUID id){		
		return adminService.getByLicenceId(id);
	}
	
}

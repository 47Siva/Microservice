package com.example.MainServices.service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.Licence.Management.entity.Licence;
import com.example.MainServices.dto.LicenceDto;

@Service
public class AdminService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${licence.baseUrl}")
	String licenceBaseUrl;

	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		return headers;
	}

	
	public ResponseEntity<?> getListLicence() {
		String licence = restTemplate.getForObject("http://localhost:8081/api/licence/getalllicence", String.class);
		return ResponseEntity.ok(licence);
	}
	
	
	public ResponseEntity<?> getListOfLicence() {
		ResponseEntity<List<Licence>> licence = restTemplate.exchange("http://localhost:8081/api/licence/getalllicence",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Licence>>() {
				});
		List<Licence> licences = licence.getBody();
		return ResponseEntity.ok(licences);
	}


	public ResponseEntity<?> getAllLicence() {
		String serviceUrl = licenceBaseUrl + "/getalllicence";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceUrl);
		ResponseEntity<List<Licence>> licence= restTemplate.exchange(builder.toUriString(), 
				HttpMethod.GET,null,new ParameterizedTypeReference<List<Licence>>() {});
		 List<Licence> licences = licence.getBody();
		return ResponseEntity.ok(licences);
	}


	public ResponseEntity<?> getByLicenceId(UUID id) {
		String serviceUrl = licenceBaseUrl + "/getlicence" +id;
//		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceUrl);
		URI uri = UriComponentsBuilder.fromHttpUrl(serviceUrl).build().toUri();
		ResponseEntity<LicenceDto> licence= restTemplate.exchange(uri, 
				HttpMethod.GET,null,new ParameterizedTypeReference<LicenceDto>() {});
		return ResponseEntity.ok(licence.getBody());
	}


	public ResponseEntity<?> getbyLicenceId(UUID id) {
		String url = "http://localhost:8081/api/licence/getlicence/" + id;
		 String response = restTemplate.getForObject(url, String.class);
//		 Licence licence = licence.getBody();
		 return ResponseEntity.ok(response);
	}


	
	
}

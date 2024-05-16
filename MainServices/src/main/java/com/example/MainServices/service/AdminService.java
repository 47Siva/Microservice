package com.example.MainServices.service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.Licence.Management.entity.Licence;
import com.example.MainServices.dto.LicenceDto;
import com.example.MainServices.dto.LicenceKeyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	// return String formet
	public ResponseEntity<?> getListLicence() {
		String licence = restTemplate.getForObject("http://localhost:8081/api/licence/getalllicence", String.class);
		return ResponseEntity.ok(licence);
	}

	// return licence Response
	public ResponseEntity<?> getListOfLicence() {
		ResponseEntity<List<Licence>> licence = restTemplate.exchange("http://localhost:8081/api/licence/getalllicence",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Licence>>() {
				});
		List<Licence> licences = licence.getBody();
		return ResponseEntity.ok(licences);
	}

	// return Licence Response
	public ResponseEntity<?> getAllLicence() {
		String serviceUrl = licenceBaseUrl + "/getalllicence";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceUrl);
		ResponseEntity<List<Licence>> licence = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Licence>>() {
				});
		List<Licence> licences = licence.getBody();
		return ResponseEntity.ok(licences);
	}

	// return licence response
	public ResponseEntity<?> getByLicenceId(UUID id) {
		String serviceUrl = licenceBaseUrl + "/getlicencebyid/" + id;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serviceUrl);
  		//URI uri = UriComponentsBuilder.fromHttpUrl(serviceUrl).build().toUri();
		ResponseEntity<LicenceDto> licence = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<LicenceDto>() {
				});
		return ResponseEntity.ok(licence.getBody());
	}

	// return String formate
	public ResponseEntity<?> getbyLicenceId(UUID id) throws JsonMappingException, JsonProcessingException {
		String url = "http://localhost:8081/api/licence/getlicence/" + id;
		try {
			ResponseEntity<Map> responseEntity= restTemplate.getForEntity(url, Map.class);
			Map<String, Object> responseBody = responseEntity.getBody();
			if (responseBody != null && responseBody.containsKey("Data")) {
				ObjectMapper mapper = new ObjectMapper();
				LicenceDto licenceKeyDto = mapper.convertValue(responseBody.get("Data"), LicenceDto.class);
				return ResponseEntity.ok(licenceKeyDto);
			} else {
				return ResponseEntity.status(responseEntity.getStatusCode())
						.body("Invalid response from License Service");
			}
		}catch (HttpClientErrorException e) {
			HttpStatusCode statusCode = e.getStatusCode();
			String responseBody = e.getResponseBodyAsString();

			ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> errorResponse = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
			return ResponseEntity.status(statusCode).body(errorResponse);
		}

	}

	public ResponseEntity<?> getEncryptLicence(UUID id) throws JsonMappingException, JsonProcessingException {
		String serviceUrl = licenceBaseUrl + "/getenlicencekey/" + id;

		URI uri = UriComponentsBuilder.fromHttpUrl(serviceUrl).build().toUri();
//		String builder = UriComponentsBuilder.fromHttpUrl(serviceUrl).toUriString();

		try {
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(uri, Map.class);
			Map<String, Object> responseBody = responseEntity.getBody();
			if (responseBody != null && responseBody.containsKey("Data")) {
				ObjectMapper mapper = new ObjectMapper();
				LicenceKeyDto licenceKeyDto = mapper.convertValue(responseBody.get("Data"), LicenceKeyDto.class);
				return ResponseEntity.ok(licenceKeyDto);
			} else {
				return ResponseEntity.status(responseEntity.getStatusCode())
						.body("Invalid response from License Service");
			}
		} catch (HttpClientErrorException e) {
			HttpStatusCode statusCode = e.getStatusCode();
			String responseBody = e.getResponseBodyAsString();

			ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> errorResponse = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
			return ResponseEntity.status(statusCode).body(errorResponse);
		}

	}
	

}

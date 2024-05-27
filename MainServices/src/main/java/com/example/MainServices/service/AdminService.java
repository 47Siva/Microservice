package com.example.MainServices.service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

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
import com.example.MainServices.config.DecryptDataConfig;
import com.example.MainServices.dto.DecryptDto;
import com.example.MainServices.dto.EncryptDataDto;
import com.example.MainServices.dto.LicenceDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdminService {

	@Value("${licence.baseUrl}")
	String licenceBaseUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DecryptDataConfig decryptDataConfig;

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
		// URI uri = UriComponentsBuilder.fromHttpUrl(serviceUrl).build().toUri();
		ResponseEntity<LicenceDto> licence = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<LicenceDto>() {
				});
		return ResponseEntity.ok(licence.getBody());
	}

	// return String formate
	public ResponseEntity<?> getbyLicenceId(UUID id) throws JsonMappingException, JsonProcessingException {
		String url = "http://localhost:8081/api/licence/getlicence/" + id;
		try {
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
			Map<String, Object> responseBody = responseEntity.getBody();
			if (responseBody != null && responseBody.containsKey("Data")) {
//				ObjectMapper mapper = new ObjectMapper();
//				LicenceDto licenceDto = mapper.convertValue(responseBody.get("Data"), LicenceDto.class);
				return ResponseEntity.ok(responseBody);
			} else {
				return ResponseEntity.status(responseEntity.getStatusCode())
						.body("Invalid response from License Service");
			}
		} catch (HttpClientErrorException e) {
			HttpStatusCode statusCode = e.getStatusCode();
			String responseBody = e.getResponseBodyAsString();

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> errorResponse = objectMapper.readValue(responseBody,
					new TypeReference<Map<String, Object>>() {
					});
			return ResponseEntity.status(statusCode).body(errorResponse);
		}

	}

	// encrypt Licence key
	public ResponseEntity<?> getEncryptLicence(UUID id) throws JsonMappingException, JsonProcessingException {
		String serviceUrl = licenceBaseUrl + "/getenlicencekey/" + id;

		URI uri = UriComponentsBuilder.fromHttpUrl(serviceUrl).build().toUri();
//		String builder = UriComponentsBuilder.fromHttpUrl(serviceUrl).toUriString();

		try {
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(uri, Map.class);
			Map<String, Object> responseBody = responseEntity.getBody();
			if (responseBody != null && responseBody.containsKey("Data")) {
//				ObjectMapper mapper = new ObjectMapper();
//				LicenceKeyDto licenceKeyDto = mapper.convertValue(responseBody.get("Data"), LicenceKeyDto.class);
				return ResponseEntity.ok(responseBody);
			} else {
				return ResponseEntity.status(responseEntity.getStatusCode())
						.body("Invalid response from License Service");
			}
		} catch (HttpClientErrorException e) {
			HttpStatusCode statusCode = e.getStatusCode();
			String responseBody = e.getResponseBodyAsString();

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> errorResponse = objectMapper.readValue(responseBody,
					new TypeReference<Map<String, Object>>() {
					});
			return ResponseEntity.status(statusCode).body(errorResponse);
		}

	}

	// encrypt data
	public ResponseEntity<?> getEncryptData(UUID id) throws JsonMappingException, JsonProcessingException {
		String serviceUrl = licenceBaseUrl + "/getencryptdata/" + id;
		URI uri = UriComponentsBuilder.fromHttpUrl(serviceUrl).build().toUri();
		try {
			ResponseEntity<Map> responseEntity = restTemplate.getForEntity(uri, Map.class);
			Map<String, Object> responseBody = responseEntity.getBody();
			if (responseBody != null && responseBody.containsKey("Data")) {
				ObjectMapper mapper = new ObjectMapper();
				EncryptDataDto encryptData = mapper.convertValue(responseBody.get("Data"), EncryptDataDto.class);
				return ResponseEntity.ok(encryptData);
			} else {
				return ResponseEntity.status(responseEntity.getStatusCode())
						.body("Invalid response from licence service");
			}
		} catch (HttpClientErrorException e) {
			HttpStatusCode statusCode = e.getStatusCode();
			String responseBody = e.getResponseBodyAsString();

			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> errorResponse = objectMapper.readValue(responseBody,
					new TypeReference<Map<String, Object>>() {
					});
			return ResponseEntity.status(statusCode).body(errorResponse);
		}
	}

	public ResponseEntity<?> decryptData(EncryptDataDto dataDto) throws Exception {

		try {
			SecretKey key = decryptDataConfig.convertStringToSecretKey(dataDto.getSecretKey());
			Object decryptData = decryptDataConfig.decryptObject(dataDto.getEncrptData(), key);
			ObjectMapper objectMapper = new ObjectMapper();

			// Assuming decryptData is a JSON string
			String jsonString = decryptData.toString();

			// Convert the object to a JSON string
			jsonString = objectMapper.writeValueAsString(decryptData);

			// Deserialize JSON string to DecryptedLicenceData
			DecryptDto licenceData = objectMapper.readValue(jsonString, DecryptDto.class);
			DecryptDto decryptDto = DecryptDto.builder().licenceKey(licenceData.getLicenceKey())
					.mailId(licenceData.getMailId()).build();

			String serviceUrl = licenceBaseUrl + "/findByemail/" + licenceData.getMailId();

			URI uri = UriComponentsBuilder.fromHttpUrl(serviceUrl).build().toUri();

			ResponseEntity<Licence> responseEntity = restTemplate.getForEntity(uri, Licence.class);
			Licence responseBody = responseEntity.getBody();

			// Assuming decryptData is a JSON string
			String jsonString1 = responseBody.toString();

			// Convert the object to a JSON string
			jsonString = objectMapper.writeValueAsString(decryptData);
			Licence licence = objectMapper.readValue(jsonString1, Licence.class);
			if(licenceData.getMailId().equals(licence.getMailId()) && licenceData.getLicenceKey().equals(licence.getLicenceKey())) {
			
			}
			return ResponseEntity.ok(decryptDto);
		} catch (HttpClientErrorException e) {
			HttpStatusCode statusCode = e.getStatusCode();
			String responseBody = e.getResponseBodyAsString();
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> errorResponse = objectMapper.readValue(responseBody,
					new TypeReference<Map<String, Object>>() {
					});
			return ResponseEntity.status(statusCode).body(errorResponse);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Deecryption faild. Check the Payload");
		}
	}

}

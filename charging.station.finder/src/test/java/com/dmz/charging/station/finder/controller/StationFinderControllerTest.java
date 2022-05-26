package com.dmz.charging.station.finder.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URL;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dmz.charging.station.finder.service.model.custom.CompanyDto;
import com.dmz.charging.station.finder.service.model.custom.SearchResult;
import com.dmz.charging.station.finder.service.model.custom.StationFinderDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StationFinderControllerTest {
	// bind the above RANDOM_PORT
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@DisplayName("When Rest consumer request with empty body- then retun error response")
	public void test1() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

		// restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
		// restTemplate, null);
		ResponseEntity<ErrorDto> response = restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
				request, ErrorDto.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertTrue(response.getBody().getErrors()[0].equalsIgnoreCase("Required request body is missing/ request unable to read"));

	}

	@Test
	@DisplayName("When Rest consumer request with Correct request body- then retun correct response hierachy")
	public void test2() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		StationFinderDTO sf = new StationFinderDTO();
		sf.setCompanyID("SVPEAB");
		sf.setLatitude(59.40172547799233);
		sf.setLongitude(17.94621121167337);
		sf.setPreferredRadius(10);
		
		HttpEntity<StationFinderDTO> request = new HttpEntity<>(sf,headers);

		// restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
		// restTemplate, null);
		ResponseEntity<SearchResult> response = restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
				request, SearchResult.class);
		assertTrue(response.getBody().getCompany().getName().equalsIgnoreCase("Svenska Petroleum Exploration AB"));
		assertNotNull(response.getBody().getCompany().getChildCompany());
		assertTrue(response.getBody().getCompany().getChildCompany().get(0).getName().equalsIgnoreCase("Svenska Petroleum Exploration AB - sub 1"));
	}
	
	@Test
	@DisplayName("When Rest consumer request with Correct company id, preferd radius,  latitude >90 - then retun 400 bad response, with Latitude can't be greter than 90 degrees")
	public void test3() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		StationFinderDTO sf = new StationFinderDTO();
		sf.setCompanyID("SVPEAB");
		sf.setLatitude(91);
		sf.setLongitude(17.94621121167337);
		sf.setPreferredRadius(10);
		
		HttpEntity<StationFinderDTO> request = new HttpEntity<>(sf,headers);

		// restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
		// restTemplate, null);
		ResponseEntity<ErrorDto> response = restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
				request, ErrorDto.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertTrue(response.getBody().getErrors()[0].equalsIgnoreCase("Latitude can't be greter than 90 degrees"));

	}
	
	@Test
	@DisplayName("When Rest consumer request with Correct company id, preferd radius,  latitude< (- 90) - then retun 400 bad response, Latitude can't be less than -90 degrees")
	public void test4() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		StationFinderDTO sf = new StationFinderDTO();
		sf.setCompanyID("SVPEAB");
		sf.setLatitude(-91);
		sf.setLongitude(17.94621121167337);
		sf.setPreferredRadius(10);
		
		HttpEntity<StationFinderDTO> request = new HttpEntity<>(sf,headers);

		// restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
		// restTemplate, null);
		ResponseEntity<ErrorDto> response = restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
				request, ErrorDto.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertTrue(response.getBody().getErrors()[0].equalsIgnoreCase("Latitude can't be less than -90 degrees"));

	}
	
	@Test
	@DisplayName("When Rest consumer request with Correct company id, preferd radius,  longitude> (180) - then retun 400 bad response, longitude can't be greter than 180 degrees")
	public void test5() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		StationFinderDTO sf = new StationFinderDTO();
		sf.setCompanyID("SVPEAB");
		sf.setLatitude(59.40172547799233);
		sf.setLongitude(181);
		sf.setPreferredRadius(10);
		
		HttpEntity<StationFinderDTO> request = new HttpEntity<>(sf,headers);

		// restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
		// restTemplate, null);
		ResponseEntity<ErrorDto> response = restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
				request, ErrorDto.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertTrue(response.getBody().getErrors()[0].equalsIgnoreCase("longitude can't be greter than 180 degrees"));

	}
	
	@Test
	@DisplayName("When Rest consumer request with Correct company id, preferd radius,  longitude< -(180) - then retun 400 bad response, longitude can't be less than -180 degrees")
	public void test6() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		StationFinderDTO sf = new StationFinderDTO();
		sf.setCompanyID("SVPEAB");
		sf.setLatitude(59.40172547799233);
		sf.setLongitude(-181);
		sf.setPreferredRadius(10);
		
		HttpEntity<StationFinderDTO> request = new HttpEntity<>(sf,headers);

		// restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
		// restTemplate, null);
		ResponseEntity<ErrorDto> response = restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
				request, ErrorDto.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		assertTrue(response.getBody().getErrors()[0].equalsIgnoreCase("longitude can't be less than -180 degrees"));

	}
	@Test
	@DisplayName("When Rest consumer request with Correct request body- then retun nearest station within the prefered radius")
	public void test7() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		StationFinderDTO sf = new StationFinderDTO();
		sf.setCompanyID("SVPEAB");
		sf.setLatitude(59.40172547799233);
		sf.setLongitude(17.94621121167337);
		sf.setPreferredRadius(10);
		
		HttpEntity<StationFinderDTO> request = new HttpEntity<>(sf,headers);

		// restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
		// restTemplate, null);
		ResponseEntity<SearchResult> response = restTemplate.postForEntity(new URI("http://localhost:" + port + "/"),
				request, SearchResult.class);
		assertTrue(response.getBody().getCompany().getName().equalsIgnoreCase("Svenska Petroleum Exploration AB"));
		assertNotNull(response.getBody().getCompany().getChildCompany());
		assertTrue(response.getBody().getCompany().getChildCompany().get(0).getName().equalsIgnoreCase("Svenska Petroleum Exploration AB - sub 1"));
		assertNull(response.getBody().getCompany().getStations());
		
		assertNotNull(response.getBody().getCompany().getChildCompany().get(0).getStations());
		assertTrue(response.getBody().getCompany().getChildCompany().get(0).getStations().get(0).getDistanceFromCurrentlocation().equalsIgnoreCase("6.526586398556875 KM"));
	}

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ErrorDto {

	String timestamp;
	String status;
	String [] errors ;


}


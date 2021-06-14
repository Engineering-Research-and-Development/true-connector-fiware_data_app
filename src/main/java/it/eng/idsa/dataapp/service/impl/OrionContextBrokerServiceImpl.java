package it.eng.idsa.dataapp.service.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.eng.idsa.dataapp.model.OrionRequest;
import it.eng.idsa.dataapp.service.OrionContextBrokerService;

@Service
public class OrionContextBrokerServiceImpl implements OrionContextBrokerService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrionContextBrokerServiceImpl.class);

	private RestTemplate restTemplate;
	
	public OrionContextBrokerServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public ResponseEntity<String> enitityCall(OrionRequest orionRequest) {
		URI orionURI = URI.create("http://localhost:1026" + orionRequest.getRequestPath());
		logger.info("Triggering request towards {}", orionURI);
		return restTemplate.exchange(orionURI, HttpMethod.GET, null, String.class);
	}

}

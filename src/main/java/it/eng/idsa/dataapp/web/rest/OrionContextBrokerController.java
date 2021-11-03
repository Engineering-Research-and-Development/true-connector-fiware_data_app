package it.eng.idsa.dataapp.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.idsa.dataapp.domain.ProxyRequest;
import it.eng.idsa.dataapp.model.OrionRequest;
import it.eng.idsa.dataapp.service.OrionContextBrokerService;
import it.eng.idsa.dataapp.service.ProxyService;
import it.eng.idsa.multipart.processor.MultipartMessageProcessor;
import it.eng.idsa.multipart.util.UtilMessageService;

@RestController
@RequestMapping({ "/ngsi-ld/v1" })
public class OrionContextBrokerController {

	private static final Logger logger = LoggerFactory.getLogger(OrionContextBrokerController.class);
	
	@Autowired
	private OrionContextBrokerService orionService;
	
	@Autowired
	private ProxyService proxyService;

	@RequestMapping("/entities/**")
	public ResponseEntity<?> proxyToOrionCB(@RequestHeader HttpHeaders httpHeaders,
			@RequestBody(required = false) String body, HttpMethod method, HttpServletRequest request) throws URISyntaxException, JsonProcessingException {
		logger.info("HTTP Method {}", method.name());
		logger.info("HTTP Headers {}", httpHeaders.entrySet().stream()
				.map(Map.Entry::toString)
				.collect(Collectors.joining(";", "[", "]")));
		
		StringBuilder contextPath = new StringBuilder(request.getRequestURI());
		if(StringUtils.isNotBlank(request.getQueryString())) {
			contextPath.append("?")
				.append(request.getQueryString());
		}
		logger.info("Path {}", contextPath.toString());
		logger.info("payload \n{}", body);
		OrionRequest orionRequest = new OrionRequest(body, method, httpHeaders, contextPath.toString());

		ObjectMapper mapper = new ObjectMapper();
		ProxyRequest proxyRequest = new ProxyRequest(ProxyRequest.MULTIPART_FORM, 
				"https://localhost:8890/data", 
				null, 
				// TODO update logic to pass some ID that will be used for UsageControl as requestedArtifact
				getMessageAsString(UtilMessageService.getArtifactRequestMessage()), 
				mapper.writeValueAsString(orionRequest), 
				null, null);
				return proxyService.proxyMultipartForm(proxyRequest, httpHeaders);
		
//		return orionService.enitityCall(orionRequest);
		
//		return ResponseEntity.ok("Done");
	}
	
	private String getMessageAsString(Object message) {
		try {
			return MultipartMessageProcessor.serializeToJsonLD(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

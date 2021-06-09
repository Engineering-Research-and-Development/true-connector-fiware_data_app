package it.eng.idsa.dataapp.web.rest;

import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/v2" })
public class OrionContextBrokerController {

	private static final Logger logger = LoggerFactory.getLogger(OrionContextBrokerController.class);

	@RequestMapping("/entities/**")
	public ResponseEntity<?> proxyToOrionCB(@RequestHeader HttpHeaders httpHeaders,
			@RequestBody String body, HttpMethod method, HttpServletRequest request) {
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
		
		return ResponseEntity.ok("Done");
	}
}

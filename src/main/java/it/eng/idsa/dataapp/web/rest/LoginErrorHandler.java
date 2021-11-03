package it.eng.idsa.dataapp.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@Component
@ControllerAdvice
public class LoginErrorHandler {

	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseBody
	public ResponseEntity<String> handleError(HttpClientErrorException e, HttpServletResponse response)
			throws IOException {
//		 return ResponseEntity.noContent().build();
		final var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.OK);
	}

	/*
	 * @ExceptionHandler(DataRetrievalException.class) public
	 * ResponseEntity<JSONObject> handleDataRetrievalException( final
	 * DataRetrievalException exception) { if (log.isDebugEnabled()) {
	 * log.debug("Failed to retrieve data. [exception=({})]", exception == null ? ""
	 * : exception.getMessage(), exception); }
	 * 
	 * final var headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * 
	 * final var body = new JSONObject(); body.put("message",
	 * "Failed to retrieve data."); body.put("details", exception == null ? "" :
	 * exception.getMessage());
	 * 
	 * return new ResponseEntity<>(body, headers, HttpStatus.EXPECTATION_FAILED); }
	 * 
	 * 
	 */

}

package it.eng.idsa.dataapp.model;

import java.io.Serializable;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * Class to wrap up original request, so it can be sent via Connectors, and recreated at other side
 * @author igor.balog
 *
 */
public class OrionRequest implements Serializable {

	private static final long serialVersionUID = -8199508846955607433L;

	private String originalPayload;
	private HttpMethod method;
	private HttpHeaders headers;
	private String requestPath;
	
	public OrionRequest() {}
	
	public OrionRequest(String originalPayload, HttpMethod method, HttpHeaders headers, String requestPath) {
		super();
		this.originalPayload = originalPayload;
		this.method = method;
		this.headers = headers;
		this.requestPath = requestPath;
	}
	
	public String getOriginalPayload() {
		return originalPayload;
	}
	public void setOriginalPayload(String originalPayload) {
		this.originalPayload = originalPayload;
	}
	public HttpMethod getMethod() {
		return method;
	}
	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	public HttpHeaders getHeaders() {
		return headers;
	}
	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}
	public String getRequestPath() {
		return requestPath;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	
	
	
}

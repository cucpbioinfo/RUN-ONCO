package run.onco.component.api.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import run.onco.component.api.models.RequestDetails;
import run.onco.component.api.util.StringUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class GenericRestClient<T, V> {

	private RestTemplate restTemplate = new RestTemplate();

	public V execute(RequestDetails requestDetails, T data, ResponseErrorHandler errorHandler, ParameterizedTypeReference<V> typeRef)
			throws ResourceAccessException, Exception {

		restTemplate.setErrorHandler(errorHandler);
		HttpHeaders headers = new HttpHeaders();
		
		if (!StringUtil.isNullOrWhitespace(requestDetails.getAccessToken())) {
			headers.set("Authorization", "Bearer "+requestDetails.getAccessToken());
		}
		
		HttpEntity<T> entity = new HttpEntity<T>(data, headers);
		ResponseEntity<V> response = restTemplate.exchange(requestDetails.getUrl(), requestDetails.getRequestType(), entity, typeRef);
		return response.getBody();
	}
}

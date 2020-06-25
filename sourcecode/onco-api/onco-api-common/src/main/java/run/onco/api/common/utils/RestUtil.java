package run.onco.api.common.utils;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import run.onco.api.common.exception.RestException;
import run.onco.api.common.message.ServiceRequest;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class RestUtil {

	public static boolean isError(HttpStatus status) {
		HttpStatus.Series series = status.series();
		return !status.equals(HttpStatus.NOT_FOUND) && (HttpStatus.Series.SERVER_ERROR.equals(series) 
				|| HttpStatus.Series.CLIENT_ERROR.equals(series));
	}

	public static HttpEntity<String> createRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<String>(headers);
	}

	public static RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> T fetchSingle(String url, HttpMethod method, Class<T> model, ServiceRequest req) {
		
		HttpEntity<String> request = createRequest();
		ResponseEntity<String> response = getRestTemplate().exchange(url, method, request, String.class);
		
		try {
			if (isError(response.getStatusCode())) {
				RestException restError = getObjectMapper().readValue(response.getBody(), RestException.class);
				throw new RestClientException("[" + restError.getCode() + "] " + restError.getMessage());
			} else {
				return getObjectMapper().readValue(response.getBody(), model);
			}
		} catch (IOException e){
			throw new RuntimeException(e);
		}

	}
}

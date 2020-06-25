package run.onco.batch.api.utils;

import java.util.concurrent.Callable;

import org.springframework.web.client.RestTemplate;

import run.onco.batch.api.dto.BatchJobDto;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class Task implements Callable<String> {

	private RestTemplate restTemplate;
	private BatchJobDto data;
	private String endpointUrl;

	public Task(RestTemplate restTemplate, String endpointUrl, BatchJobDto data) {
		this.restTemplate = restTemplate;
		this.endpointUrl = endpointUrl;
		this.data = data;
	}

	public String call() throws Exception {
		try {
			restTemplate.postForObject(this.endpointUrl, this.data, String.class);
			return "SUCCESS";
		} catch (Exception ex) {
			return "FAIL";
		}
	}
}

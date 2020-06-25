package run.onco.component.api.models;

import java.io.Serializable;

import org.springframework.http.HttpMethod;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class RequestDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2739673958981709522L;

	private String url;
	private String accessToken;
	private HttpMethod requestType;
	
	public RequestDetails() {
		
	}

	public RequestDetails(String url, HttpMethod requestType) {
		super();
		this.url = url;
		this.requestType = requestType;
	}
	
	public RequestDetails(String url, HttpMethod requestType, String accessToken) {
		super();
		this.url = url;
		this.requestType = requestType;
		this.accessToken = accessToken;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getRequestType() {
		return requestType;
	}

	public void setRequestType(HttpMethod requestType) {
		this.requestType = requestType;
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "RequestDetails [url=" + url + ", requestType=" + requestType + "]";
	}
}

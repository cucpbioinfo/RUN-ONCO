package run.onco.api.business.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class MasterDataDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4664554113786046368L;

	public MasterDataDto() {
	}

	public MasterDataDto(List<T> items) {
		this.items = items;
	}

	private List<T> items;
	private String token;
	private String type;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}

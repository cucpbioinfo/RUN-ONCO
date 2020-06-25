package run.onco.api.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class DataItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1554319624411261715L;

	private Integer id;
	private String code;
	private String name;
	
	public DataItem() {
	}
	
	public DataItem(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public DataItem(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package run.onco.api.business.dto;

import java.io.Serializable;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class TemplateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -664029093022499093L;

	private String type;
	private String fileName;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

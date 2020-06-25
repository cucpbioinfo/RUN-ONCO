package run.onco.api.business.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class OmicsDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6579860681262432060L;

	private String fileName;
	private String sampleName;
	private List<T> omics;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public List<T> getOmics() {
		return omics;
	}

	public void setOmics(List<T> omics) {
		this.omics = omics;
	}

}

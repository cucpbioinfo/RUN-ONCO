package run.onco.batch.api.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class BatchJobDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8471752551989384576L;

	private String incomingDir;
	private String outgoingDir;
	private String dataDir;
	private String jobKey;
	private String jobName;
	private Map<String, String> params;

	public String getIncomingDir() {
		return incomingDir;
	}

	public void setIncomingDir(String incomingDir) {
		this.incomingDir = incomingDir;
	}

	public String getOutgoingDir() {
		return outgoingDir;
	}

	public void setOutgoingDir(String outgoingDir) {
		this.outgoingDir = outgoingDir;
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public String getJobKey() {
		return jobKey;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}

package run.onco.component.api.dto;

import java.io.Serializable;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class DynamicContentDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5734159495600780639L;

	private Integer id;
	private String componentName;
	private String moduleName;
	private String modulePath;
	private String inputField;
	private String analysisName;
	private String parentCompName;
	private String dataTypeAnalysis;
	private Long userId;
	private String status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModulePath() {
		return modulePath;
	}

	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

	public String getInputField() {
		return inputField;
	}

	public void setInputField(String inputField) {
		this.inputField = inputField;
	}

	public String getAnalysisName() {
		return analysisName;
	}

	public void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}

	public String getParentCompName() {
		return parentCompName;
	}

	public void setParentCompName(String parentCompName) {
		this.parentCompName = parentCompName;
	}

	public String getDataTypeAnalysis() {
		return dataTypeAnalysis;
	}

	public void setDataTypeAnalysis(String dataTypeAnalysis) {
		this.dataTypeAnalysis = dataTypeAnalysis;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}

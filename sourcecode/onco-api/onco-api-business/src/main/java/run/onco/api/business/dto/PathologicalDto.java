package run.onco.api.business.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class PathologicalDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -753964795469253988L;

	private String pathologyNo;
	private String tissueReportDate;
	private String resectionBiopsySite;
	private DataItem tissueProcedure;
	private DataItem tissueType;
	private DataItem morphology;
	private DataItem histologicGrade;
	private Long id;
	private String status;

	@Size(max = AppConstants.MAXLEN_PATHO_NO)
	public String getPathologyNo() {
		return pathologyNo;
	}

	public void setPathologyNo(String pathologyNo) {
		this.pathologyNo = pathologyNo;
	}

	public String getTissueReportDate() {
		return tissueReportDate;
	}

	public void setTissueReportDate(String tissueReportDate) {
		this.tissueReportDate = tissueReportDate;
	}

	@Size(max = AppConstants.MAXLEN_RESECTION_BIOPSY_SITE)
	public String getResectionBiopsySite() {
		return resectionBiopsySite;
	}

	public void setResectionBiopsySite(String resectionBiopsySite) {
		this.resectionBiopsySite = resectionBiopsySite;
	}

	public DataItem getTissueProcedure() {
		return tissueProcedure;
	}

	public void setTissueProcedure(DataItem tissueProcedure) {
		this.tissueProcedure = tissueProcedure;
	}

	public DataItem getTissueType() {
		return tissueType;
	}

	public void setTissueType(DataItem tissueType) {
		this.tissueType = tissueType;
	}

	public DataItem getMorphology() {
		return morphology;
	}

	public void setMorphology(DataItem morphology) {
		this.morphology = morphology;
	}

	public DataItem getHistologicGrade() {
		return histologicGrade;
	}

	public void setHistologicGrade(DataItem histologicGrade) {
		this.histologicGrade = histologicGrade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}

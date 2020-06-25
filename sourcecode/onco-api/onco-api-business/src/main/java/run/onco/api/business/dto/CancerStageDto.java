package run.onco.api.business.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class CancerStageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5039425772102882037L;

	private DataItem ajcc;
	private DataItem stageType;
	private DataItem cTStage;
	private DataItem cTSubstage;
	private DataItem cNStage;
	private DataItem cNSubstage;
	private DataItem pTStage;
	private DataItem pTSubstage;
	private DataItem pNStage;
	private DataItem pNSubstage;
	private DataItem ypTStage;
	private DataItem ypTSubstage;
	private DataItem ypNStage;
	private DataItem ypNSubstage;
	private DataItem mStage;
	private DataItem mSubstage;
	private DataItem clinicalStage;
	private DataItem pathoStage;
	private DataItem pathoSubstage;
	private Long id;

	public DataItem getAjcc() {
		return ajcc;
	}

	public void setAjcc(DataItem ajcc) {
		this.ajcc = ajcc;
	}

	public DataItem getStageType() {
		return stageType;
	}

	public void setStageType(DataItem stageType) {
		this.stageType = stageType;
	}

	public DataItem getcTStage() {
		return cTStage;
	}

	public void setcTStage(DataItem cTStage) {
		this.cTStage = cTStage;
	}

	public DataItem getcNStage() {
		return cNStage;
	}

	public void setcNStage(DataItem cNStage) {
		this.cNStage = cNStage;
	}

	public DataItem getpTStage() {
		return pTStage;
	}

	public void setpTStage(DataItem pTStage) {
		this.pTStage = pTStage;
	}

	public DataItem getpNStage() {
		return pNStage;
	}

	public void setpNStage(DataItem pNStage) {
		this.pNStage = pNStage;
	}

	public DataItem getYpTStage() {
		return ypTStage;
	}

	public void setYpTStage(DataItem ypTStage) {
		this.ypTStage = ypTStage;
	}

	public DataItem getYpNStage() {
		return ypNStage;
	}

	public void setYpNStage(DataItem ypNStage) {
		this.ypNStage = ypNStage;
	}

	public DataItem getmStage() {
		return mStage;
	}

	public void setmStage(DataItem mStage) {
		this.mStage = mStage;
	}

	public DataItem getClinicalStage() {
		return clinicalStage;
	}

	public void setClinicalStage(DataItem clinicalStage) {
		this.clinicalStage = clinicalStage;
	}

	public DataItem getcTSubstage() {
		return cTSubstage;
	}

	public void setcTSubstage(DataItem cTSubstage) {
		this.cTSubstage = cTSubstage;
	}

	public DataItem getcNSubstage() {
		return cNSubstage;
	}

	public void setcNSubstage(DataItem cNSubstage) {
		this.cNSubstage = cNSubstage;
	}

	public DataItem getpTSubstage() {
		return pTSubstage;
	}

	public void setpTSubstage(DataItem pTSubstage) {
		this.pTSubstage = pTSubstage;
	}

	public DataItem getpNSubstage() {
		return pNSubstage;
	}

	public void setpNSubstage(DataItem pNSubstage) {
		this.pNSubstage = pNSubstage;
	}

	public DataItem getYpTSubstage() {
		return ypTSubstage;
	}

	public void setYpTSubstage(DataItem ypTSubstage) {
		this.ypTSubstage = ypTSubstage;
	}

	public DataItem getYpNSubstage() {
		return ypNSubstage;
	}

	public void setYpNSubstage(DataItem ypNSubstage) {
		this.ypNSubstage = ypNSubstage;
	}

	public DataItem getmSubstage() {
		return mSubstage;
	}

	public void setmSubstage(DataItem mSubstage) {
		this.mSubstage = mSubstage;
	}

	public DataItem getPathoStage() {
		return pathoStage;
	}

	public void setPathoStage(DataItem pathoStage) {
		this.pathoStage = pathoStage;
	}

	public DataItem getPathoSubstage() {
		return pathoSubstage;
	}

	public void setPathoSubstage(DataItem pathoSubstage) {
		this.pathoSubstage = pathoSubstage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}

package run.onco.api.business.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class ActionableVariantDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121665040819936331L;

	private Long variantId;
	private String isoform;
	private String refSeq;
	private String entrezGeneId; // Entrez Gene ID
	private String gene;
	private String alteration;
	private String proteinChange;
	private String cancerType;
	private String level;
	private String drugs;
	private String dataVersion;
	private Date dataDate;
	private String drugPmids;
	private String drugAbstract;

	public Long getVariantId() {
		return variantId;
	}

	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}

	public String getIsoform() {
		return isoform;
	}

	public void setIsoform(String isoform) {
		this.isoform = isoform;
	}

	public String getRefSeq() {
		return refSeq;
	}

	public void setRefSeq(String refSeq) {
		this.refSeq = refSeq;
	}

	public String getEntrezGeneId() {
		return entrezGeneId;
	}

	public void setEntrezGeneId(String entrezGeneId) {
		this.entrezGeneId = entrezGeneId;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getAlteration() {
		return alteration;
	}

	public void setAlteration(String alteration) {
		this.alteration = alteration;
	}

	public String getProteinChange() {
		return proteinChange;
	}

	public void setProteinChange(String proteinChange) {
		this.proteinChange = proteinChange;
	}

	public String getCancerType() {
		return cancerType;
	}

	public void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	public String getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public String getDrugPmids() {
		return drugPmids;
	}

	public void setDrugPmids(String drugPmids) {
		this.drugPmids = drugPmids;
	}

	public String getDrugAbstract() {
		return drugAbstract;
	}

	public void setDrugAbstract(String drugAbstract) {
		this.drugAbstract = drugAbstract;
	}

}

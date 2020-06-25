package run.onco.api.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_M_ACTIONABLE_VARIANT", indexes = {
        @Index(columnList = "ISOFORM", name = "IDX_ANNO_ISOFORM"),
        @Index(columnList = "GENE", name = "IDX_ANNO_GENE")
})
public class TbMActionableVariant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1225551841875437821L;

	private Integer id;
	private String isoform;
	private String refSeq;
	private String entrezGeneId; // Entrez Gene ID
	private String gene;
	private String alteration;
	private String proteinChange;
	private String cancerType;
	private String level;
	private String drugs;
	private String drugPmids;
	private String drugAbstract;
	private TbCDataVersion dataVersion;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACT_VAR_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ISOFORM", length = 20)
	public String getIsoform() {
		return isoform;
	}

	public void setIsoform(String isoform) {
		this.isoform = isoform;
	}

	@Column(name = "REF_SEQ", length = 20)
	public String getRefSeq() {
		return refSeq;
	}

	public void setRefSeq(String refSeq) {
		this.refSeq = refSeq;
	}

	@Column(name = "ENTREZ_GENE_ID", length = 50)
	public String getEntrezGeneId() {
		return entrezGeneId;
	}

	public void setEntrezGeneId(String entrezGeneId) {
		this.entrezGeneId = entrezGeneId;
	}

	@Column(name = "GENE", length = 50)
	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	@Column(name = "ALTERATION", length = 100)
	public String getAlteration() {
		return alteration;
	}

	public void setAlteration(String alteration) {
		this.alteration = alteration;
	}

	@Column(name = "PROTEIN_CHANGE", length = 100)
	public String getProteinChange() {
		return proteinChange;
	}

	public void setProteinChange(String proteinChange) {
		this.proteinChange = proteinChange;
	}

	@Column(name = "CANCER_TYPE", length = 100)
	public String getCancerType() {
		return cancerType;
	}

	public void setCancerType(String cancerType) {
		this.cancerType = cancerType;
	}

	@Column(name = "LEVEL", length = 2)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Column(name = "DRUGS", length = 500)
	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	@Column(name = "DRUG_PMIDS", length = 500)
	public String getDrugPmids() {
		return drugPmids;
	}

	public void setDrugPmids(String drugPmids) {
		this.drugPmids = drugPmids;
	}

	@Column(name = "DRUG_ABSTRACT", length = 1000)
	public String getDrugAbstract() {
		return drugAbstract;
	}

	public void setDrugAbstract(String drugAbstract) {
		this.drugAbstract = drugAbstract;
	}

	@ManyToOne
	@JoinColumn(name = "DATA_VER_ID")
	public TbCDataVersion getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(TbCDataVersion dataVersion) {
		this.dataVersion = dataVersion;
	}

}

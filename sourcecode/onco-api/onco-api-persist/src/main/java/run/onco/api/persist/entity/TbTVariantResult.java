package run.onco.api.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_T_VARIANT_RESULT")
public class TbTVariantResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7666349832272687970L;

	private Long id;
	private String genotype;
	private BigDecimal refAlleleDepth;
	private BigDecimal altAlleleDepth;
	private BigDecimal alleleFrequency;
	private BigDecimal altAlleleF1r2;
	private BigDecimal altAlleleF2r1;
	private BigDecimal oxogReadCounts;
	private BigDecimal refAlleleF1r2;
	private BigDecimal refAlleleF2r1;
	private BigDecimal refAlleleQss;
	private BigDecimal altAlleleQss;
	private String sampleType; // TUMOR/NORMAL
	private TbTVariantCall variantCall;
	private TbTSampleVariant sampleVariant;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VAR_RES_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "GENOTYPE", length = 10)
	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	@Column(name = "REF_ALLELE_DEPTH", precision = 20, scale = 5)
	public BigDecimal getRefAlleleDepth() {
		return refAlleleDepth;
	}

	public void setRefAlleleDepth(BigDecimal refAlleleDepth) {
		this.refAlleleDepth = refAlleleDepth;
	}

	@Column(name = "ALT_ALLELE_DEPTH", precision = 20, scale = 5)
	public BigDecimal getAltAlleleDepth() {
		return altAlleleDepth;
	}

	public void setAltAlleleDepth(BigDecimal altAlleleDepth) {
		this.altAlleleDepth = altAlleleDepth;
	}

	@Column(name = "ALLELE_FREQUENCY", precision = 20, scale = 5)
	public BigDecimal getAlleleFrequency() {
		return alleleFrequency;
	}

	public void setAlleleFrequency(BigDecimal alleleFrequency) {
		this.alleleFrequency = alleleFrequency;
	}

	@Column(name = "ALT_ALLELE_F1R2", precision = 20, scale = 5)
	public BigDecimal getAltAlleleF1r2() {
		return altAlleleF1r2;
	}

	public void setAltAlleleF1r2(BigDecimal altAlleleF1r2) {
		this.altAlleleF1r2 = altAlleleF1r2;
	}

	@Column(name = "ALT_ALLELE_F2R1", precision = 20, scale = 5)
	public BigDecimal getAltAlleleF2r1() {
		return altAlleleF2r1;
	}

	public void setAltAlleleF2r1(BigDecimal altAlleleF2r1) {
		this.altAlleleF2r1 = altAlleleF2r1;
	}

	@Column(name = "OXOG_READ_COUNTS", precision = 20, scale = 5)
	public BigDecimal getOxogReadCounts() {
		return oxogReadCounts;
	}

	public void setOxogReadCounts(BigDecimal oxogReadCounts) {
		this.oxogReadCounts = oxogReadCounts;
	}

	@Column(name = "REF_ALLELE_F1R2", precision = 20, scale = 5)
	public BigDecimal getRefAlleleF1r2() {
		return refAlleleF1r2;
	}

	public void setRefAlleleF1r2(BigDecimal refAlleleF1r2) {
		this.refAlleleF1r2 = refAlleleF1r2;
	}

	@Column(name = "REF_ALLELE_F2R1", precision = 20, scale = 5)
	public BigDecimal getRefAlleleF2r1() {
		return refAlleleF2r1;
	}

	public void setRefAlleleF2r1(BigDecimal refAlleleF2r1) {
		this.refAlleleF2r1 = refAlleleF2r1;
	}

	@Column(name = "REF_ALLELE_QSS", precision = 20, scale = 5)
	public BigDecimal getRefAlleleQss() {
		return refAlleleQss;
	}

	public void setRefAlleleQss(BigDecimal refAlleleQss) {
		this.refAlleleQss = refAlleleQss;
	}

	@Column(name = "ALT_ALLELE_QSS", precision = 20, scale = 5)
	public BigDecimal getAltAlleleQss() {
		return altAlleleQss;
	}

	public void setAltAlleleQss(BigDecimal altAlleleQss) {
		this.altAlleleQss = altAlleleQss;
	}

	@Column(name = "SAMPLE_TYPE", length = 1)
	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	@ManyToOne
	@JoinColumn(name = "VAR_ID")
	public TbTVariantCall getVariantCall() {
		return variantCall;
	}

	public void setVariantCall(TbTVariantCall variantCall) {
		this.variantCall = variantCall;
	}

	@ManyToOne
	@JoinColumn(name = "SAMPLE_VAR_ID")
	public TbTSampleVariant getSampleVariant() {
		return sampleVariant;
	}

	public void setSampleVariant(TbTSampleVariant sampleVariant) {
		this.sampleVariant = sampleVariant;
	}

}

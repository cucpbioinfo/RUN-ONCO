package run.onco.api.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_T_VARIANT_CALL", indexes = {
        @Index(columnList = "CHROMOSOME", name = "VAR_IDX_CHROM"),
        @Index(columnList = "POSITION", name = "VAR_IDX_POS"),
        @Index(columnList = "REF_ALLELE", name = "VAR_IDX_REF"),
        @Index(columnList = "ALT_ALLELE", name = "VAR_IDX_ALT")
})
public class TbTVariantCall implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7073677627355205628L;

	private Long id;
	private String chromosome;
	private String variant;
	private Long position;
	private String refAllele;
	private String altAllele;
	private BigDecimal quality;
	private String filter;

	// Info
	private BigDecimal ecnt;
	private BigDecimal hcnt;
	private BigDecimal maxEd;
	private BigDecimal minEd;
	private BigDecimal nlod;
	private BigDecimal tlod;

	private TbTSampleVariant sampleVariant;
	private List<TbTVariantAnnotation> variantAnnotationList;
	private List<TbTVariantResult> variantResultList;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VAR_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CHROMOSOME", length = 100)
	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	@Column(name = "POSITION")
	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	@Column(name = "VARIANT", length = 50)
	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	@Column(name = "REF_ALLELE", length = 1000)
	public String getRefAllele() {
		return refAllele;
	}

	public void setRefAllele(String refAllele) {
		this.refAllele = refAllele;
	}

	@Column(name = "ALT_ALLELE", length = 1000)
	public String getAltAllele() {
		return altAllele;
	}

	public void setAltAllele(String altAllele) {
		this.altAllele = altAllele;
	}

	@Column(name = "QUALITY", precision = 20, scale = 5)
	public BigDecimal getQuality() {
		return quality;
	}

	public void setQuality(BigDecimal quality) {
		this.quality = quality;
	}

	@Column(name = "FILTER", length = 10)
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Column(name = "ECNT", precision = 20, scale = 5)
	public BigDecimal getEcnt() {
		return ecnt;
	}

	public void setEcnt(BigDecimal ecnt) {
		this.ecnt = ecnt;
	}

	@Column(name = "HCNT", precision = 20, scale = 5)
	public BigDecimal getHcnt() {
		return hcnt;
	}

	public void setHcnt(BigDecimal hcnt) {
		this.hcnt = hcnt;
	}

	@Column(name = "MAX_ED", precision = 20, scale = 5)
	public BigDecimal getMaxEd() {
		return maxEd;
	}

	public void setMaxEd(BigDecimal maxEd) {
		this.maxEd = maxEd;
	}

	@Column(name = "MIN_ED", precision = 20, scale = 5)
	public BigDecimal getMinEd() {
		return minEd;
	}

	public void setMinEd(BigDecimal minEd) {
		this.minEd = minEd;
	}

	@Column(name = "NLOD", precision = 20, scale = 5)
	public BigDecimal getNlod() {
		return nlod;
	}

	public void setNlod(BigDecimal nlod) {
		this.nlod = nlod;
	}

	@Column(name = "TLOD", precision = 20, scale = 5)
	public BigDecimal getTlod() {
		return tlod;
	}

	public void setTlod(BigDecimal tlod) {
		this.tlod = tlod;
	}

	@ManyToOne
	@JoinColumn(name = "SAMPLE_VAR_ID")
	public TbTSampleVariant getSampleVariant() {
		return sampleVariant;
	}

	public void setSampleVariant(TbTSampleVariant sampleVariant) {
		this.sampleVariant = sampleVariant;
	}

	@OneToMany(mappedBy = "variantCall", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<TbTVariantAnnotation> getVariantAnnotationList() {
		return variantAnnotationList;
	}

	public void setVariantAnnotationList(List<TbTVariantAnnotation> variantAnnotationList) {
		this.variantAnnotationList = variantAnnotationList;
	}

	@OneToMany(mappedBy = "variantCall", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<TbTVariantResult> getVariantResultList() {
		return variantResultList;
	}

	public void setVariantResultList(List<TbTVariantResult> variantResultList) {
		this.variantResultList = variantResultList;
	}

}

package run.onco.api.common.vcf.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class VariantCallBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9111177616514810238L;

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

	private List<VariantAnnotationBean> variantAnnotationList;
	private List<VariantResultBean> variantResultList;

	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getRefAllele() {
		return refAllele;
	}

	public void setRefAllele(String refAllele) {
		this.refAllele = refAllele;
	}

	public String getAltAllele() {
		return altAllele;
	}

	public void setAltAllele(String altAllele) {
		this.altAllele = altAllele;
	}

	public BigDecimal getQuality() {
		return quality;
	}

	public void setQuality(BigDecimal quality) {
		this.quality = quality;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public BigDecimal getEcnt() {
		return ecnt;
	}

	public void setEcnt(BigDecimal ecnt) {
		this.ecnt = ecnt;
	}

	public BigDecimal getHcnt() {
		return hcnt;
	}

	public void setHcnt(BigDecimal hcnt) {
		this.hcnt = hcnt;
	}

	public BigDecimal getMaxEd() {
		return maxEd;
	}

	public void setMaxEd(BigDecimal maxEd) {
		this.maxEd = maxEd;
	}

	public BigDecimal getMinEd() {
		return minEd;
	}

	public void setMinEd(BigDecimal minEd) {
		this.minEd = minEd;
	}

	public BigDecimal getNlod() {
		return nlod;
	}

	public void setNlod(BigDecimal nlod) {
		this.nlod = nlod;
	}

	public BigDecimal getTlod() {
		return tlod;
	}

	public void setTlod(BigDecimal tlod) {
		this.tlod = tlod;
	}

	public List<VariantAnnotationBean> getVariantAnnotationList() {
		return variantAnnotationList;
	}

	public void setVariantAnnotationList(List<VariantAnnotationBean> variantAnnotationList) {
		this.variantAnnotationList = variantAnnotationList;
	}

	public List<VariantResultBean> getVariantResultList() {
		return variantResultList;
	}

	public void setVariantResultList(List<VariantResultBean> variantResultList) {
		this.variantResultList = variantResultList;
	}

}

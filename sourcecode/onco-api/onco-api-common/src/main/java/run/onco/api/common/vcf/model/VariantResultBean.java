package run.onco.api.common.vcf.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class VariantResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7075576710088834896L;

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
	private String sampleType;

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	public BigDecimal getRefAlleleDepth() {
		return refAlleleDepth;
	}

	public void setRefAlleleDepth(BigDecimal refAlleleDepth) {
		this.refAlleleDepth = refAlleleDepth;
	}

	public BigDecimal getAltAlleleDepth() {
		return altAlleleDepth;
	}

	public void setAltAlleleDepth(BigDecimal altAlleleDepth) {
		this.altAlleleDepth = altAlleleDepth;
	}

	public BigDecimal getAlleleFrequency() {
		return alleleFrequency;
	}

	public void setAlleleFrequency(BigDecimal alleleFrequency) {
		this.alleleFrequency = alleleFrequency;
	}

	public BigDecimal getAltAlleleF1r2() {
		return altAlleleF1r2;
	}

	public void setAltAlleleF1r2(BigDecimal altAlleleF1r2) {
		this.altAlleleF1r2 = altAlleleF1r2;
	}

	public BigDecimal getAltAlleleF2r1() {
		return altAlleleF2r1;
	}

	public void setAltAlleleF2r1(BigDecimal altAlleleF2r1) {
		this.altAlleleF2r1 = altAlleleF2r1;
	}

	public BigDecimal getOxogReadCounts() {
		return oxogReadCounts;
	}

	public void setOxogReadCounts(BigDecimal oxogReadCounts) {
		this.oxogReadCounts = oxogReadCounts;
	}

	public BigDecimal getRefAlleleF1r2() {
		return refAlleleF1r2;
	}

	public void setRefAlleleF1r2(BigDecimal refAlleleF1r2) {
		this.refAlleleF1r2 = refAlleleF1r2;
	}

	public BigDecimal getRefAlleleF2r1() {
		return refAlleleF2r1;
	}

	public void setRefAlleleF2r1(BigDecimal refAlleleF2r1) {
		this.refAlleleF2r1 = refAlleleF2r1;
	}

	public BigDecimal getRefAlleleQss() {
		return refAlleleQss;
	}

	public void setRefAlleleQss(BigDecimal refAlleleQss) {
		this.refAlleleQss = refAlleleQss;
	}

	public BigDecimal getAltAlleleQss() {
		return altAlleleQss;
	}

	public void setAltAlleleQss(BigDecimal altAlleleQss) {
		this.altAlleleQss = altAlleleQss;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

}

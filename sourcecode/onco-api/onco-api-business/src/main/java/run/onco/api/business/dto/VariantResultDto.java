package run.onco.api.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class VariantResultDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6261881389734805645L;

	private BigDecimal refAlleleDepth;
	private BigDecimal altAlleleDepth;
	private BigDecimal alleleFrequency;

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

}

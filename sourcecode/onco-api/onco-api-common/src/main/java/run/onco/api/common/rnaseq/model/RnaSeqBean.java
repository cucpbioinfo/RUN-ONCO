package run.onco.api.common.rnaseq.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class RnaSeqBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8961709426519595413L;
	
	private String geneId;
	private String geneName;
	private String ref;
	private String strand;
	private Integer start;
	private Integer end;
	private BigDecimal coverage;
	private BigDecimal fpkm;
	private BigDecimal tpm;

	public String getGeneId() {
		return geneId;
	}

	public void setGeneId(String geneId) {
		this.geneId = geneId;
	}

	public String getGeneName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getStrand() {
		return strand;
	}

	public void setStrand(String strand) {
		this.strand = strand;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public BigDecimal getCoverage() {
		return coverage;
	}

	public void setCoverage(BigDecimal coverage) {
		this.coverage = coverage;
	}

	public BigDecimal getFpkm() {
		return fpkm;
	}

	public void setFpkm(BigDecimal fpkm) {
		this.fpkm = fpkm;
	}

	public BigDecimal getTpm() {
		return tpm;
	}

	public void setTpm(BigDecimal tpm) {
		this.tpm = tpm;
	}

}

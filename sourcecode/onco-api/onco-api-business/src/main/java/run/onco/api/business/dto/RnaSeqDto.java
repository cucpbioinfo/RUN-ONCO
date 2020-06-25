package run.onco.api.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class RnaSeqDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8444089552439946157L;

	private String geneId;
	private String geneName;
	private String ref;
	private Integer start;
	private Integer end;
	private BigDecimal tpm;
	private String strand;
	private BigDecimal coverage;
	private BigDecimal fpkm;

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

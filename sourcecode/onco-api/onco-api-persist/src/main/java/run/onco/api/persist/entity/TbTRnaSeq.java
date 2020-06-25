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
@Table(name = "TB_T_RNA_SEQ")
public class TbTRnaSeq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4759560627492599437L;

	private Long id;
	private String geneId;
	private String geneName;
	private String ref;
	private String strand;
	private Integer start;
	private Integer end;
	private BigDecimal coverage;
	private BigDecimal fpkm;
	private BigDecimal tpm;
	private TbTSampleRnaSeq sampleRnaSeq;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RNA_SEQ_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "GENE_ID", length = 100)
	public String getGeneId() {
		return geneId;
	}

	public void setGeneId(String geneId) {
		this.geneId = geneId;
	}

	@Column(name = "GENE_NAME", length = 250)
	public String getGeneName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}

	@Column(name = "REF", length = 250)
	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Column(name = "STRAND", length = 10)
	public String getStrand() {
		return strand;
	}

	public void setStrand(String strand) {
		this.strand = strand;
	}

	@Column(name = "START")
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	@Column(name = "END")
	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	@Column(name = "COVERAGE", precision = 12, scale = 6)
	public BigDecimal getCoverage() {
		return coverage;
	}

	public void setCoverage(BigDecimal coverage) {
		this.coverage = coverage;
	}

	@Column(name = "FPKM", precision = 12, scale = 6)
	public BigDecimal getFpkm() {
		return fpkm;
	}

	public void setFpkm(BigDecimal fpkm) {
		this.fpkm = fpkm;
	}

	@Column(name = "TPM", precision = 12, scale = 6)
	public BigDecimal getTpm() {
		return tpm;
	}

	public void setTpm(BigDecimal tpm) {
		this.tpm = tpm;
	}

	@ManyToOne
	@JoinColumn(name = "SAMPLE_RNA_SEQ_ID")
	public TbTSampleRnaSeq getSampleRnaSeq() {
		return sampleRnaSeq;
	}

	public void setSampleRnaSeq(TbTSampleRnaSeq sampleRnaSeq) {
		this.sampleRnaSeq = sampleRnaSeq;
	}

}

package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_T_VARIANT_ANNOTATION", indexes = {
		@Index(columnList = "SYMBOL", name = "VAR_ANN_IDX_SYMBOL"),
        @Index(columnList = "FEATURE", name = "VAR_ANN_IDX_FEAT")
})
public class TbTVariantAnnotation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7666349832272687970L;

	private Long id;
	private String allele;
	private String consequence;
	private String impact;
	private String symbol;
	private String gene;
	private String featureType;
	private String feature;
	private String biotype;
	private String exon;
	private String intron;
	private String hgvsp;
	private String hgvsc;
	private String cdnaPosition;
	private String cdsPosition;
	private String proteinPosition;
	private String aminoAcids;
	private String codons;
	private String existingVariantion;
	private String distance;
	private String strand;
	private String flags;
	private String symbolSource;
	private String hgncId;
	private TbTVariantCall variantCall;
	private TbTSampleVariant sampleVariant;
	private String oncoKbAnnotated;
	private Date createDate;
	private Date updateDate;
	private TbMUser createUser;
	private TbMUser updateUser;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VAR_ANN_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ALLELE", length = 1000)
	public String getAllele() {
		return allele;
	}

	public void setAllele(String allele) {
		this.allele = allele;
	}

	@Column(name = "CONSEQUENCE", length = 100)
	public String getConsequence() {
		return consequence;
	}

	public void setConsequence(String consequence) {
		this.consequence = consequence;
	}

	@Column(name = "IMPACT", length = 100)
	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	@Column(name = "SYMBOL", length = 100)
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Column(name = "GENE", length = 250)
	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	@Column(name = "FEATURE_TYPE", length = 100)
	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	@Column(name = "FEATURE", length = 100)
	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	@Column(name = "BIOTYPE", length = 100)
	public String getBiotype() {
		return biotype;
	}

	public void setBiotype(String biotype) {
		this.biotype = biotype;
	}

	@Column(name = "EXON", length = 100)
	public String getExon() {
		return exon;
	}

	public void setExon(String exon) {
		this.exon = exon;
	}

	@Column(name = "INTRON", length = 100)
	public String getIntron() {
		return intron;
	}

	public void setIntron(String intron) {
		this.intron = intron;
	}

	@Column(name = "HGVSP", length = 255)
	public String getHgvsp() {
		return hgvsp;
	}

	public void setHgvsp(String hgvsp) {
		this.hgvsp = hgvsp;
	}

	@Column(name = "HGVSC", length = 255)
	public String getHgvsc() {
		return hgvsc;
	}

	public void setHgvsc(String hgvsc) {
		this.hgvsc = hgvsc;
	}

	@Column(name = "CDNA_POSITION", length = 100)
	public String getCdnaPosition() {
		return cdnaPosition;
	}

	public void setCdnaPosition(String cdnaPosition) {
		this.cdnaPosition = cdnaPosition;
	}

	@Column(name = "CDS_POSITION", length = 100)
	public String getCdsPosition() {
		return cdsPosition;
	}

	public void setCdsPosition(String cdsPosition) {
		this.cdsPosition = cdsPosition;
	}

	@Column(name = "PROTEIN_POSITION", length = 100)
	public String getProteinPosition() {
		return proteinPosition;
	}

	public void setProteinPosition(String proteinPosition) {
		this.proteinPosition = proteinPosition;
	}

	@Column(name = "AMINO_ACIDS", length = 100)
	public String getAminoAcids() {
		return aminoAcids;
	}

	public void setAminoAcids(String aminoAcids) {
		this.aminoAcids = aminoAcids;
	}

	@Column(name = "CODONS")
	public String getCodons() {
		return codons;
	}

	public void setCodons(String codons) {
		this.codons = codons;
	}

	@Column(name = "EXISTING_VARIANTION", length = 500)
	public String getExistingVariantion() {
		return existingVariantion;
	}

	public void setExistingVariantion(String existingVariantion) {
		this.existingVariantion = existingVariantion;
	}

	@Column(name = "DISTANCE", length = 100)
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Column(name = "STRAND", length = 100)
	public String getStrand() {
		return strand;
	}

	public void setStrand(String strand) {
		this.strand = strand;
	}

	@Column(name = "FLAGS")
	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	@Column(name = "SYMBOL_SOURCE", length = 100)
	public String getSymbolSource() {
		return symbolSource;
	}

	public void setSymbolSource(String symbolSource) {
		this.symbolSource = symbolSource;
	}

	@Column(name = "HGNC_ID", length = 100)
	public String getHgncId() {
		return hgncId;
	}

	public void setHgncId(String hgncId) {
		this.hgncId = hgncId;
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

	@Column(name = "ONCOKB_ANNOTATED", length = 1)
	public String getOncoKbAnnotated() {
		return oncoKbAnnotated;
	}

	public void setOncoKbAnnotated(String oncoKbAnnotated) {
		this.oncoKbAnnotated = oncoKbAnnotated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@ManyToOne
	@JoinColumn(name = "CREATE_USER_ID")
	public TbMUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TbMUser createUser) {
		this.createUser = createUser;
	}

	@ManyToOne
	@JoinColumn(name = "UPDATE_USER_ID")
	public TbMUser getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(TbMUser updateUser) {
		this.updateUser = updateUser;
	}

}

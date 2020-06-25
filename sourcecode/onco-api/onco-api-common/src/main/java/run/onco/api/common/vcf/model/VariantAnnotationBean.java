package run.onco.api.common.vcf.model;

import java.io.Serializable;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class VariantAnnotationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2330605252179516405L;

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

	public String getAllele() {
		return allele;
	}

	public void setAllele(String allele) {
		this.allele = allele;
	}

	public String getConsequence() {
		return consequence;
	}

	public void setConsequence(String consequence) {
		this.consequence = consequence;
	}

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getBiotype() {
		return biotype;
	}

	public void setBiotype(String biotype) {
		this.biotype = biotype;
	}

	public String getExon() {
		return exon;
	}

	public void setExon(String exon) {
		this.exon = exon;
	}

	public String getIntron() {
		return intron;
	}

	public void setIntron(String intron) {
		this.intron = intron;
	}

	public String getHgvsp() {
		return hgvsp;
	}

	public void setHgvsp(String hgvsp) {
		this.hgvsp = hgvsp;
	}

	public String getHgvsc() {
		return hgvsc;
	}

	public void setHgvsc(String hgvsc) {
		this.hgvsc = hgvsc;
	}

	public String getCdnaPosition() {
		return cdnaPosition;
	}

	public void setCdnaPosition(String cdnaPosition) {
		this.cdnaPosition = cdnaPosition;
	}

	public String getCdsPosition() {
		return cdsPosition;
	}

	public void setCdsPosition(String cdsPosition) {
		this.cdsPosition = cdsPosition;
	}

	public String getProteinPosition() {
		return proteinPosition;
	}

	public void setProteinPosition(String proteinPosition) {
		this.proteinPosition = proteinPosition;
	}

	public String getAminoAcids() {
		return aminoAcids;
	}

	public void setAminoAcids(String aminoAcids) {
		this.aminoAcids = aminoAcids;
	}

	public String getCodons() {
		return codons;
	}

	public void setCodons(String codons) {
		this.codons = codons;
	}

	public String getExistingVariantion() {
		return existingVariantion;
	}

	public void setExistingVariantion(String existingVariantion) {
		this.existingVariantion = existingVariantion;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getStrand() {
		return strand;
	}

	public void setStrand(String strand) {
		this.strand = strand;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getSymbolSource() {
		return symbolSource;
	}

	public void setSymbolSource(String symbolSource) {
		this.symbolSource = symbolSource;
	}

	public String getHgncId() {
		return hgncId;
	}

	public void setHgncId(String hgncId) {
		this.hgncId = hgncId;
	}

}

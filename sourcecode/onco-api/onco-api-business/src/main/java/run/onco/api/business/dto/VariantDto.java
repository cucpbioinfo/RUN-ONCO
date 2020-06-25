package run.onco.api.business.dto;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */

@JsonInclude(Include.NON_NULL)
public class VariantDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6944128641271617931L;

	private Long variantId;
	private String chromosome;
	private Long position;
	private String refAllele;
	private String altAllele;

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

	private VariantResultDto tumor;
	private VariantResultDto normal;

	private String oncoKbAnnotated;

	public Long getVariantId() {
		return variantId;
	}

	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}

	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
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

	public String getChromDisplay() {
		Pattern x = Pattern.compile(AppConstants.CHROMOSOME_PATTERN, Pattern.MULTILINE);
		Matcher m = x.matcher(this.chromosome);
		if (m.find()) {
			return m.group(1);
		}
		return this.chromosome;
	}

	public String getVariant() {
		return String.format("%s:%s %s / %s ", this.getChromDisplay(), this.position, this.refAllele, this.altAllele);
	}

	public VariantResultDto getTumor() {
		return tumor;
	}

	public void setTumor(VariantResultDto tumor) {
		this.tumor = tumor;
	}

	public VariantResultDto getNormal() {
		return normal;
	}

	public void setNormal(VariantResultDto normal) {
		this.normal = normal;
	}

	public String getOncoKbAnnotated() {
		return oncoKbAnnotated;
	}

	public void setOncoKbAnnotated(String oncoKbAnnotated) {
		this.oncoKbAnnotated = oncoKbAnnotated;
	}

}

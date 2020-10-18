/*
 * OncoKB APIs
 * OncoKB, a comprehensive and curated precision oncology knowledge base, offers oncologists detailed, evidence-based information about individual somatic mutations and structural alterations present in patient tumors with the goal of supporting optimal treatment decisions.
 *
 * OpenAPI spec version: v1.0.0
 * Contact: team@oncokb.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package run.onco.connector.oncokb.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * ActionableGene
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-10-03T12:49:32.816Z")
public class ActionableGene {
  @SerializedName("abstracts")
  private String abstracts = null;

  @SerializedName("cancerType")
  private String cancerType = null;

  @SerializedName("drugs")
  private String drugs = null;

  @SerializedName("entrezGeneId")
  private Integer entrezGeneId = null;

  @SerializedName("gene")
  private String gene = null;

  @SerializedName("isoform")
  private String isoform = null;

  @SerializedName("level")
  private String level = null;

  @SerializedName("pmids")
  private String pmids = null;

  @SerializedName("proteinChange")
  private String proteinChange = null;

  @SerializedName("refSeq")
  private String refSeq = null;

  @SerializedName("variant")
  private String variant = null;

  public ActionableGene abstracts(String abstracts) {
    this.abstracts = abstracts;
    return this;
  }

   /**
   * Get abstracts
   * @return abstracts
  **/
  @ApiModelProperty(value = "")
  public String getAbstracts() {
    return abstracts;
  }

  public void setAbstracts(String abstracts) {
    this.abstracts = abstracts;
  }

  public ActionableGene cancerType(String cancerType) {
    this.cancerType = cancerType;
    return this;
  }

   /**
   * Get cancerType
   * @return cancerType
  **/
  @ApiModelProperty(value = "")
  public String getCancerType() {
    return cancerType;
  }

  public void setCancerType(String cancerType) {
    this.cancerType = cancerType;
  }

  public ActionableGene drugs(String drugs) {
    this.drugs = drugs;
    return this;
  }

   /**
   * Get drugs
   * @return drugs
  **/
  @ApiModelProperty(value = "")
  public String getDrugs() {
    return drugs;
  }

  public void setDrugs(String drugs) {
    this.drugs = drugs;
  }

  public ActionableGene entrezGeneId(Integer entrezGeneId) {
    this.entrezGeneId = entrezGeneId;
    return this;
  }

   /**
   * Get entrezGeneId
   * @return entrezGeneId
  **/
  @ApiModelProperty(value = "")
  public Integer getEntrezGeneId() {
    return entrezGeneId;
  }

  public void setEntrezGeneId(Integer entrezGeneId) {
    this.entrezGeneId = entrezGeneId;
  }

  public ActionableGene gene(String gene) {
    this.gene = gene;
    return this;
  }

   /**
   * Get gene
   * @return gene
  **/
  @ApiModelProperty(value = "")
  public String getGene() {
    return gene;
  }

  public void setGene(String gene) {
    this.gene = gene;
  }

  public ActionableGene isoform(String isoform) {
    this.isoform = isoform;
    return this;
  }

   /**
   * Get isoform
   * @return isoform
  **/
  @ApiModelProperty(value = "")
  public String getIsoform() {
    return isoform;
  }

  public void setIsoform(String isoform) {
    this.isoform = isoform;
  }

  public ActionableGene level(String level) {
    this.level = level;
    return this;
  }

   /**
   * Get level
   * @return level
  **/
  @ApiModelProperty(value = "")
  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public ActionableGene pmids(String pmids) {
    this.pmids = pmids;
    return this;
  }

   /**
   * Get pmids
   * @return pmids
  **/
  @ApiModelProperty(value = "")
  public String getPmids() {
    return pmids;
  }

  public void setPmids(String pmids) {
    this.pmids = pmids;
  }

  public ActionableGene proteinChange(String proteinChange) {
    this.proteinChange = proteinChange;
    return this;
  }

   /**
   * Get proteinChange
   * @return proteinChange
  **/
  @ApiModelProperty(value = "")
  public String getProteinChange() {
    return proteinChange;
  }

  public void setProteinChange(String proteinChange) {
    this.proteinChange = proteinChange;
  }

  public ActionableGene refSeq(String refSeq) {
    this.refSeq = refSeq;
    return this;
  }

   /**
   * Get refSeq
   * @return refSeq
  **/
  @ApiModelProperty(value = "")
  public String getRefSeq() {
    return refSeq;
  }

  public void setRefSeq(String refSeq) {
    this.refSeq = refSeq;
  }

  public ActionableGene variant(String variant) {
    this.variant = variant;
    return this;
  }

   /**
   * Get variant
   * @return variant
  **/
  @ApiModelProperty(value = "")
  public String getVariant() {
    return variant;
  }

  public void setVariant(String variant) {
    this.variant = variant;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActionableGene actionableGene = (ActionableGene) o;
    return Objects.equals(this.abstracts, actionableGene.abstracts) &&
        Objects.equals(this.cancerType, actionableGene.cancerType) &&
        Objects.equals(this.drugs, actionableGene.drugs) &&
        Objects.equals(this.entrezGeneId, actionableGene.entrezGeneId) &&
        Objects.equals(this.gene, actionableGene.gene) &&
        Objects.equals(this.isoform, actionableGene.isoform) &&
        Objects.equals(this.level, actionableGene.level) &&
        Objects.equals(this.pmids, actionableGene.pmids) &&
        Objects.equals(this.proteinChange, actionableGene.proteinChange) &&
        Objects.equals(this.refSeq, actionableGene.refSeq) &&
        Objects.equals(this.variant, actionableGene.variant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(abstracts, cancerType, drugs, entrezGeneId, gene, isoform, level, pmids, proteinChange, refSeq, variant);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActionableGene {\n");
    
    sb.append("    abstracts: ").append(toIndentedString(abstracts)).append("\n");
    sb.append("    cancerType: ").append(toIndentedString(cancerType)).append("\n");
    sb.append("    drugs: ").append(toIndentedString(drugs)).append("\n");
    sb.append("    entrezGeneId: ").append(toIndentedString(entrezGeneId)).append("\n");
    sb.append("    gene: ").append(toIndentedString(gene)).append("\n");
    sb.append("    isoform: ").append(toIndentedString(isoform)).append("\n");
    sb.append("    level: ").append(toIndentedString(level)).append("\n");
    sb.append("    pmids: ").append(toIndentedString(pmids)).append("\n");
    sb.append("    proteinChange: ").append(toIndentedString(proteinChange)).append("\n");
    sb.append("    refSeq: ").append(toIndentedString(refSeq)).append("\n");
    sb.append("    variant: ").append(toIndentedString(variant)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

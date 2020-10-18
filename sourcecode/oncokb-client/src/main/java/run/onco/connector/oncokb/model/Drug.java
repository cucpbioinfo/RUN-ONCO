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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * Drug
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-10-03T12:49:32.816Z")
public class Drug {
  @SerializedName("atcCodes")
  private List<String> atcCodes = null;

  @SerializedName("drugName")
  private String drugName = null;

  @SerializedName("synonyms")
  private List<String> synonyms = null;

  public Drug atcCodes(List<String> atcCodes) {
    this.atcCodes = atcCodes;
    return this;
  }

  public Drug addAtcCodesItem(String atcCodesItem) {
    if (this.atcCodes == null) {
      this.atcCodes = new ArrayList<String>();
    }
    this.atcCodes.add(atcCodesItem);
    return this;
  }

   /**
   * Get atcCodes
   * @return atcCodes
  **/
  @ApiModelProperty(value = "")
  public List<String> getAtcCodes() {
    return atcCodes;
  }

  public void setAtcCodes(List<String> atcCodes) {
    this.atcCodes = atcCodes;
  }

  public Drug drugName(String drugName) {
    this.drugName = drugName;
    return this;
  }

   /**
   * Get drugName
   * @return drugName
  **/
  @ApiModelProperty(value = "")
  public String getDrugName() {
    return drugName;
  }

  public void setDrugName(String drugName) {
    this.drugName = drugName;
  }

  public Drug synonyms(List<String> synonyms) {
    this.synonyms = synonyms;
    return this;
  }

  public Drug addSynonymsItem(String synonymsItem) {
    if (this.synonyms == null) {
      this.synonyms = new ArrayList<String>();
    }
    this.synonyms.add(synonymsItem);
    return this;
  }

   /**
   * Get synonyms
   * @return synonyms
  **/
  @ApiModelProperty(value = "")
  public List<String> getSynonyms() {
    return synonyms;
  }

  public void setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Drug drug = (Drug) o;
    return Objects.equals(this.atcCodes, drug.atcCodes) &&
        Objects.equals(this.drugName, drug.drugName) &&
        Objects.equals(this.synonyms, drug.synonyms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(atcCodes, drugName, synonyms);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Drug {\n");
    
    sb.append("    atcCodes: ").append(toIndentedString(atcCodes)).append("\n");
    sb.append("    drugName: ").append(toIndentedString(drugName)).append("\n");
    sb.append("    synonyms: ").append(toIndentedString(synonyms)).append("\n");
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

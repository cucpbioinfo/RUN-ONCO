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
 * VariantConsequence
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-10-03T12:49:32.816Z")
public class VariantConsequence {
  @SerializedName("description")
  private String description = null;

  @SerializedName("isGenerallyTruncating")
  private Boolean isGenerallyTruncating = null;

  @SerializedName("term")
  private String term = null;

  public VariantConsequence description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public VariantConsequence isGenerallyTruncating(Boolean isGenerallyTruncating) {
    this.isGenerallyTruncating = isGenerallyTruncating;
    return this;
  }

   /**
   * Get isGenerallyTruncating
   * @return isGenerallyTruncating
  **/
  @ApiModelProperty(value = "")
  public Boolean isIsGenerallyTruncating() {
    return isGenerallyTruncating;
  }

  public void setIsGenerallyTruncating(Boolean isGenerallyTruncating) {
    this.isGenerallyTruncating = isGenerallyTruncating;
  }

  public VariantConsequence term(String term) {
    this.term = term;
    return this;
  }

   /**
   * Get term
   * @return term
  **/
  @ApiModelProperty(value = "")
  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VariantConsequence variantConsequence = (VariantConsequence) o;
    return Objects.equals(this.description, variantConsequence.description) &&
        Objects.equals(this.isGenerallyTruncating, variantConsequence.isGenerallyTruncating) &&
        Objects.equals(this.term, variantConsequence.term);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, isGenerallyTruncating, term);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VariantConsequence {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isGenerallyTruncating: ").append(toIndentedString(isGenerallyTruncating)).append("\n");
    sb.append("    term: ").append(toIndentedString(term)).append("\n");
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


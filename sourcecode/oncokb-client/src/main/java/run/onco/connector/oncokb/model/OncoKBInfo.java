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
 * OncoKBInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-10-03T12:49:32.816Z")
public class OncoKBInfo {
  @SerializedName("dataVersion")
  private Version dataVersion = null;

  @SerializedName("oncoTreeVersion")
  private String oncoTreeVersion = null;

  public OncoKBInfo dataVersion(Version dataVersion) {
    this.dataVersion = dataVersion;
    return this;
  }

   /**
   * Get dataVersion
   * @return dataVersion
  **/
  @ApiModelProperty(value = "")
  public Version getDataVersion() {
    return dataVersion;
  }

  public void setDataVersion(Version dataVersion) {
    this.dataVersion = dataVersion;
  }

  public OncoKBInfo oncoTreeVersion(String oncoTreeVersion) {
    this.oncoTreeVersion = oncoTreeVersion;
    return this;
  }

   /**
   * Get oncoTreeVersion
   * @return oncoTreeVersion
  **/
  @ApiModelProperty(value = "")
  public String getOncoTreeVersion() {
    return oncoTreeVersion;
  }

  public void setOncoTreeVersion(String oncoTreeVersion) {
    this.oncoTreeVersion = oncoTreeVersion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OncoKBInfo oncoKBInfo = (OncoKBInfo) o;
    return Objects.equals(this.dataVersion, oncoKBInfo.dataVersion) &&
        Objects.equals(this.oncoTreeVersion, oncoKBInfo.oncoTreeVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataVersion, oncoTreeVersion);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OncoKBInfo {\n");
    
    sb.append("    dataVersion: ").append(toIndentedString(dataVersion)).append("\n");
    sb.append("    oncoTreeVersion: ").append(toIndentedString(oncoTreeVersion)).append("\n");
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


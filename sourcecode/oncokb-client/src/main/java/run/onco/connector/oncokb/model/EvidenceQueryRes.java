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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import io.swagger.annotations.ApiModelProperty;

/**
 * EvidenceQueryRes
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-10-03T12:49:32.816Z")
public class EvidenceQueryRes {
  @SerializedName("alleles")
  private List<Alteration> alleles = null;

  @SerializedName("alterations")
  private List<Alteration> alterations = null;

  @SerializedName("evidences")
  private List<Evidence> evidences = null;

  @SerializedName("gene")
  private Gene gene = null;

  @SerializedName("id")
  private String id = null;

  /**
   * Gets or Sets levelOfEvidences
   */
  @JsonAdapter(LevelOfEvidencesEnum.Adapter.class)
  public enum LevelOfEvidencesEnum {
    _0("LEVEL_0"),
    
    _1("LEVEL_1"),
    
    _2A("LEVEL_2A"),
    
    _2B("LEVEL_2B"),
    
    _3("LEVEL_3"),
    
    _3A("LEVEL_3A"),
    
    _3B("LEVEL_3B"),
    
    _4("LEVEL_4"),
    
    R1("LEVEL_R1"),
    
    R2("LEVEL_R2"),
    
    R3("LEVEL_R3"),
    
    PX1("LEVEL_Px1"),
    
    PX2("LEVEL_Px2"),
    
    PX3("LEVEL_Px3"),
    
    PX4("LEVEL_Px4"),
    
    DX1("LEVEL_Dx1"),
    
    DX2("LEVEL_Dx2"),
    
    DX3("LEVEL_Dx3");

    private String value;

    LevelOfEvidencesEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static LevelOfEvidencesEnum fromValue(String text) {
      for (LevelOfEvidencesEnum b : LevelOfEvidencesEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<LevelOfEvidencesEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final LevelOfEvidencesEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public LevelOfEvidencesEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return LevelOfEvidencesEnum.fromValue(String.valueOf(value));
      }
    }
  }

  @SerializedName("levelOfEvidences")
  private List<LevelOfEvidencesEnum> levelOfEvidences = null;

  @SerializedName("oncoTreeTypes")
  private List<TumorType> oncoTreeTypes = null;

  @SerializedName("query")
  private Query query = null;

  public EvidenceQueryRes alleles(List<Alteration> alleles) {
    this.alleles = alleles;
    return this;
  }

  public EvidenceQueryRes addAllelesItem(Alteration allelesItem) {
    if (this.alleles == null) {
      this.alleles = new ArrayList<Alteration>();
    }
    this.alleles.add(allelesItem);
    return this;
  }

   /**
   * Get alleles
   * @return alleles
  **/
  @ApiModelProperty(value = "")
  public List<Alteration> getAlleles() {
    return alleles;
  }

  public void setAlleles(List<Alteration> alleles) {
    this.alleles = alleles;
  }

  public EvidenceQueryRes alterations(List<Alteration> alterations) {
    this.alterations = alterations;
    return this;
  }

  public EvidenceQueryRes addAlterationsItem(Alteration alterationsItem) {
    if (this.alterations == null) {
      this.alterations = new ArrayList<Alteration>();
    }
    this.alterations.add(alterationsItem);
    return this;
  }

   /**
   * Get alterations
   * @return alterations
  **/
  @ApiModelProperty(value = "")
  public List<Alteration> getAlterations() {
    return alterations;
  }

  public void setAlterations(List<Alteration> alterations) {
    this.alterations = alterations;
  }

  public EvidenceQueryRes evidences(List<Evidence> evidences) {
    this.evidences = evidences;
    return this;
  }

  public EvidenceQueryRes addEvidencesItem(Evidence evidencesItem) {
    if (this.evidences == null) {
      this.evidences = new ArrayList<Evidence>();
    }
    this.evidences.add(evidencesItem);
    return this;
  }

   /**
   * Get evidences
   * @return evidences
  **/
  @ApiModelProperty(value = "")
  public List<Evidence> getEvidences() {
    return evidences;
  }

  public void setEvidences(List<Evidence> evidences) {
    this.evidences = evidences;
  }

  public EvidenceQueryRes gene(Gene gene) {
    this.gene = gene;
    return this;
  }

   /**
   * Get gene
   * @return gene
  **/
  @ApiModelProperty(value = "")
  public Gene getGene() {
    return gene;
  }

  public void setGene(Gene gene) {
    this.gene = gene;
  }

  public EvidenceQueryRes id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public EvidenceQueryRes levelOfEvidences(List<LevelOfEvidencesEnum> levelOfEvidences) {
    this.levelOfEvidences = levelOfEvidences;
    return this;
  }

  public EvidenceQueryRes addLevelOfEvidencesItem(LevelOfEvidencesEnum levelOfEvidencesItem) {
    if (this.levelOfEvidences == null) {
      this.levelOfEvidences = new ArrayList<LevelOfEvidencesEnum>();
    }
    this.levelOfEvidences.add(levelOfEvidencesItem);
    return this;
  }

   /**
   * Get levelOfEvidences
   * @return levelOfEvidences
  **/
  @ApiModelProperty(value = "")
  public List<LevelOfEvidencesEnum> getLevelOfEvidences() {
    return levelOfEvidences;
  }

  public void setLevelOfEvidences(List<LevelOfEvidencesEnum> levelOfEvidences) {
    this.levelOfEvidences = levelOfEvidences;
  }

  public EvidenceQueryRes oncoTreeTypes(List<TumorType> oncoTreeTypes) {
    this.oncoTreeTypes = oncoTreeTypes;
    return this;
  }

  public EvidenceQueryRes addOncoTreeTypesItem(TumorType oncoTreeTypesItem) {
    if (this.oncoTreeTypes == null) {
      this.oncoTreeTypes = new ArrayList<TumorType>();
    }
    this.oncoTreeTypes.add(oncoTreeTypesItem);
    return this;
  }

   /**
   * Get oncoTreeTypes
   * @return oncoTreeTypes
  **/
  @ApiModelProperty(value = "")
  public List<TumorType> getOncoTreeTypes() {
    return oncoTreeTypes;
  }

  public void setOncoTreeTypes(List<TumorType> oncoTreeTypes) {
    this.oncoTreeTypes = oncoTreeTypes;
  }

  public EvidenceQueryRes query(Query query) {
    this.query = query;
    return this;
  }

   /**
   * Get query
   * @return query
  **/
  @ApiModelProperty(value = "")
  public Query getQuery() {
    return query;
  }

  public void setQuery(Query query) {
    this.query = query;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EvidenceQueryRes evidenceQueryRes = (EvidenceQueryRes) o;
    return Objects.equals(this.alleles, evidenceQueryRes.alleles) &&
        Objects.equals(this.alterations, evidenceQueryRes.alterations) &&
        Objects.equals(this.evidences, evidenceQueryRes.evidences) &&
        Objects.equals(this.gene, evidenceQueryRes.gene) &&
        Objects.equals(this.id, evidenceQueryRes.id) &&
        Objects.equals(this.levelOfEvidences, evidenceQueryRes.levelOfEvidences) &&
        Objects.equals(this.oncoTreeTypes, evidenceQueryRes.oncoTreeTypes) &&
        Objects.equals(this.query, evidenceQueryRes.query);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alleles, alterations, evidences, gene, id, levelOfEvidences, oncoTreeTypes, query);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EvidenceQueryRes {\n");
    
    sb.append("    alleles: ").append(toIndentedString(alleles)).append("\n");
    sb.append("    alterations: ").append(toIndentedString(alterations)).append("\n");
    sb.append("    evidences: ").append(toIndentedString(evidences)).append("\n");
    sb.append("    gene: ").append(toIndentedString(gene)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    levelOfEvidences: ").append(toIndentedString(levelOfEvidences)).append("\n");
    sb.append("    oncoTreeTypes: ").append(toIndentedString(oncoTreeTypes)).append("\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
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

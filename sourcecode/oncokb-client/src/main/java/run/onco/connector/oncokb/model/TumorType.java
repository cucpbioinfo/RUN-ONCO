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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * TumorType
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-10-03T12:49:32.816Z")
public class TumorType {
  @SerializedName("NCI")
  private List<String> NCI = null;

  @SerializedName("UMLS")
  private List<String> UMLS = null;

  @SerializedName("children")
  private Map<String, TumorType> children = null;

  @SerializedName("code")
  private String code = null;

  @SerializedName("color")
  private String color = null;

  @SerializedName("deprecated")
  private Boolean deprecated = null;

  @SerializedName("history")
  private List<String> history = null;

  @SerializedName("id")
  private Integer id = null;

  @SerializedName("level")
  private Integer level = null;

  @SerializedName("links")
  private List<Link> links = null;

  @SerializedName("mainType")
  private MainType mainType = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("parent")
  private String parent = null;

  @SerializedName("tissue")
  private String tissue = null;

  public TumorType NCI(List<String> NCI) {
    this.NCI = NCI;
    return this;
  }

  public TumorType addNCIItem(String NCIItem) {
    if (this.NCI == null) {
      this.NCI = new ArrayList<String>();
    }
    this.NCI.add(NCIItem);
    return this;
  }

   /**
   * NCI Thesaurus Code(s).
   * @return NCI
  **/
  @ApiModelProperty(value = "NCI Thesaurus Code(s).")
  public List<String> getNCI() {
    return NCI;
  }

  public void setNCI(List<String> NCI) {
    this.NCI = NCI;
  }

  public TumorType UMLS(List<String> UMLS) {
    this.UMLS = UMLS;
    return this;
  }

  public TumorType addUMLSItem(String UMLSItem) {
    if (this.UMLS == null) {
      this.UMLS = new ArrayList<String>();
    }
    this.UMLS.add(UMLSItem);
    return this;
  }

   /**
   * Concept Unique Identifier(s).
   * @return UMLS
  **/
  @ApiModelProperty(value = "Concept Unique Identifier(s).")
  public List<String> getUMLS() {
    return UMLS;
  }

  public void setUMLS(List<String> UMLS) {
    this.UMLS = UMLS;
  }

  public TumorType children(Map<String, TumorType> children) {
    this.children = children;
    return this;
  }

  public TumorType putChildrenItem(String key, TumorType childrenItem) {
    if (this.children == null) {
      this.children = new HashMap<String, TumorType>();
    }
    this.children.put(key, childrenItem);
    return this;
  }

   /**
   * List of all available children tumor types.
   * @return children
  **/
  @ApiModelProperty(value = "List of all available children tumor types.")
  public Map<String, TumorType> getChildren() {
    return children;
  }

  public void setChildren(Map<String, TumorType> children) {
    this.children = children;
  }

  public TumorType code(String code) {
    this.code = code;
    return this;
  }

   /**
   * Unique identifier representing OncoTree tumor types.
   * @return code
  **/
  @ApiModelProperty(value = "Unique identifier representing OncoTree tumor types.")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public TumorType color(String color) {
    this.color = color;
    return this;
  }

   /**
   * Tumor type color.
   * @return color
  **/
  @ApiModelProperty(value = "Tumor type color.")
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public TumorType deprecated(Boolean deprecated) {
    this.deprecated = deprecated;
    return this;
  }

   /**
   * The indicater whether this code has been deprecated.
   * @return deprecated
  **/
  @ApiModelProperty(example = "false", value = "The indicater whether this code has been deprecated.")
  public Boolean isDeprecated() {
    return deprecated;
  }

  public void setDeprecated(Boolean deprecated) {
    this.deprecated = deprecated;
  }

  public TumorType history(List<String> history) {
    this.history = history;
    return this;
  }

  public TumorType addHistoryItem(String historyItem) {
    if (this.history == null) {
      this.history = new ArrayList<String>();
    }
    this.history.add(historyItem);
    return this;
  }

   /**
   * Get history
   * @return history
  **/
  @ApiModelProperty(value = "")
  public List<String> getHistory() {
    return history;
  }

  public void setHistory(List<String> history) {
    this.history = history;
  }

  public TumorType id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * the numarical identifier of tumor type.
   * @return id
  **/
  @ApiModelProperty(value = "the numarical identifier of tumor type.")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public TumorType level(Integer level) {
    this.level = level;
    return this;
  }

   /**
   * -1
   * @return level
  **/
  @ApiModelProperty(value = "-1")
  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public TumorType links(List<Link> links) {
    this.links = links;
    return this;
  }

  public TumorType addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<Link>();
    }
    this.links.add(linksItem);
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @ApiModelProperty(value = "")
  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public TumorType mainType(MainType mainType) {
    this.mainType = mainType;
    return this;
  }

   /**
   * Get mainType
   * @return mainType
  **/
  @ApiModelProperty(value = "")
  public MainType getMainType() {
    return mainType;
  }

  public void setMainType(MainType mainType) {
    this.mainType = mainType;
  }

  public TumorType name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Tumor type name.
   * @return name
  **/
  @ApiModelProperty(value = "Tumor type name.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TumorType parent(String parent) {
    this.parent = parent;
    return this;
  }

   /**
   * The parent node code.
   * @return parent
  **/
  @ApiModelProperty(value = "The parent node code.")
  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public TumorType tissue(String tissue) {
    this.tissue = tissue;
    return this;
  }

   /**
   * The tissue this tumor type belongs to.
   * @return tissue
  **/
  @ApiModelProperty(value = "The tissue this tumor type belongs to.")
  public String getTissue() {
    return tissue;
  }

  public void setTissue(String tissue) {
    this.tissue = tissue;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TumorType tumorType = (TumorType) o;
    return Objects.equals(this.NCI, tumorType.NCI) &&
        Objects.equals(this.UMLS, tumorType.UMLS) &&
        Objects.equals(this.children, tumorType.children) &&
        Objects.equals(this.code, tumorType.code) &&
        Objects.equals(this.color, tumorType.color) &&
        Objects.equals(this.deprecated, tumorType.deprecated) &&
        Objects.equals(this.history, tumorType.history) &&
        Objects.equals(this.id, tumorType.id) &&
        Objects.equals(this.level, tumorType.level) &&
        Objects.equals(this.links, tumorType.links) &&
        Objects.equals(this.mainType, tumorType.mainType) &&
        Objects.equals(this.name, tumorType.name) &&
        Objects.equals(this.parent, tumorType.parent) &&
        Objects.equals(this.tissue, tumorType.tissue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(NCI, UMLS, children, code, color, deprecated, history, id, level, links, mainType, name, parent, tissue);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TumorType {\n");
    
    sb.append("    NCI: ").append(toIndentedString(NCI)).append("\n");
    sb.append("    UMLS: ").append(toIndentedString(UMLS)).append("\n");
    sb.append("    children: ").append(toIndentedString(children)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    deprecated: ").append(toIndentedString(deprecated)).append("\n");
    sb.append("    history: ").append(toIndentedString(history)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    level: ").append(toIndentedString(level)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    mainType: ").append(toIndentedString(mainType)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    tissue: ").append(toIndentedString(tissue)).append("\n");
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

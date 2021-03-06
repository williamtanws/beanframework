package com.beanframework.dynamicfield.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldConstants;
import com.beanframework.dynamicfield.DynamicFieldType;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldConstants.Table.DYNAMIC_FIELD)
public class DynamicField extends GenericEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 4733112810509713059L;
  public static final String NAME = "name";
  public static final String TYPE = "type";
  public static final String SORT = "sort";
  public static final String REQUIRED = "required";
  public static final String RULE = "rule";
  public static final String LABEL = "label";
  public static final String GRID = "grid";
  public static final String LANGUAGE = "language";
  public static final String ENUMERATIONS = "enumerations";

  public DynamicField() {
    super();
  }

  public DynamicField(UUID uuid, String id, String name, DynamicFieldType type) {
    super();
    setUuid(uuid);
    setId(id);
    setName(name);
    this.type = type;
  }

  @Audited(withModifiedFlag = true)
  private String name;

  @Audited(withModifiedFlag = true)
  @NotNull
  @Enumerated(EnumType.STRING)
  private DynamicFieldType type;

  @Audited(withModifiedFlag = true)
  private Boolean required;

  @Audited(withModifiedFlag = true)
  private String rule;

  @Audited(withModifiedFlag = true)
  private String label;

  @Audited(withModifiedFlag = true)
  private String grid;

  @Audited(withModifiedFlag = true)
  @Column(name = "language_uuid", columnDefinition = "BINARY(16)")
  private UUID language;

  @Audited(withModifiedFlag = true)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = DynamicFieldConstants.Table.DYNAMIC_FIELD_ENUMERATION_REL,
      joinColumns = @JoinColumn(name = "dynamicfield_uuid"))
  @Column(name = "enumeration_uuid", columnDefinition = "BINARY(16)", nullable = false)
  private Set<UUID> enumerations = new HashSet<UUID>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DynamicFieldType getType() {
    return type;
  }

  public void setType(DynamicFieldType type) {
    this.type = type;
  }

  public Boolean getRequired() {
    return required;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getGrid() {
    return grid;
  }

  public void setGrid(String grid) {
    this.grid = grid;
  }

  public UUID getLanguage() {
    return language;
  }

  public void setLanguage(UUID language) {
    this.language = language;
  }

  public Set<UUID> getEnumerations() {
    return enumerations;
  }

  public void setEnumerations(Set<UUID> enumerations) {
    this.enumerations = enumerations;
  }
}

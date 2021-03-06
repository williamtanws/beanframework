package com.beanframework.dynamicfield.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.dynamicfield.DynamicFieldTemplateConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = DynamicFieldTemplateConstants.Table.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
public class DynamicFieldTemplate extends GenericEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 250602072404640389L;

  public static final String NAME = "name";
  public static final String DYNAMIC_FIELD_SLOTS = "dynamicFieldSlots";

  @Audited(withModifiedFlag = true)
  private String name;

  @Audited(withModifiedFlag = true)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = DynamicFieldTemplateConstants.Table.DYNAMIC_FIELD_TEMPLATE_FIELDSLOT_REL,
      joinColumns = @JoinColumn(name = "template_uuid"))
  @Column(name = "dynamicfieldslot_uuid", columnDefinition = "BINARY(16)", nullable = false)
  private Set<UUID> dynamicFieldSlots = new HashSet<UUID>();

  public DynamicFieldTemplate() {
    super();
  }

  public DynamicFieldTemplate(UUID uuid, String id, String name) {
    super();
    setUuid(uuid);
    setId(id);
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<UUID> getDynamicFieldSlots() {
    return dynamicFieldSlots;
  }

  public void setDynamicFieldSlots(Set<UUID> dynamicFieldSlots) {
    this.dynamicFieldSlots = dynamicFieldSlots;
  }

}

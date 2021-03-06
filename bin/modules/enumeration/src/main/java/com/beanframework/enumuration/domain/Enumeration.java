package com.beanframework.enumuration.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.enumuration.EnumerationConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = EnumerationConstants.Table.ENUMURATION)
public class Enumeration extends GenericEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 6419266217361908724L;
  public static final String ENUMERATION = "enumeration";
  public static final String NAME = "name";
  public static final String SORT = "sort";

  public Enumeration() {
    super();
  }

  public Enumeration(UUID uuid, String id, String name, Integer sort) {
    super();
    setUuid(uuid);
    setId(id);
    setName(name);
    this.sort = sort;
  }

  @Audited(withModifiedFlag = true)
  private String name;

  @Audited(withModifiedFlag = true)
  private Integer sort;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }
}

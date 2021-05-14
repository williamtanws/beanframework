package com.beanframework.internationalization.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.internationalization.LanguageConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = LanguageConstants.Table.LANGUAGE)
public class Language extends GenericEntity {

  private static final long serialVersionUID = 5992760081038782486L;
  public static final String NAME = "name";
  public static final String ACTIVE = "active";
  public static final String SORT = "sort";

  public Language() {
    super();
  }

  public Language(UUID uuid, String id, String name, Integer sort) {
    super();
    setUuid(uuid);
    setId(id);
    setName(name);
    setSort(sort);
  }

  @Audited(withModifiedFlag = true)
  private String name;

  @Audited(withModifiedFlag = true)
  private Boolean active;

  @Audited(withModifiedFlag = true)
  private Integer sort;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

}

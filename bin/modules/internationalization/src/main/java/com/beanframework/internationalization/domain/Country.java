package com.beanframework.internationalization.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.internationalization.CountryConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CountryConstants.Table.COUNTRY)
public class Country extends GenericEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 7791727441562748178L;

  public static final String NAME = "name";
  public static final String ACTIVE = "active";

  public Country() {
    super();
  }

  public Country(UUID uuid, String id, String name, Boolean active) {
    super();
    setUuid(uuid);
    setId(id);
    setName(name);
    setActive(active);
  }

  @Audited(withModifiedFlag = true)
  private String name;

  @Audited(withModifiedFlag = true)
  private Boolean active;

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

}

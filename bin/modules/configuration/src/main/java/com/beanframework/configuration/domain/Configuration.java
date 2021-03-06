package com.beanframework.configuration.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.configuration.ConfigurationConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = ConfigurationConstants.Table.CONFIGURATION)
public class Configuration extends GenericEntity {

  private static final long serialVersionUID = 2129119893141952037L;
  public static final String VALUE = "value";

  public Configuration() {
    super();
  }

  public Configuration(UUID uuid, String id, String value) {
    super();
    setUuid(uuid);
    setId(id);
    setValue(value);
  }

  @Audited(withModifiedFlag = true)
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}

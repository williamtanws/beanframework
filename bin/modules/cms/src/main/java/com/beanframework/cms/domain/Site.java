package com.beanframework.cms.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.cms.SiteConstants;
import com.beanframework.common.domain.GenericEntity;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = SiteConstants.Table.SITE)
public class Site extends GenericEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -5520792330246884683L;
  public static final String NAME = "name";
  public static final String URL = "url";

  public Site() {
    super();
  }

  public Site(UUID uuid, String id, String name) {
    super();
    setUuid(uuid);
    setId(id);
    setName(name);
  }

  @Audited(withModifiedFlag = true)
  private String name;

  @Audited(withModifiedFlag = true)
  private String url;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}

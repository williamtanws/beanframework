package com.beanframework.logentry.domain;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.logentry.LogentryConstants;
import com.beanframework.logentry.LogentryType;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = LogentryConstants.Table.LOGENTRY)
public class Logentry extends GenericEntity {
  /**
   * 
   */
  private static final long serialVersionUID = 915220059297875865L;
  public static final String TYPE = "type";
  public static final String MESSAGE = "message";

  public Logentry() {
    super();
  }

  public Logentry(UUID uuid, Date createdDate, LogentryType type, String message) {
    super();
    setUuid(uuid);
    setCreatedDate(createdDate);
    setType(type);
    setMessage(message);
  }

  private LogentryType type;
  private String message;

  public LogentryType getType() {
    return type;
  }

  public void setType(LogentryType type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

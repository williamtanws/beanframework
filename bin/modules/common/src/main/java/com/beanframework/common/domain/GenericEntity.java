package com.beanframework.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class GenericEntity implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 320464347425514935L;
  public static final String UUID = "uuid";
  public static final String ID = "id";
  public static final String CREATED_DATE = "createdDate";
  public static final String CREATED_BY = "createdBy";
  public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
  public static final String LAST_MODIFIED_BY = "lastModifiedBy";

  @Id
  @GeneratedValue(generator = "inquisitive-uuid2")
  @GenericGenerator(name = "inquisitive-uuid2",
      strategy = "com.beanframework.common.domain.InquisitiveUUIDGenerator")
  @Column(columnDefinition = "BINARY(16)", unique = true, updatable = false)
  private UUID uuid;

  @Audited(withModifiedFlag = true)
  @Column(unique = true)
  private String id;

  @CreatedDate
  @Column(updatable = false)
  private Date createdDate;

  @CreatedBy
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "createdby_uuid")
  private Auditor createdBy;

  @Audited
  private Date lastModifiedDate;

  @Audited(withModifiedFlag = true)
  @LastModifiedBy
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "lastmodifiedby_uuid")
  private Auditor lastModifiedBy;

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Auditor getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Auditor createdBy) {
    this.createdBy = createdBy;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public Auditor getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(Auditor lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  @Override
  public String toString() {
    return StringUtils.isBlank(id) ? "UUID: " + uuid : "ID: " + id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GenericEntity other = (GenericEntity) obj;
    if (uuid == null) {
      if (other.uuid != null)
        return false;
    } else if (!uuid.equals(other.uuid))
      return false;
    return true;
  }

}

package com.beanframework.user.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.user.UserConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = UserConstants.Table.USER_ATTRIBUTE)
public class UserAttribute extends GenericEntity {

  /**
   * 
   */
  private static final long serialVersionUID = -7666190244677961254L;
  public static final String USER = "user";
  public static final String DYNAMIC_FIELD_SLOT = "dynamicFieldSlot";
  public static final String VALUE = "value";

  @JsonIgnore
  @Audited(withModifiedFlag = true)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_uuid")
  private User user;

  @Audited(withModifiedFlag = true)
  @Column(name = "dynamicfieldslot_uuid", columnDefinition = "BINARY(16)")
  private UUID dynamicFieldSlot;

  @Audited(withModifiedFlag = true)
  private String value;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public UUID getDynamicFieldSlot() {
    return dynamicFieldSlot;
  }

  public void setDynamicFieldSlot(UUID dynamicFieldSlot) {
    this.dynamicFieldSlot = dynamicFieldSlot;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}

package com.beanframework.user.domain;

import java.util.UUID;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.user.CustomerConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@DiscriminatorValue(CustomerConstants.Discriminator.CUSTOMER)
public class Customer extends User {
  /**
   * 
   */
  private static final long serialVersionUID = -628677275018700297L;

  public Customer() {
    super();
  }

  public Customer(UUID uuid, String id, String name) {
    super();
    setUuid(uuid);
    setId(id);
    setName(name);
  }
}

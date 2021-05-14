package com.beanframework.internationalization.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.internationalization.CurrencyConstants;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CurrencyConstants.Table.CURRENCY)
public class Currency extends GenericEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 6640641390598966129L;

  public static final String NAME = "name";
  public static final String ACTIVE = "active";
  public static final String BASE = "base";
  public static final String CONVERTION = "conversion";
  public static final String DIGIT = "digit";
  public static final String SYMBOL = "symbol";

  public Currency() {
    super();
  }

  public Currency(UUID uuid, String id, String name, Boolean active) {
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

  @Audited(withModifiedFlag = true)
  private Boolean base;

  @Audited(withModifiedFlag = true)
  private Double conversion;

  @Audited(withModifiedFlag = true)
  private Integer digit;

  @Audited(withModifiedFlag = true)
  private String symbol;

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

  public Boolean getBase() {
    return base;
  }

  public void setBase(Boolean base) {
    this.base = base;
  }

  public Double getConversion() {
    return conversion;
  }

  public void setConversion(Double conversion) {
    this.conversion = conversion;
  }

  public Integer getDigit() {
    return digit;
  }

  public void setDigit(Integer digit) {
    this.digit = digit;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

}

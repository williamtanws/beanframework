package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class CountryDto extends GenericDto {

  /**
   * 
   */
  private static final long serialVersionUID = -7536881992925185254L;
  private String name;
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

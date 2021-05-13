package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class LanguageDto extends GenericDto {
  /**
   * 
   */
  private static final long serialVersionUID = 3610994073724879265L;

  private String name;
  private Boolean active;
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

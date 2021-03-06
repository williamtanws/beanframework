package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class UserGroupAttributeDto extends GenericDto {

  /**
   * 
   */
  private static final long serialVersionUID = -6527945805106422096L;

  private UserGroupDto userGroup;

  private DynamicFieldSlotDto dynamicFieldSlot;

  private String value;

  public UserGroupDto getUserGroup() {
    return userGroup;
  }

  public void setUserGroup(UserGroupDto userGroup) {
    this.userGroup = userGroup;
  }

  public DynamicFieldSlotDto getDynamicFieldSlot() {
    return dynamicFieldSlot;
  }

  public void setDynamicFieldSlot(DynamicFieldSlotDto dynamicFieldSlot) {
    this.dynamicFieldSlot = dynamicFieldSlot;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}

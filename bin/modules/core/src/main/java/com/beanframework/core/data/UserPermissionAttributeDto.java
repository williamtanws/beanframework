package com.beanframework.core.data;

import com.beanframework.common.data.GenericDto;

public class UserPermissionAttributeDto extends GenericDto {

  /**
   * 
   */
  private static final long serialVersionUID = -2920029811939870923L;

  private UserPermissionDto userPermission;

  private DynamicFieldSlotDto dynamicFieldSlot;

  private String value;

  public UserPermissionDto getUserPermission() {
    return userPermission;
  }

  public void setUserPermission(UserPermissionDto userPermission) {
    this.userPermission = userPermission;
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

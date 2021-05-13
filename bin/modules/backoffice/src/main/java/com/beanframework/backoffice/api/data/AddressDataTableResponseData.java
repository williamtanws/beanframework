package com.beanframework.backoffice.api.data;

import com.beanframework.core.data.DataTableResponseData;

public class AddressDataTableResponseData extends DataTableResponseData {

  private String streetName;

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }
}

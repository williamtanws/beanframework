package com.beanframework.user.service;

import java.util.HashMap;
import java.util.Map;
import com.beanframework.user.domain.User;

public class OverRiddenUser {
  private Map<String, User> userThreadMap = new HashMap<>();

  public Map<String, User> getUserThreadMap() {
    return userThreadMap;
  }

  public void setUserThreadMap(Map<String, User> userThreadMap) {
    this.userThreadMap = userThreadMap;
  }
}

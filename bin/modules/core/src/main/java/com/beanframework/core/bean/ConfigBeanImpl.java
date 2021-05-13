package com.beanframework.core.bean;

import org.springframework.beans.factory.annotation.Autowired;
import com.beanframework.core.facade.ConfigurationFacade;

public class ConfigBeanImpl implements ConfigBean {

  @Autowired
  private ConfigurationFacade configurationFacade;

  @Override
  public String get(String id) throws Exception {
    return get(id, null);
  }

  @Override
  public boolean is(String id) throws Exception {
    return is(id, false);
  }

  @Override
  public String get(String id, String defaultValue) throws Exception {
    return configurationFacade.get(id, defaultValue);
  }

  @Override
  public boolean is(String id, boolean defaultValue) throws Exception {
    return configurationFacade.is(id, defaultValue);
  }
}

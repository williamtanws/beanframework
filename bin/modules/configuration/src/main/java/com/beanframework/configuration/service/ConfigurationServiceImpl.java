package com.beanframework.configuration.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

  @Autowired
  private ModelService modelService;

  @Override
  public String get(String id) throws InterceptorException {
    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Configuration.ID, id);
    Configuration entity = modelService.findOneByProperties(properties, Configuration.class);
    if (entity == null) {
      return null;
    } else {
      return entity.getValue();
    }
  }

  @Override
  public String get(String id, String defaultValue) throws InterceptorException {
    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Configuration.ID, id);
    Configuration entity = modelService.findOneByProperties(properties, Configuration.class);
    if (entity == null) {
      return defaultValue;
    } else {
      return entity.getValue();
    }
  }
}

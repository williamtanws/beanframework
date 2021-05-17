package com.beanframework.core.converter.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.ConfigurationDto;

@Component
public class ConfigurationEntityConverter
    implements EntityConverter<ConfigurationDto, Configuration> {

  @Autowired
  private ModelService modelService;

  @Override
  public Configuration convert(ConfigurationDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Configuration prototype = modelService.findOneByUuid(source.getUuid(), Configuration.class);
        return convertToEntity(source, prototype);
      }

      return convertToEntity(source, modelService.create(Configuration.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

  }

  private Configuration convertToEntity(ConfigurationDto source, Configuration prototype) {

    if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
        prototype.getId()) == Boolean.FALSE) {
      prototype.setId(StringUtils.stripToNull(source.getId()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getValue()),
        prototype.getValue()) == Boolean.FALSE) {
      prototype.setValue(StringUtils.stripToNull(source.getValue()));
    }

    return prototype;
  }

}

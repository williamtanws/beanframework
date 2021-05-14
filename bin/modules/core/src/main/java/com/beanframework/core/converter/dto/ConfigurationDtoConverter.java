package com.beanframework.core.converter.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.data.ConfigurationDto;

public class ConfigurationDtoConverter extends AbstractDtoConverter<Configuration, ConfigurationDto>
    implements DtoConverter<Configuration, ConfigurationDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(ConfigurationDtoConverter.class);

  @Override
  public ConfigurationDto convert(Configuration source) throws ConverterException {
    return super.convert(source, new ConfigurationDto());
  }

}

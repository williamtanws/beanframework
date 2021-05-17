package com.beanframework.core.converter.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.LogentryDto;
import com.beanframework.logentry.domain.Logentry;

@Component
public class LogentryEntityConverter implements EntityConverter<LogentryDto, Logentry> {

  @Autowired
  private ModelService modelService;

  @Override
  public Logentry convert(LogentryDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Logentry prototype = modelService.findOneByUuid(source.getUuid(), Logentry.class);
        return convertToEntity(source, prototype);
      }

      return convertToEntity(source, modelService.create(Logentry.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Logentry convertToEntity(LogentryDto source, Logentry prototype) {

    if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
        prototype.getId()) == Boolean.FALSE) {
      prototype.setId(StringUtils.stripToNull(source.getId()));
    }

    if (prototype.getType() == source.getType() == Boolean.FALSE) {
      prototype.setType(source.getType());
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getMessage()),
        prototype.getMessage()) == Boolean.FALSE) {
      prototype.setMessage(StringUtils.stripToNull(source.getMessage()));
    }

    return prototype;
  }

}

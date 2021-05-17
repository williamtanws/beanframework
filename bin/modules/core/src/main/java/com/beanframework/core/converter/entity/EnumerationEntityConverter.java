package com.beanframework.core.converter.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.enumuration.domain.Enumeration;

@Component
public class EnumerationEntityConverter implements EntityConverter<EnumerationDto, Enumeration> {

  @Autowired
  private ModelService modelService;

  @Override
  public Enumeration convert(EnumerationDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Enumeration prototype = modelService.findOneByUuid(source.getUuid(), Enumeration.class);
        return convertToEntity(source, prototype);
      }

      return convertToEntity(source, modelService.create(Enumeration.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Enumeration convertToEntity(EnumerationDto source, Enumeration prototype) {

    if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
        prototype.getId()) == Boolean.FALSE) {
      prototype.setId(StringUtils.stripToNull(source.getId()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getName()),
        prototype.getName()) == Boolean.FALSE) {
      prototype.setName(StringUtils.stripToNull(source.getName()));
    }

    if (source.getSort() == null) {
      if (prototype.getSort() != null) {
        prototype.setSort(null);
      }
    } else {
      if (prototype.getSort() == null || prototype.getSort() == source.getSort() == Boolean.FALSE) {
        prototype.setSort(source.getSort());
      }
    }

    return prototype;
  }

}

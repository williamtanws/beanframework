package com.beanframework.core.converter.entity;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CountryDto;
import com.beanframework.internationalization.domain.Country;

@Component
public class CountryEntityConverter implements EntityConverter<CountryDto, Country> {

  @Autowired
  private ModelService modelService;

  @Override
  public Country convert(CountryDto source) throws ConverterException {

    try {
      if (source.getUuid() != null) {
        Country prototype = modelService.findOneByUuid(source.getUuid(), Country.class);

        if (prototype != null) {
          return convertToEntity(source, prototype);
        }
      }
      return convertToEntity(source, modelService.create(Country.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Country convertToEntity(CountryDto source, Country prototype) throws ConverterException {

    try {
      Date lastModifiedDate = new Date();

      if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
          prototype.getId()) == Boolean.FALSE) {
        prototype.setId(StringUtils.stripToNull(source.getId()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getName()),
          prototype.getName()) == Boolean.FALSE) {
        prototype.setName(StringUtils.stripToNull(source.getName()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (prototype.getActive() == source.getActive() == Boolean.FALSE) {
        prototype.setActive(source.getActive());
        prototype.setLastModifiedDate(lastModifiedDate);
      }
    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

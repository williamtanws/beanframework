package com.beanframework.core.converter.entity.csv;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.CountryCsv;
import com.beanframework.internationalization.domain.Country;

@Component
public class CountryCsvEntityConverter implements EntityCsvConverter<CountryCsv, Country> {

  protected static Logger LOGGER = LoggerFactory.getLogger(CountryCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public Country convert(CountryCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Country.ID, source.getId());

        Country prototype = modelService.findOneByProperties(properties, Country.class);

        if (prototype != null) {

          return convertToEntity(source, prototype);
        }
      }
      return convertToEntity(source, modelService.create(Country.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Country convertToEntity(CountryCsv source, Country prototype) throws ConverterException {

    try {
      Date lastModifiedDate = new Date();

      if (StringUtils.isNotBlank(source.getId())) {
        prototype.setId(source.getId());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.isNotBlank(source.getName())) {
        prototype.setName(source.getName());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (source.getActive() != null) {
        prototype.setActive(source.getActive());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

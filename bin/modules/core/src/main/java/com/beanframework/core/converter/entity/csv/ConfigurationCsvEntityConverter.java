package com.beanframework.core.converter.entity.csv;

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
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.core.csv.ConfigurationCsv;

@Component
public class ConfigurationCsvEntityConverter
    implements EntityCsvConverter<ConfigurationCsv, Configuration> {

  protected static Logger LOGGER = LoggerFactory.getLogger(ConfigurationCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public Configuration convert(ConfigurationCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Configuration.ID, source.getId());

        Configuration prototype = modelService.findOneByProperties(properties, Configuration.class);

        if (prototype != null) {

          return convertToEntity(source, prototype);
        }
      }
      return convertToEntity(source, modelService.create(Configuration.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Configuration convertToEntity(ConfigurationCsv source, Configuration prototype)
      throws ConverterException {

    try {
      if (StringUtils.isNotBlank(source.getId()))
        prototype.setId(source.getId());

      if (StringUtils.isNotBlank(source.getValue()))
        prototype.setValue(source.getValue());

    } catch (Exception e) {
      e.printStackTrace();
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

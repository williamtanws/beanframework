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
import com.beanframework.core.csv.LanguageCsv;
import com.beanframework.internationalization.domain.Language;

@Component
public class LanguageCsvEntityConverter implements EntityCsvConverter<LanguageCsv, Language> {

  protected static Logger LOGGER = LoggerFactory.getLogger(LanguageCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public Language convert(LanguageCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Language.ID, source.getId());

        Language prototype = modelService.findOneByProperties(properties, Language.class);

        if (prototype != null) {

          return convertToEntity(source, prototype);
        }
      }
      return convertToEntity(source, modelService.create(Language.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Language convertToEntity(LanguageCsv source, Language prototype)
      throws ConverterException {

    try {
      if (StringUtils.isNotBlank(source.getId()))
        prototype.setId(source.getId());

      if (StringUtils.isNotBlank(source.getName()))
        prototype.setName(source.getName());

      if (source.getActive() != null)
        prototype.setActive(source.getActive());

      if (source.getSort() != null)
        prototype.setSort(source.getSort());

    } catch (Exception e) {
      e.printStackTrace();
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

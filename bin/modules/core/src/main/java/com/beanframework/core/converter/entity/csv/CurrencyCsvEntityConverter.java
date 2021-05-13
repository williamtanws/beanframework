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
import com.beanframework.core.csv.CurrencyCsv;
import com.beanframework.internationalization.domain.Currency;

@Component
public class CurrencyCsvEntityConverter implements EntityCsvConverter<CurrencyCsv, Currency> {

  protected static Logger LOGGER = LoggerFactory.getLogger(CurrencyCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public Currency convert(CurrencyCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Currency.ID, source.getId());

        Currency prototype = modelService.findOneByProperties(properties, Currency.class);

        if (prototype != null) {

          return convertToEntity(source, prototype);
        }
      }
      return convertToEntity(source, modelService.create(Currency.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Currency convertToEntity(CurrencyCsv source, Currency prototype)
      throws ConverterException {

    try {
      Date lastModifiedDate = new Date();

      if (StringUtils.isNotBlank(source.getId())) {
        prototype.setId(source.getId());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.isNotBlank(source.getName())) {
        prototype.setName(StringUtils.stripToNull(source.getName()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (source.getActive() != null) {
        prototype.setActive(source.getActive());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (source.getBase() != null) {
        prototype.setBase(source.getBase());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (source.getConversion() != null) {
        prototype.setConversion(source.getConversion());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (source.getDigit() != null) {
        prototype.setDigit(source.getDigit());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.isNotBlank(source.getSymbol())) {
        prototype.setSymbol(source.getSymbol());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

package com.beanframework.core.converter.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.internationalization.domain.Currency;

@Component
public class CurrencyEntityConverter implements EntityConverter<CurrencyDto, Currency> {

  @Autowired
  private ModelService modelService;

  @Override
  public Currency convert(CurrencyDto source) throws ConverterException {

    try {
      if (source.getUuid() != null) {
        Currency prototype = modelService.findOneByUuid(source.getUuid(), Currency.class);
        return convertToEntity(source, prototype);
      }
      return convertToEntity(source, modelService.create(Currency.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Currency convertToEntity(CurrencyDto source, Currency prototype)
      throws ConverterException {

    try {

      if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
          prototype.getId()) == Boolean.FALSE) {
        prototype.setId(StringUtils.stripToNull(source.getId()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getName()),
          prototype.getName()) == Boolean.FALSE) {
        prototype.setName(StringUtils.stripToNull(source.getName()));
      }

      if (prototype.getActive() == source.getActive() == Boolean.FALSE) {
        prototype.setActive(source.getActive());
      }

      if (prototype.getBase() == source.getBase() == Boolean.FALSE) {
        prototype.setBase(source.getBase());
      }

      if (prototype.getConversion() == source.getConversion() == Boolean.FALSE) {
        prototype.setConversion(source.getConversion());
      }

      if (prototype.getDigit() == source.getDigit() == Boolean.FALSE) {
        prototype.setDigit(source.getDigit());
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getSymbol()),
          prototype.getSymbol()) == Boolean.FALSE) {
        prototype.setSymbol(StringUtils.stripToNull(source.getSymbol()));
      }

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

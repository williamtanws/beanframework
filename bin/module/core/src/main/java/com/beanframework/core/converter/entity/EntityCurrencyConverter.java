package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.internationalization.domain.Currency;

public class EntityCurrencyConverter implements EntityConverter<CurrencyDto, Currency> {

	@Autowired
	private ModelService modelService;

	@Override
	public Currency convert(CurrencyDto source, EntityConverterContext context) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Currency.UUID, source.getUuid());
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

	private Currency convertToEntity(CurrencyDto source, Currency prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getActive() == source.getActive() == Boolean.FALSE) {
				prototype.setActive(source.getActive());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getBase()), prototype.getBase()) == Boolean.FALSE) {
				prototype.setBase(StringUtils.stripToNull(source.getBase()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getConvertion() == source.getConvertion() == Boolean.FALSE) {
				prototype.setConvertion(source.getConvertion());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getDigit() == source.getDigit() == Boolean.FALSE) {
				prototype.setDigit(source.getDigit());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getSymbol()), prototype.getSymbol()) == Boolean.FALSE) {
				prototype.setSymbol(StringUtils.stripToNull(source.getSymbol()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
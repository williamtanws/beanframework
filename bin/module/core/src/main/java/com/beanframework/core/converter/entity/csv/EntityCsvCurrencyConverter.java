package com.beanframework.core.converter.entity.csv;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.CurrencyCsv;
import com.beanframework.internationalization.domain.Currency;

public class EntityCsvCurrencyConverter implements EntityCsvConverter<CurrencyCsv, Currency> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvCurrencyConverter.class);

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

	private Currency convertToEntity(CurrencyCsv source, Currency prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
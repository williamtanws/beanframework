package com.beanframework.core.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.EnumerationCsv;
import com.beanframework.enumuration.domain.Enumeration;


public class EntityCsvEnumerationConverter implements EntityCsvConverter<EnumerationCsv, Enumeration> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvEnumerationConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Enumeration convert(EnumerationCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Enumeration.ID, source.getId());

				Enumeration prototype = modelService.findOneByProperties(properties, Enumeration.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Enumeration.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Enumeration convertToEntity(EnumerationCsv source, Enumeration prototype) throws ConverterException {

		try {
			if (StringUtils.isNotBlank(source.getId()))
				prototype.setId(source.getId());

			if (StringUtils.isNotBlank(source.getName()))
				prototype.setName(source.getName());

			if (source.getSort() != null)
				prototype.setSort(source.getSort());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.EnumerationCsv;
import com.beanframework.enumuration.domain.Enumeration;

@Component
public class EntityCsvEnumerationConverter implements EntityConverter<EnumerationCsv, Enumeration> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvEnumerationConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Enumeration convert(EnumerationCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Enumeration.ID, source.getId());

				Enumeration prototype = modelService.findOneEntityByProperties(properties, Enumeration.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new Enumeration());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public Enumeration convert(EnumerationCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private Enumeration convert(EnumerationCsv source, Enumeration prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setSort(Integer.valueOf(source.getSort()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

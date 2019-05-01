package com.beanframework.console.converter;

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
import com.beanframework.console.csv.LanguageCsv;
import com.beanframework.language.domain.Language;

@Component
public class EntityCsvLanguageConverter implements EntityCsvConverter<LanguageCsv, Language> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvLanguageConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Language convert(LanguageCsv source) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.ID, source.getId());

				Language prototype = modelService.findByProperties(properties, Language.class);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Language.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Language convertToEntity(LanguageCsv source, Language prototype) throws ConverterException {

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

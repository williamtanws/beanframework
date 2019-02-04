package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.LanguageCsv;
import com.beanframework.language.domain.Language;

@Component
public class EntityCsvLanguageConverter implements EntityConverter<LanguageCsv, Language> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvLanguageConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Language convert(LanguageCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.ID, source.getId());

				Language prototype = modelService.findOneEntityByProperties(properties, true,Language.class);

				if (prototype != null) {

					return convert(source, prototype);
				}
			}
			return convert(source, new Language());

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Language convert(LanguageCsv source, Language prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setActive(source.isActive());
			prototype.setSort(Integer.valueOf(source.getSort()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

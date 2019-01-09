package com.beanframework.language.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;

public class EntityLanguageConverter implements EntityConverter<Language, Language> {

	@Autowired
	private ModelService modelService;

	@Override
	public Language convert(Language source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.UUID, source.getUuid());
				Language prototype = modelService.findOneEntityByProperties(properties, Language.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Language.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}
	}

	private Language convert(Language source, Language prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false) {
			prototype.setId(StringUtils.strip(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(prototype.getName(), source.getName()) == false) {
			prototype.setName(StringUtils.strip(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (prototype.getSort() == source.getSort() == false) {
			prototype.setSort(source.getSort());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (prototype.getActive() == source.getActive() == false) {
			prototype.setActive(source.getActive());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		return prototype;
	}

}

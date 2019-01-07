package com.beanframework.language.converter;

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

		Language prototype;
		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Language.UUID, source.getUuid());
				Language exists = modelService.findOneEntityByProperties(properties, Language.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Language.class);
				}
			} else {
				prototype = modelService.create(Language.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private Language convert(Language source, Language prototype) {

		
		
		if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
			prototype.setId(StringUtils.strip(source.getId()));

		if (StringUtils.equals(prototype.getName(), source.getName()) == false)
			prototype.setName(StringUtils.strip(source.getName()));

		if (prototype.getSort() == source.getSort() == false)
			prototype.setSort(source.getSort());

		if (prototype.getActive() == source.getActive() == false)
			prototype.setActive(source.getActive());

		return prototype;
	}

}

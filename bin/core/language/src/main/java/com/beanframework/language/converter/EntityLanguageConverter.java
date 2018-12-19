package com.beanframework.language.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;

@Component
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

		if (source.getId() != null)
			prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		if (source.getName() != null)
			prototype.setName(source.getName());
		if (source.getSort() != null)
			prototype.setSort(source.getSort());
		if (source.getActive() != null)
			prototype.setActive(source.getActive());

		return prototype;
	}

}

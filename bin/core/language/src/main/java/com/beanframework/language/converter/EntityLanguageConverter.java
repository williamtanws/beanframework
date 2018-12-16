package com.beanframework.language.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;

@Component
public class EntityLanguageConverter implements EntityConverter<Language, Language> {

	@Autowired
	private ModelService modelService;

	@Override
	public Language convert(Language source) {

		Language prototype = modelService.create(Language.class);
		if (source.getUuid() != null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Language.UUID, source.getUuid());
			Language exists = modelService.findOneEntityByProperties(properties, Language.class);
			
			if (exists != null) {
				prototype = exists;
			}
		}

		return convert(source, prototype);
	}

	private Language convert(Language source, Language prototype) {

		prototype.setLastModifiedDate(new Date());
		prototype.setId(source.getId());
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
		prototype.setActive(source.getActive());

		return prototype;
	}

}

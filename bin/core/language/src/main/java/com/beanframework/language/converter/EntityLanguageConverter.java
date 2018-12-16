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

		Language prototype = null;
		if (source.getUuid() != null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Language.UUID, source.getUuid());
			prototype = modelService.findOneEntityByProperties(properties, Language.class);
			
			if (prototype == null) {
				prototype = modelService.create(Language.class);
			}
		}
		else {
			prototype = modelService.create(Language.class);
		}

		return convert(source, prototype);
	}

	private Language convert(Language source, Language prototype) {

		prototype.setId(source.getId());
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
		prototype.setActive(source.getActive());
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}

package com.beanframework.language.converter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;

@Component
public class EntityLanguageConverter implements EntityConverter<Language, Language> {

	@Autowired
	private LanguageService languageService;

	@Override
	public Language convert(Language source) {

		Optional<Language> prototype = Optional.of(languageService.create());
		if (source.getUuid() != null) {
			Optional<Language> exists = languageService.findEntityByUuid(source.getUuid());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}
		else if (StringUtils.isNotEmpty(source.getId())) {
			Optional<Language> exists = languageService.findEntityById(source.getId());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}

		return convert(source, prototype.get());
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

package com.beanframework.language.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;

@Component
public class DtoLanguageConverter implements Converter<Language, Language> {

	@Autowired
	private LanguageService languageService;

	@Override
	public Language convert(Language source) {
		return convert(source, languageService.create());
	}

	public List<Language> convert(List<Language> sources) {
		List<Language> convertedList = new ArrayList<Language>();
		for (Language source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Language convert(Language source, Language prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
		prototype.setActive(source.getActive());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		return prototype;
	}

}

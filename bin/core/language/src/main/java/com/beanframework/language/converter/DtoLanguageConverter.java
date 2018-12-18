package com.beanframework.language.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.domain.Language;

@Component
public class DtoLanguageConverter implements DtoConverter<Language, Language> {

	@Override
	public Language convert(Language source) {
		return convert(source, new Language());
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
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
		prototype.setActive(source.getActive());

		return prototype;
	}

}

package com.beanframework.language.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.service.ModelService;
import com.beanframework.language.domain.Language;

@Component
public class DtoLanguageConverter implements DtoConverter<Language, Language> {

	@Autowired
	private ModelService modelService;

	@Override
	public Language convert(Language source) {
		return convert(source, modelService.create(Language.class));
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

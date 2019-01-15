package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.language.domain.Language;

public class DtoLanguageConverter implements DtoConverter<Language, LanguageDto> {

	@Override
	public LanguageDto convert(Language source) {
		return convert(source, new LanguageDto());
	}

	public List<LanguageDto> convert(List<Language> sources) {
		List<LanguageDto> convertedList = new ArrayList<LanguageDto>();
		for (Language source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private LanguageDto convert(Language source, LanguageDto prototype) {
		
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

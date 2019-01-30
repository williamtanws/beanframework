package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.language.domain.Language;

public class DtoLanguageConverter implements DtoConverter<Language, LanguageDto> {

	@Override
	public LanguageDto convert(Language source, ModelAction action) {
		return convert(source, new LanguageDto(), action);
	}

	public List<LanguageDto> convert(List<Language> sources, ModelAction action) {
		List<LanguageDto> convertedList = new ArrayList<LanguageDto>();
		for (Language source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private LanguageDto convert(Language source, LanguageDto prototype, ModelAction action) {
		
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

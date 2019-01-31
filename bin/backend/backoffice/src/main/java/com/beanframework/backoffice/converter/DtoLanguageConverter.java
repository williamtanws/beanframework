package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.language.domain.Language;

public class DtoLanguageConverter implements DtoConverter<Language, LanguageDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoLanguageConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public LanguageDto convert(Language source, ModelAction action) throws ConverterException {
		return convert(source, new LanguageDto(), action);
	}

	public List<LanguageDto> convert(List<Language> sources, ModelAction action) throws ConverterException {
		List<LanguageDto> convertedList = new ArrayList<LanguageDto>();
		for (Language source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private LanguageDto convert(Language source, LanguageDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
		prototype.setActive(source.getActive());

		try {
			ModelAction disableInitialCollectionAction = new ModelAction();
			disableInitialCollectionAction.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionAction, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionAction, AuditorDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

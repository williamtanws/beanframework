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
import com.beanframework.core.data.DynamicFieldEnumDto;
import com.beanframework.dynamicfield.domain.DynamicFieldEnum;

public class DtoDynamicFieldEnumConverter implements DtoConverter<DynamicFieldEnum, DynamicFieldEnumDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoDynamicFieldEnumConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public DynamicFieldEnumDto convert(DynamicFieldEnum source, ModelAction action) throws ConverterException {
		return convert(source, new DynamicFieldEnumDto(), action);
	}

	public List<DynamicFieldEnumDto> convert(List<DynamicFieldEnum> sources, ModelAction action) throws ConverterException {
		List<DynamicFieldEnumDto> convertedList = new ArrayList<DynamicFieldEnumDto>();
		for (DynamicFieldEnum source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private DynamicFieldEnumDto convert(DynamicFieldEnum source, DynamicFieldEnumDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setEnumGroup(source.getEnumGroup());
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());

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

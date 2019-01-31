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
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.menu.domain.MenuField;

public class DtoMenuFieldConverter implements DtoConverter<MenuField, MenuFieldDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuFieldConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public MenuFieldDto convert(MenuField source, ModelAction action) throws ConverterException {
		return convert(source, new MenuFieldDto(), action);
	}

	public List<MenuFieldDto> convert(List<MenuField> sources, ModelAction action) throws ConverterException {
		List<MenuFieldDto> convertedList = new ArrayList<MenuFieldDto>();
		for (MenuField source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	public MenuFieldDto convert(MenuField source, MenuFieldDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setValue(source.getValue());

		try {
			ModelAction disableInitialCollectionAction = new ModelAction();
			disableInitialCollectionAction.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionAction, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionAction, AuditorDto.class));

			if (action.isInitializeCollection()) {
				prototype.setDynamicField(modelService.getDto(source.getDynamicField(), action, DynamicFieldDto.class));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

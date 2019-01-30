package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.ModelAction;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.menu.domain.Menu;

public class DtoMenuConverter implements DtoConverter<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public MenuDto convert(Menu source, ModelAction action) throws ConverterException {
		return convert(source, new MenuDto(), action);
	}

	public List<MenuDto> convert(List<Menu> sources, ModelAction action) throws ConverterException {
		List<MenuDto> convertedList = new ArrayList<MenuDto>();
		for (Menu source : sources) {
			convertedList.add(convert(source, action));
		}
		return convertedList;
	}

	private MenuDto convert(Menu source, MenuDto prototype, ModelAction action) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setParent(source.getParent());
		prototype.setIcon(source.getIcon());
		prototype.setPath(source.getPath());
		prototype.setSort(source.getSort());
		prototype.setTarget(source.getTarget());
		prototype.setEnabled(source.getEnabled());

		if (action.isInitializeCollection()) {
			try {
				prototype.setChilds(modelService.getDto(source.getChilds(), action, MenuDto.class));
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), action, UserGroupDto.class));
				prototype.setFields(modelService.getDto(source.getFields(), action, MenuFieldDto.class));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw new ConverterException(e.getMessage(), e);
			}
		}

		return prototype;
	}
}

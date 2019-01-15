package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.MenuDto;
import com.beanframework.backoffice.data.MenuFieldDto;
import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;

public class DtoMenuConverter implements DtoConverter<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public MenuDto convert(Menu source) throws ConverterException {
		return convert(source, new MenuDto());
	}

	public List<MenuDto> convert(List<Menu> sources) throws ConverterException {
		List<MenuDto> convertedList = new ArrayList<MenuDto>();
		for (Menu source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private MenuDto convert(Menu source, MenuDto prototype) throws ConverterException {

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
		try {
			prototype.setChilds(modelService.getDto(source.getChilds(), MenuDto.class));
			prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class));
			prototype.setFields(modelService.getDto(source.getFields(), MenuFieldDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}

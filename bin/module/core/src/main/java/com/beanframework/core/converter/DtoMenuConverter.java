package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.AbstractDtoConverter;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.menu.domain.Menu;

public class DtoMenuConverter extends AbstractDtoConverter<Menu, MenuDto> implements DtoConverter<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoMenuConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public MenuDto convert(Menu source, DtoConverterContext context) throws ConverterException {
		return convert(source, new MenuDto(), context);
	}

	public List<MenuDto> convert(List<Menu> sources, DtoConverterContext context) throws ConverterException {
		List<MenuDto> convertedList = new ArrayList<MenuDto>();
		for (Menu source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private MenuDto convert(Menu source, MenuDto prototype, DtoConverterContext context) throws ConverterException {

		try {
			convertCommonProperties(source, prototype, context);

			prototype.setName(source.getName());
			prototype.setParent(modelService.getDto(source.getParent(), MenuDto.class, new DtoConverterContext(ConvertRelationType.BASIC)));
			prototype.setIcon(source.getIcon());
			prototype.setPath(source.getPath());
			prototype.setSort(source.getSort());
			prototype.setTarget(source.getTarget());
			prototype.setEnabled(source.getEnabled());

			if (context.getConverModelType() == ConvertRelationType.ALL) {

				prototype.setChilds(modelService.getDto(source.getChilds(), MenuDto.class, new DtoConverterContext(ConvertRelationType.ALL)));
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class, new DtoConverterContext(ConvertRelationType.ALL)));

				prototype.setFields(modelService.getDto(source.getFields(), MenuFieldDto.class, new DtoConverterContext(ConvertRelationType.ALL)));
				Collections.sort(prototype.getFields(), new Comparator<MenuFieldDto>() {
					@Override
					public int compare(MenuFieldDto o1, MenuFieldDto o2) {
						if (o1.getDynamicFieldSlot().getSort() == null)
							return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

						if (o2.getDynamicFieldSlot().getSort() == null)
							return -1;

						return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
					}
				});
			}

		} catch (

		Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}

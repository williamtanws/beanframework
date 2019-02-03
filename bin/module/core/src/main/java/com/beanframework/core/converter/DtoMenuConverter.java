package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.AuditorDto;
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
	public MenuDto convert(Menu source, InterceptorContext context) throws ConverterException {
		return convert(source, new MenuDto(), context);
	}

	public List<MenuDto> convert(List<Menu> sources, InterceptorContext context) throws ConverterException {
		List<MenuDto> convertedList = new ArrayList<MenuDto>();
		for (Menu source : sources) {
			convertedList.add(convert(source, context));
		}
		return convertedList;
	}

	private MenuDto convert(Menu source, MenuDto prototype, InterceptorContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		prototype.setName(source.getName());
		prototype.setParent(source.getParent());
		prototype.setIcon(source.getIcon());
		prototype.setPath(source.getPath());
		prototype.setSort(String.valueOf(source.getSort()));
		prototype.setTarget(source.getTarget());
		prototype.setEnabled(source.getEnabled());

		try {
			InterceptorContext disableInitialCollectionContext = new InterceptorContext();
			disableInitialCollectionContext.setInitializeCollection(false);

			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), disableInitialCollectionContext, AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), disableInitialCollectionContext, AuditorDto.class));

			if (context.isInitializeCollection()) {
				prototype.setChilds(modelService.getDto(source.getChilds(), context, MenuDto.class));
				prototype.setUserGroups(modelService.getDto(source.getUserGroups(), context, UserGroupDto.class));
				prototype.setFields(modelService.getDto(source.getFields(), context, MenuFieldDto.class));
				Collections.sort(prototype.getFields(), new Comparator<MenuFieldDto>() {
					@Override
					public int compare(MenuFieldDto o1, MenuFieldDto o2) {
						if (o1.getDynamicField() == null || o1.getDynamicField().getSort() == null)
							return (o2.getDynamicField() == null || o2.getDynamicField().getSort() == null) ? 0 : 1;

						if (o2.getDynamicField() == null || o2.getDynamicField().getSort() == null)
							return -1;

						return Integer.valueOf(o1.getDynamicField().getSort()) - Integer.valueOf(o2.getDynamicField().getSort());
					}
				});
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}
}

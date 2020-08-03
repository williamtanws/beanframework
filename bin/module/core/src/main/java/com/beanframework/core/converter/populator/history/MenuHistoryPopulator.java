package com.beanframework.core.converter.populator.history;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.menu.domain.Menu;

@Component
public class MenuHistoryPopulator extends AbstractPopulator<Menu, MenuDto> implements Populator<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MenuHistoryPopulator.class);

	@Autowired
	private MenuHistoryPopulator menuHistoryPopulator;

	@Autowired
	private UserGroupHistoryPopulator userGroupHistoryPopulator;

	@Autowired
	private MenuFieldHistoryPopulator menuFieldHistoryPopulator;

	@Override
	public void populate(Menu source, MenuDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setParent(modelService.getDto(source.getParent(), MenuDto.class, new DtoConverterContext(menuHistoryPopulator)));
			target.setIcon(source.getIcon());
			target.setPath(source.getPath());
			target.setSort(source.getSort());
			target.setTarget(source.getTarget());
			target.setEnabled(source.getEnabled());
			target.setChilds(modelService.getDto(source.getChilds(), MenuDto.class, new DtoConverterContext(menuHistoryPopulator)));
			target.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class, new DtoConverterContext(userGroupHistoryPopulator)));

			target.setFields(modelService.getDto(source.getFields(), MenuFieldDto.class, new DtoConverterContext(menuFieldHistoryPopulator)));
			Collections.sort(target.getFields(), new Comparator<MenuFieldDto>() {
				@Override
				public int compare(MenuFieldDto o1, MenuFieldDto o2) {
					if (o1.getDynamicFieldSlot().getSort() == null)
						return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

					if (o2.getDynamicFieldSlot().getSort() == null)
						return -1;

					return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
				}
			});
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}

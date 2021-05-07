package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuAttributeDto;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuAttribute;

public class MenuBreadcrumbsPopulator extends AbstractPopulator<Menu, MenuDto> implements Populator<Menu, MenuDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MenuBreadcrumbsPopulator.class);

	@Override
	public void populate(Menu source, MenuDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			target.setIcon(source.getIcon());
			target.setPath(source.getPath());
			target.setSort(source.getSort());
			target.setTarget(source.getTarget());
			target.setEnabled(source.getEnabled());

			for (MenuAttribute field : source.getAttributes()) {
				target.getAttributes().add(populateMenuField(field));
			}

			Collections.sort(target.getAttributes(), new Comparator<MenuAttributeDto>() {
				@Override
				public int compare(MenuAttributeDto o1, MenuAttributeDto o2) {
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

	public MenuAttributeDto populateMenuField(MenuAttribute source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			MenuAttributeDto target = new MenuAttributeDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setValue(source.getValue());
			target.setDynamicFieldSlot(populateDynamicFieldSlot(source.getDynamicFieldSlot()));

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
}

package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.MenuAttributeDto;
import com.beanframework.core.data.MenuDto;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuAttribute;

@Component
public class MenuPopulator extends AbstractPopulator<Menu, MenuDto>
    implements Populator<Menu, MenuDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(MenuPopulator.class);

  @Override
  public void populate(Menu source, MenuDto target) throws PopulatorException {
    try {
      populateGeneric(source, target);
      target.setName(source.getName());
      target.setParent(populateParent(source.getParent()));
      target.setIcon(source.getIcon());
      target.setPath(source.getPath());
      target.setSort(source.getSort());
      target.setTarget(source.getTarget());
      target.setEnabled(source.getEnabled());

      Hibernate.initialize(source.getChilds());
      for (Menu child : source.getChilds()) {
        target.getChilds().add(populateChild(child.getUuid()));
      }
      for (UUID userGroup : source.getUserGroups()) {
        target.getUserGroups().add(populateUserGroup(userGroup));
      }
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

  public MenuDto populateParent(Menu source) throws PopulatorException {
    if (source == null)
      return null;

    MenuDto target = new MenuDto();
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
    return target;
  }

  public MenuDto populateChild(UUID uuid) throws PopulatorException {
    if (uuid == null)
      return null;

    try {
      Menu source = modelService.findOneByUuid(uuid, Menu.class);
      if (source == null) {
        return null;
      }

      MenuDto target = new MenuDto();
      populateGeneric(source, target);
      target.setName(source.getName());
      target.setIcon(source.getIcon());
      target.setPath(source.getPath());
      target.setSort(source.getSort());
      target.setTarget(source.getTarget());
      target.setEnabled(source.getEnabled());

      Hibernate.initialize(source.getChilds());
      for (Menu child : source.getChilds()) {
        target.getChilds().add(populateChild(child.getUuid()));
      }
      for (UUID userGroup : source.getUserGroups()) {
        target.getUserGroups().add(populateUserGroup(userGroup));
      }
      for (MenuAttribute field : source.getAttributes()) {
        target.getAttributes().add(populateMenuField(field));
      }

      return target;
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

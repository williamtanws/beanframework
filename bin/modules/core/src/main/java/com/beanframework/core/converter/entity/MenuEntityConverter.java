package com.beanframework.core.converter.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MenuAttributeDto;
import com.beanframework.core.data.MenuDto;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;

@Component
public class MenuEntityConverter implements EntityConverter<MenuDto, Menu> {

  protected static Logger LOGGER = LoggerFactory.getLogger(MenuEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public Menu convert(MenuDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {

        Menu prototype = modelService.findOneByUuid(source.getUuid(), Menu.class);

        if (prototype != null) {
          return convertToEntity(source, prototype);
        }
      }

      return convertToEntity(source, modelService.create(Menu.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

  }

  public List<Menu> convertToEntity(List<MenuDto> sources) throws ConverterException {
    List<Menu> convertedList = new ArrayList<Menu>();
    for (MenuDto source : sources) {
      convertedList.add(convert(source));
    }
    return convertedList;
  }

  private Menu convertToEntity(MenuDto source, Menu prototype) throws ConverterException {

    try {
      Date lastModifiedDate = new Date();

      if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
          prototype.getId()) == Boolean.FALSE) {
        prototype.setId(StringUtils.stripToNull(source.getId()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getName()),
          prototype.getName()) == Boolean.FALSE) {
        prototype.setName(StringUtils.stripToNull(source.getName()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (source.getSort() == null) {
        if (prototype.getSort() != null) {
          prototype.setSort(null);
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      } else {
        if (prototype.getSort() == null
            || prototype.getSort() == source.getSort() == Boolean.FALSE) {
          prototype.setSort(source.getSort());
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getIcon()),
          prototype.getIcon()) == Boolean.FALSE) {
        prototype.setIcon(StringUtils.stripToNull(source.getIcon()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getPath()),
          prototype.getPath()) == Boolean.FALSE) {
        prototype.setPath(StringUtils.stripToNull(source.getPath()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (prototype.getTarget() == source.getTarget() == Boolean.FALSE) {
        prototype.setTarget(source.getTarget());
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (source.getEnabled() == null) {
        if (prototype.getEnabled() != null) {
          prototype.setEnabled(null);
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      } else {
        if (prototype.getEnabled() == null
            || prototype.getEnabled().equals(source.getEnabled()) == Boolean.FALSE) {
          prototype.setEnabled(source.getEnabled());
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      }

      // Attribute
      if (source.getAttributes() != null && source.getAttributes().isEmpty() == Boolean.FALSE) {
        for (int i = 0; i < prototype.getAttributes().size(); i++) {
          for (MenuAttributeDto sourceField : source.getAttributes()) {

            if (prototype.getAttributes().get(i).getDynamicFieldSlot()
                .equals(sourceField.getDynamicFieldSlot().getUuid())) {
              if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()),
                  prototype.getAttributes().get(i).getValue()) == Boolean.FALSE) {
                prototype.getAttributes().get(i)
                    .setValue(StringUtils.stripToNull(sourceField.getValue()));

                prototype.getAttributes().get(i).setLastModifiedDate(lastModifiedDate);
                prototype.setLastModifiedDate(lastModifiedDate);
              }
            }
          }
        }
      }

      // User Groups
      if (source.getSelectedUserGroupUuids() != null) {

        Iterator<UUID> itr = prototype.getUserGroups().iterator();
        while (itr.hasNext()) {
          UUID model = itr.next();

          boolean remove = true;
          for (int i = 0; i < source.getSelectedUserGroupUuids().length; i++) {
            if (model.equals(UUID.fromString(source.getSelectedUserGroupUuids()[i]))) {
              remove = false;
            }
          }
          if (remove) {
            itr.remove();
            prototype.setLastModifiedDate(lastModifiedDate);
          }
        }

        for (int i = 0; i < source.getSelectedUserGroupUuids().length; i++) {

          boolean add = true;
          itr = prototype.getUserGroups().iterator();
          while (itr.hasNext()) {
            UUID model = itr.next();

            if (model.equals(UUID.fromString(source.getSelectedUserGroupUuids()[i]))) {
              add = false;
            }
          }

          if (add) {
            UserGroup entity = modelService.findOneByUuid(
                UUID.fromString(source.getSelectedUserGroupUuids()[i]), UserGroup.class);
            if (entity != null) {
              prototype.getUserGroups().add(entity.getUuid());
              prototype.setLastModifiedDate(lastModifiedDate);
            }
          }
        }
      } else if (prototype.getUserGroups() != null
          && prototype.getUserGroups().isEmpty() == false) {
        for (final Iterator<UUID> itr = prototype.getUserGroups().iterator(); itr.hasNext();) {
          itr.next();
          itr.remove();
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      }

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
    return prototype;
  }

}

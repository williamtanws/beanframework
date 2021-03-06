package com.beanframework.core.converter.entity.csv;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.MenuCsv;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;

@Component
public class MenuCsvEntityConverter implements EntityCsvConverter<MenuCsv, Menu> {

  protected static Logger LOGGER = LoggerFactory.getLogger(MenuCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public Menu convert(MenuCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Menu.ID, source.getId());

        Menu prototype = modelService.findOneByProperties(properties, Menu.class);

        if (prototype != null) {

          return convert(source, prototype);
        }
      }
      return convert(source, modelService.create(Menu.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Menu convert(MenuCsv source, Menu prototype) throws ConverterException {

    try {
      if (StringUtils.isNotBlank(source.getId()))
        prototype.setId(source.getId());

      if (StringUtils.isNotBlank(source.getName()))
        prototype.setName(source.getName());

      if (source.getSort() != null)
        prototype.setSort(source.getSort());

      if (StringUtils.isNotBlank(source.getIcon()))
        prototype.setIcon(source.getIcon());

      if (StringUtils.isNotBlank(source.getPath()))
        prototype.setPath(source.getPath());

      if (source.getTarget() != null)
        prototype.setTarget(source.getTarget());

      if (source.getEnabled() != null)
        prototype.setEnabled(source.getEnabled());

      // Parent
      if (StringUtils.isNotBlank(source.getParent())) {
        Map<String, Object> parentProperties = new HashMap<String, Object>();
        parentProperties.put(Menu.ID, source.getParent());

        Menu parent = modelService.findOneByProperties(parentProperties, Menu.class);

        if (parent == null) {
          LOGGER.error("Parent not exists: " + source.getParent());
        } else {
          boolean addChild = true;
          for (Menu child : parent.getChilds()) {
            if (child.getUuid().equals(prototype.getUuid())) {
              addChild = false;
            }
          }
          if (addChild) {
            parent.getChilds().add(prototype);
            prototype.setParent(parent);
          }
        }
      }

      // Attributes
      if (StringUtils.isNotBlank(source.getDynamicFieldSlotIds())) {
        String[] dynamicFieldSlots = source.getDynamicFieldSlotIds().split(ImportListener.SPLITTER);

        if (dynamicFieldSlots != null) {
          for (int i = 0; i < prototype.getAttributes().size(); i++) {

            for (String dynamicFieldSlot : dynamicFieldSlots) {
              String dynamicFieldSlotId =
                  StringUtils.stripToNull(dynamicFieldSlot.split(ImportListener.EQUALS)[0]);
              String value =
                  StringUtils.stripToNull(dynamicFieldSlot.split(ImportListener.EQUALS)[1]);

              Map<String, Object> properties = new HashMap<String, Object>();
              properties.put(DynamicFieldSlot.ID, dynamicFieldSlotId);
              DynamicFieldSlot slot =
                  modelService.findOneByProperties(properties, DynamicFieldSlot.class);

              if (prototype.getAttributes().get(i).getDynamicFieldSlot().equals(slot.getUuid())) {
                if (StringUtils.equals(StringUtils.stripToNull(value),
                    prototype.getAttributes().get(i).getValue()) == Boolean.FALSE) {
                  prototype.getAttributes().get(i).setValue(StringUtils.stripToNull(value));
                }
              }
            }
          }
        }
      }

      // User Group
      if (StringUtils.isNotBlank(source.getUserGroupIds())) {
        String[] userGroupIds = source.getUserGroupIds().split(ImportListener.SPLITTER);
        for (int i = 0; i < userGroupIds.length; i++) {
          boolean add = true;
          for (UUID userGroup : prototype.getUserGroups()) {
            UserGroup entity = modelService.findOneByUuid(userGroup, UserGroup.class);
            if (StringUtils.equals(entity.getId(), userGroupIds[i]))
              add = false;
          }

          if (add) {
            Map<String, Object> userGroupProperties = new HashMap<String, Object>();
            userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
            UserGroup userGroup =
                modelService.findOneByProperties(userGroupProperties, UserGroup.class);

            if (userGroup == null) {
              LOGGER.error("UserGroup ID not exists: " + userGroupIds[i]);
            } else {
              prototype.getUserGroups().add(userGroup.getUuid());
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

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
import com.beanframework.core.csv.UserGroupCsv;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.user.domain.UserGroup;

@Component
public class UserGroupCsvEntityConverter implements EntityCsvConverter<UserGroupCsv, UserGroup> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserGroupCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public UserGroup convert(UserGroupCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(UserGroup.ID, source.getId());

        UserGroup prototype = modelService.findOneByProperties(properties, UserGroup.class);

        if (prototype != null) {
          return convert(source, prototype);
        }
      }
      return convert(source, modelService.create(UserGroup.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private UserGroup convert(UserGroupCsv source, UserGroup prototype) throws ConverterException {

    try {
      if (StringUtils.isNotBlank(source.getId()))
        prototype.setId(source.getId());

      if (StringUtils.isNotBlank(source.getName()))
        prototype.setName(source.getName());

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

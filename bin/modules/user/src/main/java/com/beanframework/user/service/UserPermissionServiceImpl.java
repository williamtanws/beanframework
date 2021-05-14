package com.beanframework.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.user.UserPermissionConstants;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionAttribute;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

  @Autowired
  private ModelService modelService;

  @Value(UserPermissionConstants.CONFIGURATION_DYNAMIC_FIELD_TEMPLATE)
  private String CONFIGURATION_DYNAMIC_FIELD_TEMPLATE;

  @Override
  public void generateUserPermissionAttribute(UserPermission model) throws Exception {
    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Configuration.ID, CONFIGURATION_DYNAMIC_FIELD_TEMPLATE);
    Configuration configuration = modelService.findOneByProperties(properties, Configuration.class);

    if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
      properties = new HashMap<String, Object>();
      properties.put(DynamicFieldTemplate.ID, configuration.getValue());

      DynamicFieldTemplate dynamicFieldTemplate =
          modelService.findOneByProperties(properties, DynamicFieldTemplate.class);

      if (dynamicFieldTemplate != null) {

        for (UUID dynamicFieldSlot : dynamicFieldTemplate.getDynamicFieldSlots()) {

          boolean addField = true;
          for (UserPermissionAttribute field : model.getAttributes()) {
            if (field.getDynamicFieldSlot().equals(dynamicFieldSlot)) {
              addField = false;
            }
          }

          if (addField) {
            UserPermissionAttribute field = new UserPermissionAttribute();
            field.setDynamicFieldSlot(dynamicFieldSlot);
            field.setUserPermission(model);
            model.getAttributes().add(field);
          }
        }
      }
    }
  }
}

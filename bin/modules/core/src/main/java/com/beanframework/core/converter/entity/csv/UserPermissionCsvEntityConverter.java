package com.beanframework.core.converter.entity.csv;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.UserPermissionCsv;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.user.domain.UserPermission;

@Component
public class UserPermissionCsvEntityConverter
    implements EntityCsvConverter<UserPermissionCsv, UserPermission> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Override
  public UserPermission convert(UserPermissionCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(UserPermission.ID, source.getId());

        UserPermission prototype =
            modelService.findOneByProperties(properties, UserPermission.class);

        if (prototype != null) {

          return convert(source, prototype);
        }
      }
      return convert(source, modelService.create(UserPermission.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private UserPermission convert(UserPermissionCsv source, UserPermission prototype)
      throws ConverterException {

    try {
      if (StringUtils.isNotBlank(source.getId()))
        prototype.setId(source.getId());

      if (StringUtils.isNotBlank(source.getName()))
        prototype.setName(source.getName());

      if (source.getSort() != null)
        prototype.setSort(source.getSort());

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

    } catch (Exception e) {
      e.printStackTrace();
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

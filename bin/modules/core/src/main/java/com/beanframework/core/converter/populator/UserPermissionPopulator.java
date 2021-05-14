package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserPermissionAttributeDto;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionAttribute;

@Component
public class UserPermissionPopulator extends AbstractPopulator<UserPermission, UserPermissionDto>
    implements Populator<UserPermission, UserPermissionDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserPermissionPopulator.class);

  @Override
  public void populate(UserPermission source, UserPermissionDto target) throws PopulatorException {
    try {
      populateGeneric(source, target);
      target.setName(source.getName());
      target.setSort(source.getSort());

      for (UserPermissionAttribute field : source.getAttributes()) {
        target.getAttributes().add(populateUserPermissionField(field));
      }

      Collections.sort(target.getAttributes(), new Comparator<UserPermissionAttributeDto>() {
        @Override
        public int compare(UserPermissionAttributeDto o1, UserPermissionAttributeDto o2) {
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

  public UserPermissionAttributeDto populateUserPermissionField(UserPermissionAttribute source)
      throws PopulatorException {
    if (source == null)
      return null;

    try {
      UserPermissionAttributeDto target = new UserPermissionAttributeDto();
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

package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserAttributeDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAttribute;

@Component
public class UserPopulator extends AbstractPopulator<User, UserDto>
    implements Populator<User, UserDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(UserPopulator.class);

  @Override
  public void populate(User source, UserDto target) throws PopulatorException {
    try {
      populateGeneric(source, target);
      target.setName(source.getName());
      target.setType(source.getType());
      target.setAccountNonExpired(source.getAccountNonExpired());
      target.setAccountNonLocked(source.getAccountNonLocked());
      target.setCredentialsNonExpired(source.getCredentialsNonExpired());
      target.setEnabled(source.getEnabled());
      target.setParameters(source.getParameters());

      for (UUID uuid : source.getUserGroups()) {
        target.getUserGroups().add(populateUserGroup(uuid));
      }
      for (UUID uuid : source.getCompanies()) {
        target.getCompanies().add(populateCompany(uuid));
      }
      for (UUID uuid : source.getAddresses()) {
        target.getAddresses().add(populateAddress(uuid));
      }
      for (UserAttribute field : source.getAttributes()) {
        target.getAttributes().add(populateUserField(field));
      }

      if (target.getAttributes() != null)
        Collections.sort(target.getAttributes(), new Comparator<UserAttributeDto>() {
          @Override
          public int compare(UserAttributeDto o1, UserAttributeDto o2) {
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

  public UserAttributeDto populateUserField(UserAttribute source) throws PopulatorException {
    if (source == null)
      return null;

    try {
      UserAttributeDto target = new UserAttributeDto();
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

package com.beanframework.core.converter.entity.csv;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityCsvConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.csv.CustomerCsv;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.imex.registry.ImportListener;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.Customer;
import com.beanframework.user.domain.UserGroup;

@Component
public class CustomerCsvEntityConverter implements EntityCsvConverter<CustomerCsv, Customer> {

  protected static Logger LOGGER = LoggerFactory.getLogger(CustomerCsvEntityConverter.class);

  @Autowired
  private ModelService modelService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Customer convert(CustomerCsv source) throws ConverterException {

    try {

      if (StringUtils.isNotBlank(source.getId())) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(Customer.ID, source.getId());

        Customer prototype = modelService.findOneByProperties(properties, Customer.class);

        if (prototype != null) {
          return convertToEntity(source, prototype);
        }
      }
      return convertToEntity(source, modelService.create(Customer.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Customer convertToEntity(CustomerCsv source, Customer prototype)
      throws ConverterException {

    try {
      if (StringUtils.isNotBlank(source.getId()))
        prototype.setId(source.getId());

      if (StringUtils.isNotBlank(source.getProfilePicture()))
        prototype.setProfilePicture(source.getProfilePicture());

      if (StringUtils.isNotBlank(source.getName()))
        prototype.setName(source.getName());

      if (source.getAccountNonExpired() != null)
        prototype.setAccountNonExpired(source.getAccountNonExpired());

      if (source.getAccountNonLocked() != null)
        prototype.setAccountNonLocked(source.getAccountNonLocked());

      if (source.getCredentialsNonExpired() != null)
        prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());

      if (source.getEnabled() != null)
        prototype.setEnabled(source.getEnabled());

      if (StringUtils.isNotBlank(source.getPassword()))
        prototype.setPassword(passwordEncoder.encode(source.getPassword()));

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

      // Company
      if (StringUtils.isNotBlank(source.getCompanyIds())) {
        String[] companyIds = source.getCompanyIds().split(ImportListener.SPLITTER);
        for (int i = 0; i < companyIds.length; i++) {
          boolean add = true;
          for (UUID company : prototype.getCompanies()) {
            Company entity = modelService.findOneByUuid(company, Company.class);
            if (StringUtils.equals(entity.getId(), companyIds[i]))
              add = false;
          }

          if (add) {
            Map<String, Object> companyProperties = new HashMap<String, Object>();
            companyProperties.put(Company.ID, companyIds[i]);
            Company company = modelService.findOneByProperties(companyProperties, Company.class);

            if (company == null) {
              LOGGER.error("Company ID not exists: " + companyIds[i]);
            } else {
              prototype.getCompanies().add(company.getUuid());
            }
          }
        }
      }

      // Address
      if (StringUtils.isNotBlank(source.getAddressIds())) {
        String[] addressIds = source.getAddressIds().split(ImportListener.SPLITTER);
        for (int i = 0; i < addressIds.length; i++) {
          boolean add = true;
          for (UUID address : prototype.getAddresses()) {
            Address entity = modelService.findOneByUuid(address, Address.class);
            if (StringUtils.equals(entity.getId(), addressIds[i]))
              add = false;
          }

          if (add) {
            Map<String, Object> addressProperties = new HashMap<String, Object>();
            addressProperties.put(Address.ID, addressIds[i]);
            Address address = modelService.findOneByProperties(addressProperties, Address.class);

            if (address == null) {
              LOGGER.error("Address ID not exists: " + addressIds[i]);
            } else {
              prototype.getAddresses().add(address.getUuid());
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

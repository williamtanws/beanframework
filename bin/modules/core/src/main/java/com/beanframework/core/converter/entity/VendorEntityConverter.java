package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserAttributeDto;
import com.beanframework.core.data.VendorDto;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.Vendor;

@Component
public class VendorEntityConverter implements EntityConverter<VendorDto, Vendor> {

  @Autowired
  private ModelService modelService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public Vendor convert(VendorDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Vendor prototype = modelService.findOneByUuid(source.getUuid(), Vendor.class);

        if (prototype != null) {
          return convertToEntity(source, prototype);
        }
      }

      return convertToEntity(source, modelService.create(Vendor.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

  }

  private Vendor convertToEntity(VendorDto source, Vendor prototype) throws ConverterException {

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

      if (StringUtils.isNotBlank(source.getPassword())) {
        prototype.setPassword(passwordEncoder.encode(source.getPassword()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (source.getAccountNonExpired() == null) {
        if (prototype.getAccountNonExpired() != null) {
          prototype.setAccountNonExpired(null);
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      } else {
        if (prototype.getAccountNonExpired() == null || prototype.getAccountNonExpired()
            .equals(source.getAccountNonExpired()) == Boolean.FALSE) {
          prototype.setAccountNonExpired(source.getAccountNonExpired());
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      }

      if (source.getAccountNonLocked() == null) {
        if (prototype.getAccountNonLocked() != null) {
          prototype.setAccountNonLocked(null);
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      } else {
        if (prototype.getAccountNonLocked() == null || prototype.getAccountNonLocked()
            .equals(source.getAccountNonLocked()) == Boolean.FALSE) {
          prototype.setAccountNonLocked(source.getAccountNonLocked());
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      }

      if (source.getCredentialsNonExpired() == null) {
        if (prototype.getCredentialsNonExpired() != null) {
          prototype.setCredentialsNonExpired(null);
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      } else {
        if (prototype.getCredentialsNonExpired() == null || prototype.getCredentialsNonExpired()
            .equals(source.getCredentialsNonExpired()) == Boolean.FALSE) {
          prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
          prototype.setLastModifiedDate(lastModifiedDate);
        }
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

      // Parameters
      Map<String, String> prototypeParameters = new HashMap<String, String>();
      if (source.getSelectedParameterKeys() != null && source.getSelectedParameterValues() != null
          && source.getSelectedParameterKeys().length == source
              .getSelectedParameterValues().length) {
        for (int i = 0; i < source.getSelectedParameterKeys().length; i++) {
          prototypeParameters.put(source.getSelectedParameterKeys()[i],
              source.getSelectedParameterValues()[i]);
        }
      }

      if (prototypeParameters.isEmpty()) {
        prototype.getParameters().clear();
      } else {

        for (Map.Entry<String, String> entry : prototypeParameters.entrySet()) {
          prototype.getParameters().put(entry.getKey(), entry.getValue());
        }
      }

      if (source.getParameters().equals(prototypeParameters) == false) {
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      // Attribute
      if (source.getAttributes() != null && source.getAttributes().isEmpty() == Boolean.FALSE) {
        for (int i = 0; i < prototype.getAttributes().size(); i++) {
          for (UserAttributeDto sourceField : source.getAttributes()) {

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

      // Companies
      if (source.getSelectedCompanyUuids() != null) {

        Iterator<UUID> itr = prototype.getCompanies().iterator();
        while (itr.hasNext()) {
          UUID model = itr.next();

          boolean remove = true;
          for (int i = 0; i < source.getSelectedCompanyUuids().length; i++) {
            if (model.equals(UUID.fromString(source.getSelectedCompanyUuids()[i]))) {
              remove = false;
            }
          }
          if (remove) {
            itr.remove();
            prototype.setLastModifiedDate(lastModifiedDate);
          }
        }

        for (int i = 0; i < source.getSelectedCompanyUuids().length; i++) {

          boolean add = true;
          itr = prototype.getCompanies().iterator();
          while (itr.hasNext()) {
            UUID model = itr.next();

            if (model.equals(UUID.fromString(source.getSelectedCompanyUuids()[i]))) {
              add = false;
            }
          }

          if (add) {
            Company entity = modelService
                .findOneByUuid(UUID.fromString(source.getSelectedCompanyUuids()[i]), Company.class);
            if (entity != null) {
              prototype.getCompanies().add(entity.getUuid());
              prototype.setLastModifiedDate(lastModifiedDate);
            }
          }
        }
      } else if (prototype.getCompanies() != null && prototype.getCompanies().isEmpty() == false) {
        for (final Iterator<UUID> itr = prototype.getCompanies().iterator(); itr.hasNext();) {
          itr.next();
          itr.remove();
          prototype.setLastModifiedDate(lastModifiedDate);
        }
      }

      // Addresses
      if (source.getSelectedAddressUuids() != null) {

        Iterator<UUID> it = prototype.getAddresses().iterator();
        while (it.hasNext()) {
          UUID o = it.next();

          boolean remove = true;
          for (int i = 0; i < source.getSelectedAddressUuids().length; i++) {
            if (o.equals(UUID.fromString(source.getSelectedAddressUuids()[i]))) {
              remove = false;
            }
          }
          if (remove) {
            Address entity = modelService.findOneByUuid(o, Address.class);
            entity.setOwner(null);
            modelService.saveEntity(entity);
            it.remove();
            prototype.setLastModifiedDate(lastModifiedDate);
          }
        }

        for (int i = 0; i < source.getSelectedAddressUuids().length; i++) {

          boolean add = true;
          it = prototype.getAddresses().iterator();
          while (it.hasNext()) {
            UUID entity = it.next();

            if (entity.equals(UUID.fromString(source.getSelectedAddressUuids()[i]))) {
              add = false;
            }
          }

          if (add) {
            Address entity = modelService
                .findOneByUuid(UUID.fromString(source.getSelectedAddressUuids()[i]), Address.class);
            if (entity != null) {
              entity.setOwner(prototype.getUuid());
              prototype.getAddresses().add(entity.getUuid());
              prototype.setLastModifiedDate(lastModifiedDate);
            }
          }
        }
      } else if (prototype.getAddresses() != null && prototype.getAddresses().isEmpty() == false) {
        for (final Iterator<UUID> itr = prototype.getAddresses().iterator(); itr.hasNext();) {
          Address entity = modelService.findOneByUuid(itr.next(), Address.class);
          entity.setOwner(null);
          modelService.saveEntity(entity);
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

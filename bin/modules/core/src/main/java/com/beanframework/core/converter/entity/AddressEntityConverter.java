package com.beanframework.core.converter.entity;

import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AddressDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.User;

@Component
public class AddressEntityConverter implements EntityConverter<AddressDto, Address> {

  @Autowired
  private ModelService modelService;

  @Override
  public Address convert(AddressDto source) throws ConverterException {

    try {
      if (source.getUuid() != null) {
        Address prototype = modelService.findOneByUuid(source.getUuid(), Address.class);
        return convertToEntity(source, prototype);
      }
      return convertToEntity(source, modelService.create(Address.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Address convertToEntity(AddressDto source, Address prototype) throws ConverterException {

    try {

      if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
          prototype.getId()) == Boolean.FALSE) {
        prototype.setId(StringUtils.stripToNull(source.getId()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getStreetName()),
          prototype.getStreetName()) == Boolean.FALSE) {
        prototype.setStreetName(StringUtils.stripToNull(source.getStreetName()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getStreetNumber()),
          prototype.getStreetNumber()) == Boolean.FALSE) {
        prototype.setStreetNumber(StringUtils.stripToNull(source.getStreetNumber()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getPostalCode()),
          prototype.getPostalCode()) == Boolean.FALSE) {
        prototype.setPostalCode(StringUtils.stripToNull(source.getPostalCode()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getTown()),
          prototype.getTown()) == Boolean.FALSE) {
        prototype.setTown(StringUtils.stripToNull(source.getTown()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getCompany()),
          prototype.getCompany()) == Boolean.FALSE) {
        prototype.setCompany(StringUtils.stripToNull(source.getCompany()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getPhone1()),
          prototype.getPhone1()) == Boolean.FALSE) {
        prototype.setPhone1(StringUtils.stripToNull(source.getPhone1()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getPhone2()),
          prototype.getPhone2()) == Boolean.FALSE) {
        prototype.setPhone2(StringUtils.stripToNull(source.getPhone2()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getMobilePhone()),
          prototype.getMobilePhone()) == Boolean.FALSE) {
        prototype.setMobilePhone(StringUtils.stripToNull(source.getMobilePhone()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getEmail()),
          prototype.getEmail()) == Boolean.FALSE) {
        prototype.setEmail(StringUtils.stripToNull(source.getEmail()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getPoBox()),
          prototype.getPoBox()) == Boolean.FALSE) {
        prototype.setPoBox(StringUtils.stripToNull(source.getPoBox()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getFax()),
          prototype.getFax()) == Boolean.FALSE) {
        prototype.setFax(StringUtils.stripToNull(source.getFax()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getTitle()),
          prototype.getTitle()) == Boolean.FALSE) {
        prototype.setTitle(StringUtils.stripToNull(source.getTitle()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getLastName()),
          prototype.getLastName()) == Boolean.FALSE) {
        prototype.setLastName(StringUtils.stripToNull(source.getLastName()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getFirstName()),
          prototype.getFirstName()) == Boolean.FALSE) {
        prototype.setFirstName(StringUtils.stripToNull(source.getFirstName()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getMiddleName()),
          prototype.getMiddleName()) == Boolean.FALSE) {
        prototype.setMiddleName(StringUtils.stripToNull(source.getMiddleName()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getMiddleName2()),
          prototype.getMiddleName2()) == Boolean.FALSE) {
        prototype.setMiddleName2(StringUtils.stripToNull(source.getMiddleName2()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getDepartment()),
          prototype.getDepartment()) == Boolean.FALSE) {
        prototype.setDepartment(StringUtils.stripToNull(source.getDepartment()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getBuilding()),
          prototype.getBuilding()) == Boolean.FALSE) {
        prototype.setBuilding(StringUtils.stripToNull(source.getBuilding()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getApartment()),
          prototype.getApartment()) == Boolean.FALSE) {
        prototype.setApartment(StringUtils.stripToNull(source.getApartment()));
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getDistrict()),
          prototype.getDistrict()) == Boolean.FALSE) {
        prototype.setDistrict(StringUtils.stripToNull(source.getDistrict()));
      }

      // Country
      if (StringUtils.isBlank(source.getSelectedCountryUuid())) {
        if (prototype.getCountry() != null) {
          prototype.setCountry(null);
        }
      } else {
        Country entity = modelService
            .findOneByUuid(UUID.fromString(source.getSelectedCountryUuid()), Country.class);

        if (entity != null) {

          if (prototype.getCountry() == null
              || prototype.getCountry().equals(entity.getUuid()) == false) {
            prototype.setCountry(entity.getUuid());
          }
        } else {
          throw new ConverterException(
              "Country UUID not found: " + source.getSelectedCountryUuid());
        }
      }

      // Region
      if (StringUtils.isBlank(source.getSelectedRegionUuid())) {
        if (prototype.getRegion() != null) {
          prototype.setRegion(null);
        }
      } else {
        Region entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedRegionUuid()),
            Region.class);

        if (entity != null) {

          if (prototype.getRegion() == null
              || prototype.getRegion().equals(entity.getUuid()) == false) {
            prototype.setRegion(entity.getUuid());
          }
        } else {
          throw new ConverterException("Region UUID not found: " + source.getSelectedRegionUuid());
        }
      }

      // Owner
      if (StringUtils.isBlank(source.getSelectedOwnerUuid())) {
        if (prototype.getOwner() != null) {
          prototype.setOwner(null);
        }
      } else {
        User entity =
            modelService.findOneByUuid(UUID.fromString(source.getSelectedOwnerUuid()), User.class);

        if (entity != null) {

          if (prototype.getOwner() == null
              || prototype.getOwner().equals(entity.getUuid()) == false) {
            prototype.setOwner(entity.getUuid());
          }
        } else {
          throw new ConverterException("Owner UUID not found: " + source.getSelectedOwnerUuid());
        }
      }

      // ShippingAddress
      if (StringUtils.isBlank(source.getSelectedShippingAddressUuid())) {
        if (prototype.getShippingAddress() != null) {
          prototype.setShippingAddress(null);
        }
      } else {
        Address entity = modelService
            .findOneByUuid(UUID.fromString(source.getSelectedShippingAddressUuid()), Address.class);

        if (entity != null) {

          if (prototype.getShippingAddress() == null
              || prototype.getShippingAddress().equals(entity.getUuid()) == false) {
            prototype.setShippingAddress(entity.getUuid());
          }
        } else {
          throw new ConverterException(
              "ShippingAddress UUID not found: " + source.getSelectedShippingAddressUuid());
        }
      }

      // BillingAddress
      if (StringUtils.isBlank(source.getSelectedBillingAddressUuid())) {
        if (prototype.getBillingAddress() != null) {
          prototype.setBillingAddress(null);
        }
      } else {
        Address entity = modelService
            .findOneByUuid(UUID.fromString(source.getSelectedBillingAddressUuid()), Address.class);

        if (entity != null) {

          if (prototype.getBillingAddress() == null
              || prototype.getBillingAddress().equals(entity.getUuid()) == false) {
            prototype.setBillingAddress(entity.getUuid());
          }
        } else {
          throw new ConverterException(
              "BillingAddress UUID not found: " + source.getSelectedBillingAddressUuid());
        }
      }

      // ContactAddress
      if (StringUtils.isBlank(source.getSelectedContactAddressUuid())) {
        if (prototype.getContactAddress() != null) {
          prototype.setContactAddress(null);
        }
      } else {
        Address entity = modelService
            .findOneByUuid(UUID.fromString(source.getSelectedContactAddressUuid()), Address.class);

        if (entity != null) {

          if (prototype.getContactAddress() == null
              || prototype.getContactAddress().equals(entity.getUuid()) == false) {
            prototype.setContactAddress(entity.getUuid());
          }
        } else {
          throw new ConverterException(
              "ContactAddress UUID not found: " + source.getSelectedContactAddressUuid());
        }
      }

      // DefaultPaymentAddress
      if (StringUtils.isBlank(source.getSelectedDefaultPaymentAddressUuid())) {
        if (prototype.getDefaultPaymentAddress() != null) {
          prototype.setDefaultPaymentAddress(null);
        }
      } else {
        Address entity = modelService.findOneByUuid(
            UUID.fromString(source.getSelectedDefaultPaymentAddressUuid()), Address.class);

        if (entity != null) {

          if (prototype.getDefaultPaymentAddress() == null
              || prototype.getDefaultPaymentAddress().equals(entity.getUuid()) == false) {
            prototype.setDefaultPaymentAddress(entity.getUuid());
          }
        } else {
          throw new ConverterException("DefaultPaymentAddress UUID not found: "
              + source.getSelectedDefaultPaymentAddressUuid());
        }
      }

      // DefaultShipmentAddress
      if (StringUtils.isBlank(source.getSelectedDefaultShipmentAddressUuid())) {
        if (prototype.getDefaultShipmentAddress() != null) {
          prototype.setDefaultShipmentAddress(null);
        }
      } else {
        Address entity = modelService.findOneByUuid(
            UUID.fromString(source.getSelectedDefaultShipmentAddressUuid()), Address.class);

        if (entity != null) {

          if (prototype.getDefaultShipmentAddress() == null
              || prototype.getDefaultShipmentAddress().equals(entity.getUuid()) == false) {
            prototype.setDefaultShipmentAddress(entity.getUuid());
          }
        } else {
          throw new ConverterException("DefaultShipmentAddress UUID not found: "
              + source.getSelectedDefaultShipmentAddressUuid());
        }
      }

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}

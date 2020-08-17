package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AddressDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.User;

public class EntityAddressConverter implements EntityConverter<AddressDto, Address> {

	@Autowired
	private ModelService modelService;

	@Override
	public Address convert(AddressDto source, EntityConverterContext context) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Address.UUID, source.getUuid());
				Address prototype = modelService.findOneByProperties(properties, Address.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Address.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Address convertToEntity(AddressDto source, Address prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getStreetName()), prototype.getStreetName()) == Boolean.FALSE) {
				prototype.setStreetName(StringUtils.stripToNull(source.getStreetName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getStreetNumber()), prototype.getStreetNumber()) == Boolean.FALSE) {
				prototype.setStreetNumber(StringUtils.stripToNull(source.getStreetNumber()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getPostalCode()), prototype.getPostalCode()) == Boolean.FALSE) {
				prototype.setPostalCode(StringUtils.stripToNull(source.getPostalCode()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getTown()), prototype.getTown()) == Boolean.FALSE) {
				prototype.setTown(StringUtils.stripToNull(source.getTown()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getCompany()), prototype.getCompany()) == Boolean.FALSE) {
				prototype.setCompany(StringUtils.stripToNull(source.getCompany()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getPhone1()), prototype.getPhone1()) == Boolean.FALSE) {
				prototype.setPhone1(StringUtils.stripToNull(source.getPhone1()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getPhone2()), prototype.getPhone2()) == Boolean.FALSE) {
				prototype.setPhone2(StringUtils.stripToNull(source.getPhone2()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getMobilePhone()), prototype.getMobilePhone()) == Boolean.FALSE) {
				prototype.setMobilePhone(StringUtils.stripToNull(source.getMobilePhone()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getEmail()), prototype.getEmail()) == Boolean.FALSE) {
				prototype.setEmail(StringUtils.stripToNull(source.getEmail()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getPoBox()), prototype.getPoBox()) == Boolean.FALSE) {
				prototype.setPoBox(StringUtils.stripToNull(source.getPoBox()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getFax()), prototype.getFax()) == Boolean.FALSE) {
				prototype.setFax(StringUtils.stripToNull(source.getFax()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getTitle()), prototype.getTitle()) == Boolean.FALSE) {
				prototype.setTitle(StringUtils.stripToNull(source.getTitle()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getLastName()), prototype.getLastName()) == Boolean.FALSE) {
				prototype.setLastName(StringUtils.stripToNull(source.getLastName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getFirstName()), prototype.getFirstName()) == Boolean.FALSE) {
				prototype.setFirstName(StringUtils.stripToNull(source.getFirstName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getMiddleName()), prototype.getMiddleName()) == Boolean.FALSE) {
				prototype.setMiddleName(StringUtils.stripToNull(source.getMiddleName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getMiddleName2()), prototype.getMiddleName2()) == Boolean.FALSE) {
				prototype.setMiddleName2(StringUtils.stripToNull(source.getMiddleName2()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getDepartment()), prototype.getDepartment()) == Boolean.FALSE) {
				prototype.setDepartment(StringUtils.stripToNull(source.getDepartment()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getBuilding()), prototype.getBuilding()) == Boolean.FALSE) {
				prototype.setBuilding(StringUtils.stripToNull(source.getBuilding()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getApartment()), prototype.getApartment()) == Boolean.FALSE) {
				prototype.setApartment(StringUtils.stripToNull(source.getApartment()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getDistrict()), prototype.getDistrict()) == Boolean.FALSE) {
				prototype.setDistrict(StringUtils.stripToNull(source.getDistrict()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Country
			if (StringUtils.isBlank(source.getSelectedCountry())) {
				if (prototype.getCountry() != null) {
					prototype.setCountry(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Country entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedCountry()), Country.class);

				if (entity != null) {

					if (prototype.getCountry() == null || prototype.getCountry().getUuid().equals(entity.getUuid()) == false) {
						prototype.setCountry(entity);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("Country UUID not found: " + source.getSelectedCountry());
				}
			}

			// Region
			if (StringUtils.isBlank(source.getSelectedRegion())) {
				if (prototype.getRegion() != null) {
					prototype.setRegion(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Region entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedRegion()), Region.class);

				if (entity != null) {

					if (prototype.getRegion() == null || prototype.getRegion().getUuid().equals(entity.getUuid()) == false) {
						prototype.setRegion(entity);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("Region UUID not found: " + source.getSelectedRegion());
				}
			}

			// Owner
			if (StringUtils.isBlank(source.getSelectedOwner())) {
				if (prototype.getOwner() != null) {
					prototype.setOwner(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				User entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedOwner()), User.class);

				if (entity != null) {

					if (prototype.getOwner() == null || prototype.getOwner().getUuid().equals(entity.getUuid()) == false) {
						prototype.setOwner(entity);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("Owner UUID not found: " + source.getSelectedOwner());
				}
			}

			// ShippingAddress
			if (StringUtils.isBlank(source.getSelectedShippingAddress())) {
				if (prototype.getShippingAddress() != null) {
					prototype.setShippingAddress(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Address entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedShippingAddress()), Address.class);

				if (entity != null) {

					if (prototype.getShippingAddress() == null || prototype.getShippingAddress().getUuid().equals(entity.getUuid()) == false) {
						prototype.setShippingAddress(entity);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("ShippingAddress UUID not found: " + source.getSelectedShippingAddress());
				}
			}

			// BillingAddress
			if (StringUtils.isBlank(source.getSelectedBillingAddress())) {
				if (prototype.getBillingAddress() != null) {
					prototype.setBillingAddress(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Address entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedBillingAddress()), Address.class);

				if (entity != null) {

					if (prototype.getBillingAddress() == null || prototype.getBillingAddress().getUuid().equals(entity.getUuid()) == false) {
						prototype.setBillingAddress(entity);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("BillingAddress UUID not found: " + source.getSelectedBillingAddress());
				}
			}

			// ContactAddress
			if (StringUtils.isBlank(source.getSelectedContactAddress())) {
				if (prototype.getContactAddress() != null) {
					prototype.setContactAddress(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Address entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedContactAddress()), Address.class);

				if (entity != null) {

					if (prototype.getContactAddress() == null || prototype.getContactAddress().getUuid().equals(entity.getUuid()) == false) {
						prototype.setContactAddress(entity);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("ContactAddress UUID not found: " + source.getSelectedContactAddress());
				}
			}

			// DefaultPaymentAddress
			if (StringUtils.isBlank(source.getSelectedDefaultPaymentAddress())) {
				if (prototype.getDefaultPaymentAddress() != null) {
					prototype.setDefaultPaymentAddress(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Address entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedDefaultPaymentAddress()), Address.class);

				if (entity != null) {

					if (prototype.getDefaultPaymentAddress() == null || prototype.getDefaultPaymentAddress().getUuid().equals(entity.getUuid()) == false) {
						prototype.setDefaultPaymentAddress(entity);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("DefaultPaymentAddress UUID not found: " + source.getSelectedDefaultPaymentAddress());
				}
			}

			// DefaultShipmentAddress
			if (StringUtils.isBlank(source.getSelectedDefaultShipmentAddress())) {
				if (prototype.getDefaultShipmentAddress() != null) {
					prototype.setDefaultShipmentAddress(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Address entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedDefaultShipmentAddress()), Address.class);

				if (entity != null) {

					if (prototype.getDefaultShipmentAddress() == null || prototype.getDefaultShipmentAddress().getUuid().equals(entity.getUuid()) == false) {
						prototype.setDefaultShipmentAddress(entity);
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("DefaultShipmentAddress UUID not found: " + source.getSelectedDefaultShipmentAddress());
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

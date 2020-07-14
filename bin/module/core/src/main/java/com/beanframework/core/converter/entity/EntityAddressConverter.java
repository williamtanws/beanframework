package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.address.domain.Address;
import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AddressDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
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
			
			if (StringUtils.equals(StringUtils.stripToNull(source.getPoBox()), prototype.getPoBox()) == Boolean.FALSE) {
				prototype.setPoBox(StringUtils.stripToNull(source.getPoBox()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}
			
			if (StringUtils.equals(StringUtils.stripToNull(source.getFax()), prototype.getFax()) == Boolean.FALSE) {
				prototype.setFax(StringUtils.stripToNull(source.getFax()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}
			
			if (StringUtils.equals(StringUtils.stripToNull(source.getFax()), prototype.getFax()) == Boolean.FALSE) {
				prototype.setFax(StringUtils.stripToNull(source.getFax()));
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

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

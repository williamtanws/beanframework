package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.User;

public class EntityCompanyConverter implements EntityConverter<CompanyDto, Company> {

	@Autowired
	private ModelService modelService;

	@Override
	public Company convert(CompanyDto source) throws ConverterException {

		try {
			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Company.UUID, source.getUuid());
				Company prototype = modelService.findOneByProperties(properties, Company.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Company.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	private Company convertToEntity(CompanyDto source, Company prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getDescription()), prototype.getDescription()) == Boolean.FALSE) {
				prototype.setDescription(StringUtils.stripToNull(source.getDescription()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getLineOfBusiness() == source.getLineOfBusiness() == Boolean.FALSE) {
				prototype.setLineOfBusiness(source.getLineOfBusiness());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getBuyer() == source.getBuyer() == Boolean.FALSE) {
				prototype.setBuyer(source.getBuyer());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getManufacturer() == source.getManufacturer() == Boolean.FALSE) {
				prototype.setManufacturer(source.getManufacturer());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getSupplier() == source.getSupplier() == Boolean.FALSE) {
				prototype.setSupplier(source.getSupplier());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (prototype.getCarrier() == source.getCarrier() == Boolean.FALSE) {
				prototype.setCarrier(source.getCarrier());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getVatId()), prototype.getVatId()) == Boolean.FALSE) {
				prototype.setVatId(StringUtils.stripToNull(source.getVatId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getDunsId()), prototype.getDunsId()) == Boolean.FALSE) {
				prototype.setDunsId(StringUtils.stripToNull(source.getDunsId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getIlnId()), prototype.getIlnId()) == Boolean.FALSE) {
				prototype.setIlnId(StringUtils.stripToNull(source.getIlnId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getBuyerSpecificId()), prototype.getBuyerSpecificId()) == Boolean.FALSE) {
				prototype.setBuyerSpecificId(StringUtils.stripToNull(source.getBuyerSpecificId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getSupplierSpecificId()), prototype.getSupplierSpecificId()) == Boolean.FALSE) {
				prototype.setSupplierSpecificId(StringUtils.stripToNull(source.getSupplierSpecificId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Company
			if (StringUtils.isBlank(source.getSelectedResponsibleCompany())) {
				if (prototype.getResponsibleCompany() != null) {
					prototype.setResponsibleCompany(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Company entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedResponsibleCompany()), Company.class);

				if (entity != null) {

					if (prototype.getResponsibleCompany() == null || prototype.getResponsibleCompany().equals(entity.getUuid()) == false) {
						prototype.setResponsibleCompany(entity.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("Company UUID not found: " + source.getSelectedResponsibleCompany());
				}
			}

			// ContactPerson
			if (StringUtils.isBlank(source.getSelectedContactPerson())) {
				if (prototype.getContactPerson() != null) {
					prototype.setContactPerson(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				User entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedContactPerson()), User.class);

				if (entity != null) {

					if (prototype.getContactPerson() == null || prototype.getContactPerson().equals(entity.getUuid()) == false) {
						prototype.setContactPerson(entity.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("ContactPerson UUID not found: " + source.getSelectedContactPerson());
				}
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

					if (prototype.getCountry() == null || prototype.getCountry().equals(entity.getUuid()) == false) {
						prototype.setCountry(entity.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("Country UUID not found: " + source.getSelectedCountry());
				}
			}

			// Addresses
			if (source.getSelectedAddresses() != null) {

				Iterator<UUID> it = prototype.getAddresses().iterator();
				while (it.hasNext()) {
					UUID o = it.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedAddresses().length; i++) {
						if (o.equals(UUID.fromString(source.getSelectedAddresses()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						it.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedAddresses().length; i++) {

					boolean add = true;
					it = prototype.getAddresses().iterator();
					while (it.hasNext()) {
						UUID o = it.next();

						if (o.equals(UUID.fromString(source.getSelectedAddresses()[i]))) {
							add = false;
						}
					}

					if (add) {
						Address entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedAddresses()[i]), Address.class);
						if (entity != null) {
							prototype.getAddresses().add(entity.getUuid());
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			} else if (prototype.getAddresses() != null && prototype.getAddresses().isEmpty() == false) {
				prototype.setAddresses(null);
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

					if (prototype.getShippingAddress() == null || prototype.getShippingAddress().equals(entity.getUuid()) == false) {
						prototype.setShippingAddress(entity.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("ShippingAddress UUID not found: " + source.getSelectedShippingAddress());
				}
			}

			// UnloadingAddress
			if (StringUtils.isBlank(source.getSelectedUnloadingAddress())) {
				if (prototype.getUnloadingAddress() != null) {
					prototype.setUnloadingAddress(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				Address entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedUnloadingAddress()), Address.class);

				if (entity != null) {

					if (prototype.getUnloadingAddress() == null || prototype.getUnloadingAddress().equals(entity.getUuid()) == false) {
						prototype.setUnloadingAddress(entity.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("UnloadingAddress UUID not found: " + source.getSelectedUnloadingAddress());
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

					if (prototype.getBillingAddress() == null || prototype.getBillingAddress().equals(entity.getUuid()) == false) {
						prototype.setBillingAddress(entity.getUuid());
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

					if (prototype.getContactAddress() == null || prototype.getContactAddress().equals(entity.getUuid()) == false) {
						prototype.setContactAddress(entity.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				} else {
					throw new ConverterException("ContactAddress UUID not found: " + source.getSelectedContactAddress());
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

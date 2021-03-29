package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.Iterator;
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
				Company prototype = modelService.findOneByUuid(source.getUuid(), Company.class);

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

			// ResponsibleCompany
			if (StringUtils.isBlank(source.getSelectedResponsibleCompanyUuid())) {
				prototype.setResponsibleCompany(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				Company entityResponsibleCompany = modelService.findOneByUuid(UUID.fromString(source.getSelectedResponsibleCompanyUuid()), Company.class);

				if (entityResponsibleCompany != null) {

					if (prototype.getResponsibleCompany() == null || prototype.getResponsibleCompany().equals(entityResponsibleCompany.getUuid()) == Boolean.FALSE) {
						prototype.setResponsibleCompany(entityResponsibleCompany.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

			// ContactPerson
			if (StringUtils.isBlank(source.getSelectedContactPersonUuid())) {
				prototype.setContactPerson(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				User entityUser = modelService.findOneByUuid(UUID.fromString(source.getSelectedContactPersonUuid()), User.class);

				if (entityUser != null) {

					if (prototype.getContactPerson() == null || prototype.getContactPerson().equals(entityUser.getUuid()) == Boolean.FALSE) {
						prototype.setContactPerson(entityUser.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

			// Country
			if (StringUtils.isBlank(source.getSelectedCountryUuid())) {
				prototype.setCountry(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				Country entityCountry = modelService.findOneByUuid(UUID.fromString(source.getSelectedCountryUuid()), Country.class);

				if (entityCountry != null) {

					if (prototype.getCountry() == null || prototype.getCountry().equals(entityCountry.getUuid()) == Boolean.FALSE) {
						prototype.setCountry(entityCountry.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

			// Addresses
			if (source.getSelectedAddressUuids() != null) {

				Iterator<UUID> enumerationsIterator = prototype.getAddresses().iterator();
				while (enumerationsIterator.hasNext()) {
					UUID enumeration = enumerationsIterator.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedAddressUuids().length; i++) {
						if (enumeration.equals(UUID.fromString(source.getSelectedAddressUuids()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						enumerationsIterator.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedAddressUuids().length; i++) {

					boolean add = true;
					enumerationsIterator = prototype.getAddresses().iterator();
					while (enumerationsIterator.hasNext()) {
						UUID enumeration = enumerationsIterator.next();

						if (enumeration.equals(UUID.fromString(source.getSelectedAddressUuids()[i]))) {
							add = false;
						}
					}

					if (add) {
						Address enumeration = modelService.findOneByUuid(UUID.fromString(source.getSelectedAddressUuids()[i]), Address.class);
						if (enumeration != null) {
							prototype.getAddresses().add(enumeration.getUuid());
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			} else if (prototype.getAddresses() != null && prototype.getAddresses().isEmpty() == false) {
				for (final Iterator<UUID> itr = prototype.getAddresses().iterator(); itr.hasNext();) {
					itr.next();
					itr.remove();
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// ShippingAddress
			if (StringUtils.isBlank(source.getSelectedShippingAddressUuid())) {
				prototype.setShippingAddress(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				Address entityShippingAddress = modelService.findOneByUuid(UUID.fromString(source.getSelectedShippingAddressUuid()), Address.class);

				if (entityShippingAddress != null) {

					if (prototype.getShippingAddress() == null || prototype.getShippingAddress().equals(entityShippingAddress.getUuid()) == Boolean.FALSE) {
						prototype.setShippingAddress(entityShippingAddress.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

			// UnloadingAddress
			if (StringUtils.isBlank(source.getSelectedUnloadingAddressUuid())) {
				prototype.setUnloadingAddress(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				Address entityUnloadingAddress = modelService.findOneByUuid(UUID.fromString(source.getSelectedUnloadingAddressUuid()), Address.class);

				if (entityUnloadingAddress != null) {

					if (prototype.getUnloadingAddress() == null || prototype.getUnloadingAddress().equals(entityUnloadingAddress.getUuid()) == Boolean.FALSE) {
						prototype.setUnloadingAddress(entityUnloadingAddress.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

			// BillingAddress
			if (StringUtils.isBlank(source.getSelectedBillingAddressUuid())) {
				prototype.setBillingAddress(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				Address entityBillingAddress = modelService.findOneByUuid(UUID.fromString(source.getSelectedBillingAddressUuid()), Address.class);

				if (entityBillingAddress != null) {

					if (prototype.getBillingAddress() == null || prototype.getBillingAddress().equals(entityBillingAddress.getUuid()) == Boolean.FALSE) {
						prototype.setBillingAddress(entityBillingAddress.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}

			// ContactAddress
			if (StringUtils.isBlank(source.getSelectedContactAddressUuid())) {
				prototype.setContactAddress(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			} else {
				Address entityContactAddress = modelService.findOneByUuid(UUID.fromString(source.getSelectedContactAddressUuid()), Address.class);

				if (entityContactAddress != null) {

					if (prototype.getContactAddress() == null || prototype.getContactAddress().equals(entityContactAddress.getUuid()) == Boolean.FALSE) {
						prototype.setContactAddress(entityContactAddress.getUuid());
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

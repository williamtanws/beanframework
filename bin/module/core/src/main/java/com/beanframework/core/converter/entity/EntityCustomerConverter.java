package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.Customer;
import com.beanframework.user.domain.UserGroup;

public class EntityCustomerConverter implements EntityConverter<CustomerDto, Customer> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Customer convert(CustomerDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Customer.UUID, source.getUuid());
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

	private Customer convertToEntity(CustomerDto source, Customer prototype) throws ConverterException {

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

			if (source.getAccountNonExpired() == null) {
				if (prototype.getAccountNonExpired() != null) {
					prototype.setAccountNonExpired(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getAccountNonExpired() == null || prototype.getAccountNonExpired().equals(source.getAccountNonExpired()) == Boolean.FALSE) {
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
				if (prototype.getAccountNonLocked() == null || prototype.getAccountNonLocked().equals(source.getAccountNonLocked()) == Boolean.FALSE) {
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
				if (prototype.getCredentialsNonExpired() == null || prototype.getCredentialsNonExpired().equals(source.getCredentialsNonExpired()) == Boolean.FALSE) {
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
				if (prototype.getEnabled() == null || prototype.getEnabled().equals(source.getEnabled()) == Boolean.FALSE) {
					prototype.setEnabled(source.getEnabled());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (StringUtils.isNotBlank(source.getPassword())) {
				prototype.setPassword(passwordEncoder.encode(source.getPassword()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == Boolean.FALSE) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserFieldDto sourceField : source.getFields()) {

						if (prototype.getFields().get(i).getDynamicFieldSlot().equals(sourceField.getDynamicFieldSlot().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == Boolean.FALSE) {
								prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}

			// User Groups
			if (source.getSelectedUserGroups() != null) {

				Iterator<UUID> itr = prototype.getUserGroups().iterator();
				while (itr.hasNext()) {
					UUID model = itr.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedUserGroups().length; i++) {
						if (model.equals(UUID.fromString(source.getSelectedUserGroups()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						itr.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedUserGroups().length; i++) {

					boolean add = true;
					itr = prototype.getUserGroups().iterator();
					while (itr.hasNext()) {
						UUID model = itr.next();

						if (model.equals(UUID.fromString(source.getSelectedUserGroups()[i]))) {
							add = false;
						}
					}

					if (add) {
						UserGroup entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedUserGroups()[i]), UserGroup.class);
						if (entity != null) {
							prototype.getUserGroups().add(entity.getUuid());
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			} else if (prototype.getUserGroups() != null && prototype.getUserGroups().isEmpty() == false) {
				for (final Iterator<UUID> itr = prototype.getUserGroups().iterator(); itr.hasNext();) {
					itr.next();
					itr.remove();
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			// Companies
			if (source.getSelectedCompanies() != null) {

				Iterator<UUID> itr = prototype.getCompanies().iterator();
				while (itr.hasNext()) {
					UUID model = itr.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedCompanies().length; i++) {
						if (model.equals(UUID.fromString(source.getSelectedCompanies()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						itr.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedCompanies().length; i++) {

					boolean add = true;
					itr = prototype.getCompanies().iterator();
					while (itr.hasNext()) {
						UUID model = itr.next();

						if (model.equals(UUID.fromString(source.getSelectedCompanies()[i]))) {
							add = false;
						}
					}

					if (add) {
						Company entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedCompanies()[i]), Company.class);
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
						Address entity = modelService.findOneByUuid(o, Address.class);
						entity.setOwner(null);
						modelService.saveEntity(entity);
						it.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedAddresses().length; i++) {

					boolean add = true;
					it = prototype.getAddresses().iterator();
					while (it.hasNext()) {
						UUID entity = it.next();

						if (entity.equals(UUID.fromString(source.getSelectedAddresses()[i]))) {
							add = false;
						}
					}

					if (add) {
						Address entity = modelService.findOneByUuid(UUID.fromString(source.getSelectedAddresses()[i]), Address.class);
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

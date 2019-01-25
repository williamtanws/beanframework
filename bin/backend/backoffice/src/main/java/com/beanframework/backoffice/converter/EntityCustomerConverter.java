package com.beanframework.backoffice.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.backoffice.data.CustomerDto;
import com.beanframework.backoffice.data.UserFieldDto;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class EntityCustomerConverter implements EntityConverter<CustomerDto, Customer> {

	@Autowired
	private ModelService modelService;

	@Override
	public Customer convert(CustomerDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Customer.UUID, source.getUuid());
				Customer prototype = modelService.findOneEntityByProperties(properties, true, Customer.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Customer.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private Customer convert(CustomerDto source, Customer prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getAccountNonExpired() == null) {
				if (prototype.getAccountNonExpired() != null) {
					prototype.setAccountNonExpired(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getAccountNonExpired() == null || prototype.getAccountNonExpired().equals(source.getAccountNonExpired()) == false) {
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
				if (prototype.getAccountNonLocked() == null || prototype.getAccountNonLocked().equals(source.getAccountNonLocked()) == false) {
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
				if (prototype.getCredentialsNonExpired() == null || prototype.getCredentialsNonExpired().equals(source.getCredentialsNonExpired()) == false) {
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
				if (prototype.getEnabled() == null || prototype.getEnabled().equals(source.getEnabled()) == false) {
					prototype.setEnabled(source.getEnabled());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (StringUtils.isNotBlank(source.getPassword())) {
				prototype.setPassword(PasswordUtils.encode(source.getPassword()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserFieldDto sourceField : source.getFields()) {
						if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
							prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

							prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}

			// UserGroup
			if (source.getTableUserGroups() != null) {

				for (int i = 0; i < source.getTableUserGroups().length; i++) {

					boolean remove = true;
					if (source.getTableSelectedUserGroups() != null && source.getTableSelectedUserGroups().length > i && BooleanUtils.parseBoolean(source.getTableSelectedUserGroups()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<UserGroup> userGroupsIterator = prototype.getUserGroups().listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(source.getTableUserGroups()[i]))) {
								userGroupsIterator.remove();
							}
						}
					} else {
						boolean add = true;
						for (Iterator<UserGroup> userGroupsIterator = prototype.getUserGroups().listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(source.getTableUserGroups()[i]))) {
								add = false;
							}
						}

						if (add) {
							UserGroup entityUserGroups = modelService.findOneEntityByUuid(UUID.fromString(source.getTableUserGroups()[i]), false, UserGroup.class);
							prototype.getUserGroups().add(entityUserGroups);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

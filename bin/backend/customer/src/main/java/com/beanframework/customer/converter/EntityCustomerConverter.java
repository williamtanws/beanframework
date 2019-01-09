package com.beanframework.customer.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

public class EntityCustomerConverter implements EntityConverter<Customer, Customer> {

	@Autowired
	private ModelService modelService;

	@Override
	public Customer convert(Customer source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Customer.UUID, source.getUuid());
				Customer prototype = modelService.findOneEntityByProperties(properties, Customer.class);

				if (prototype != null) {
					return convert(source, prototype);
				}
			}

			return convert(source, modelService.create(Customer.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

	}

	private Customer convert(Customer source, Customer prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false) {
				prototype.setId(StringUtils.strip(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getAccountNonExpired() != null && source.getAccountNonExpired() != prototype.getAccountNonExpired()) {
				prototype.setAccountNonExpired(source.getAccountNonExpired());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getAccountNonLocked() != null && source.getAccountNonLocked() != prototype.getAccountNonLocked()) {
				prototype.setAccountNonLocked(source.getAccountNonLocked());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getCredentialsNonExpired() != null && source.getCredentialsNonExpired() != prototype.getCredentialsNonExpired()) {
				prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getEnabled() != null && source.getEnabled() != prototype.getEnabled()) {
				prototype.setEnabled(source.getEnabled());
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.isNotBlank(source.getPassword())) {
				prototype.setPassword(PasswordUtils.encode(source.getPassword()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(source.getName(), prototype.getName()) == false) {
				prototype.setName(StringUtils.strip(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserField sourceField : source.getFields()) {
						if (StringUtils.equals(StringUtils.strip(sourceField.getValue()), prototype.getFields().get(i).getValue()) == false) {
							prototype.getFields().get(i).setValue(StringUtils.strip(sourceField.getValue()));

							prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}

			// User Group
			if (source.getUserGroups() == null || source.getUserGroups().isEmpty())
				prototype.setUserGroups(new ArrayList<UserGroup>());

			Iterator<UserGroup> itr = prototype.getUserGroups().iterator();
			while (itr.hasNext()) {
				UserGroup userGroup = itr.next();

				boolean remove = true;
				for (UserGroup sourceUserGroup : source.getUserGroups()) {
					if (userGroup.getUuid().equals(sourceUserGroup.getUuid())) {
						remove = false;
					}
				}

				if (remove) {
					itr.remove();
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			for (UserGroup sourceUserGroup : source.getUserGroups()) {

				boolean add = true;
				for (UserGroup userGroup : prototype.getUserGroups()) {
					if (sourceUserGroup.getUuid().equals(userGroup.getUuid()))
						add = false;
				}

				if (add) {
					UserGroup entityUserGroup = modelService.findOneEntityByUuid(sourceUserGroup.getUuid(), UserGroup.class);
					if (entityUserGroup != null) {
						prototype.getUserGroups().add(entityUserGroup);
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

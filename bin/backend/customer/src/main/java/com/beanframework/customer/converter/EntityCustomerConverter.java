package com.beanframework.customer.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

		Customer prototype;
		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Customer.UUID, source.getUuid());
				Customer exists = modelService.findOneEntityByProperties(properties, Customer.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(Customer.class);
				}
			} else {
				prototype = modelService.create(Customer.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private Customer convert(Customer source, Customer prototype) throws ConverterException {

		try {
			prototype.setLastModifiedDate(new Date());
			
			if (StringUtils.isNotBlank(source.getId()) && StringUtils.equals(source.getId(), prototype.getId()) == false)
				prototype.setId(StringUtils.strip(source.getId()));

			if (source.getAccountNonExpired() != null && source.getAccountNonExpired() != prototype.getAccountNonExpired())
				prototype.setAccountNonExpired(source.getAccountNonExpired());

			if (source.getAccountNonLocked() != null && source.getAccountNonLocked() != prototype.getAccountNonLocked())
				prototype.setAccountNonLocked(source.getAccountNonLocked());

			if (source.getCredentialsNonExpired() != null && source.getCredentialsNonExpired() != prototype.getCredentialsNonExpired())
				prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());

			if (source.getEnabled() != null && source.getEnabled() != prototype.getEnabled())
				prototype.setEnabled(source.getEnabled());

			if (StringUtils.isNotBlank(source.getPassword()))
				prototype.setPassword(PasswordUtils.encode(source.getPassword()));

			if (StringUtils.equals(source.getName(), prototype.getName()) == false)
				prototype.setName(StringUtils.strip(source.getName()));

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == false) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserField sourceUserField : source.getFields()) {
						if (prototype.getFields().get(i).getUuid().equals(sourceUserField.getUuid())) {
							prototype.getFields().get(i).setValue(StringUtils.strip(sourceUserField.getValue()));
						}
					}
				}
			}

			// User Group
			if (source.getUserGroups() == null || source.getUserGroups().isEmpty()) {
				prototype.setUserGroups(new ArrayList<UserGroup>());
			} else {
				List<UserGroup> childs = new ArrayList<UserGroup>();
				for (UserGroup userGroup : source.getUserGroups()) {
					UserGroup entityUserGroup = modelService.findOneEntityByUuid(userGroup.getUuid(), UserGroup.class);
					if (entityUserGroup != null)
						childs.add(entityUserGroup);
				}
				prototype.setUserGroups(childs);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}

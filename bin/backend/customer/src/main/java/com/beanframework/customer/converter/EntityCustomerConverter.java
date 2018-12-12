package com.beanframework.customer.converter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupService;
import com.beanframework.user.utils.PasswordUtils;

@Component
public class EntityCustomerConverter implements Converter<Customer, Customer> {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserGroupService userGroupService;

	@Override
	public Customer convert(Customer source) {

		Optional<Customer> prototype = Optional.of(customerService.create());
		if (source.getUuid() != null) {
			Optional<Customer> exists = customerService.findEntityByUuid(source.getUuid());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}
		else if (StringUtils.isNotEmpty(source.getId())) {
			Optional<Customer> exists = customerService.findEntityById(source.getId());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}

		return convert(source, prototype.get());
	}

	private Customer convert(Customer source, Customer prototype) {

		prototype.setId(source.getId());
		prototype.setAccountNonExpired(source.isAccountNonExpired());
		prototype.setAccountNonLocked(source.isAccountNonLocked());
		prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
		prototype.setEnabled(source.isEnabled());
		prototype.setLastModifiedDate(new Date());
		
		if (StringUtils.isNotEmpty(source.getPassword())) {
			prototype.setPassword(PasswordUtils.encode(source.getPassword()));
		}
		
		Hibernate.initialize(prototype.getUserGroups());
		prototype.getUserGroups().clear();
		for (UserGroup userGroup : source.getUserGroups()) {
			if(userGroup.getUuid() != null) {
				Optional<UserGroup> existingUserGroup = userGroupService.findEntityByUuid(userGroup.getUuid());
				if (existingUserGroup.isPresent()) {
					prototype.getUserGroups().add(existingUserGroup.get());
				}
			}
			else if(StringUtils.isNotEmpty(userGroup.getId())) {
				Optional<UserGroup> existingUserGroup = userGroupService.findEntityById(userGroup.getId());
				if (existingUserGroup.isPresent()) {
					prototype.getUserGroups().add(existingUserGroup.get());
				}
			}
		}

		return prototype;
	}

}

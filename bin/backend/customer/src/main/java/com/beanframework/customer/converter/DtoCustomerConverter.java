package com.beanframework.customer.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;
import com.beanframework.user.converter.DtoUserGroupConverter;

@Component
public class DtoCustomerConverter implements DtoConverter<Customer, Customer> {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private DtoUserGroupConverter dtoUserGroupConverter;

	@Override
	public Customer convert(Customer source) {
		return convert(source, customerService.create());
	}

	public List<Customer> convert(List<Customer> sources) {
		List<Customer> convertedList = new ArrayList<Customer>();
		for (Customer source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Customer convert(Customer source, Customer prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setAccountNonExpired(source.isAccountNonExpired());
		prototype.setAccountNonLocked(source.isAccountNonLocked());
		prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
		prototype.setEnabled(source.isEnabled());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());
		
		Hibernate.initialize(source.getUserGroups());
		prototype.setUserGroups(dtoUserGroupConverter.convert(source.getUserGroups()));

		return prototype;
	}

}

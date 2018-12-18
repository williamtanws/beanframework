package com.beanframework.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;

@Component
public class CustomerFacadeImpl implements CustomerFacade {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private CustomerService customerService;

	@Override
	public Customer getCurrentCustomer() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Customer customer = (Customer) auth.getPrincipal();
			return customer;
		} else {
			return null;
		}
	}

	@Override
	public Customer authenticate(String id, String password) throws Exception {
		Customer customer = customerService.authenticate(id, password);

		if (customer == null) {
			throw new BadCredentialsException("Bad Credentials");
		}
		return modelService.getDto(customer);
	}
}

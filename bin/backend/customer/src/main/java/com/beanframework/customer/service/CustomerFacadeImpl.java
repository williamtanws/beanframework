package com.beanframework.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.domain.CustomerSpecification;

@Component
public class CustomerFacadeImpl implements CustomerFacade {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private CustomerService customerService;

	@Override
	public Page<Customer> page(Customer customer, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		Page<Customer> customerPage = modelService.findPage(CustomerSpecification.findByCriteria(customer), pageRequest, Customer.class);
		
		List<Customer> content = modelService.getDto(customerPage.getContent());
		return new PageImpl<Customer>(content, customerPage.getPageable(), customerPage.getTotalElements());
	}

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
	public Customer authenticate(String id, String password) {
		Customer customer = customerService.authenticate(id, password);

		if (customer == null) {
			throw new BadCredentialsException("Bad Credentials");
		}
		return modelService.getDto(customer);
	}
}

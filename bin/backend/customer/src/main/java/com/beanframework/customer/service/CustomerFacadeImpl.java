package com.beanframework.customer.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
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
		return modelService.getDto(customer, Customer.class);
	}

	@Override
	public Page<Customer> findPage(Specification<Customer> specification, PageRequest pageable) throws Exception {
		return modelService.findDtoPage(specification, pageable, Customer.class);
	}

	@Override
	public Customer create() throws Exception {
		return modelService.create(Customer.class);
	}

	@Override
	public Customer findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findDtoByProperties(properties, Customer.class);
	}

	@Override
	public void createDto(Customer model) throws BusinessException {
		modelService.saveDto(model, Customer.class);
	}

	@Override
	public void updateDto(Customer model) throws BusinessException {
		modelService.saveDto(model, Customer.class);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.delete(uuid, Customer.class);
	}
}

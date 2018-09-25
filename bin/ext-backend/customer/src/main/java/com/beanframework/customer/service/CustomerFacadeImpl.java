package com.beanframework.customer.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.validator.DeleteCustomerValidator;
import com.beanframework.customer.validator.SaveCustomerValidator;

@Component
public class CustomerFacadeImpl implements CustomerFacade {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SaveCustomerValidator saveCustomerValidator;

	@Autowired
	private DeleteCustomerValidator deleteCustomerValidator;

	@Override
	public Customer create() {
		return customerService.create();
	}

	@Override
	public Customer initDefaults(Customer customer) {
		return customerService.initDefaults(customer);
	}

	@Override
	public Customer save(Customer customer, Errors bindingResult) {
		saveCustomerValidator.validate(customer, bindingResult);

		if (bindingResult.hasErrors()) {
			return customer;
		}

		return customerService.save(customer);
	}
	
	
	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteCustomerValidator.validate(uuid, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			customerService.delete(uuid);
		}
	}

	@Override
	public void delete(String id, Errors bindingResult) {
		deleteCustomerValidator.validate(id, bindingResult);
		
		if (bindingResult.hasErrors() == false) {
			customerService.deleteById(id);
		}
	}

	@Override
	public void deleteAll() {
		customerService.deleteAll();
	}

	@Override
	public Customer findByUuid(UUID uuid) {
		return customerService.findByUuid(uuid);
	}

	@Override
	public Customer findById(String id) {
		return customerService.findById(id);
	}
	
	@Override
	public boolean existsById(String id) {
		return customerService.existsById(id);
	}

	@Override
	public Page<Customer> page(Customer customer, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return customerService.page(customer, pageRequest);
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
		return customer;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public SaveCustomerValidator getSaveCustomerValidator() {
		return saveCustomerValidator;
	}

	public void setSaveCustomerValidator(SaveCustomerValidator saveCustomerValidator) {
		this.saveCustomerValidator = saveCustomerValidator;
	}

	public DeleteCustomerValidator getDeleteCustomerValidator() {
		return deleteCustomerValidator;
	}

	public void setDeleteCustomerValidator(DeleteCustomerValidator deleteCustomerValidator) {
		this.deleteCustomerValidator = deleteCustomerValidator;
	}

}

package com.beanframework.customer.validator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.customer.CustomerConstants;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;
import com.beanframework.common.service.LocaleMessageService;

@Component
public class DeleteCustomerValidator implements Validator {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private LocaleMessageService localMessageService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class == clazz;
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		
		if(target instanceof UUID) {
			UUID uuid = (UUID) target;

			Customer customer = customerService.findByUuid(uuid);
			if(customer == null) {
				errors.reject(Customer.UUID, localMessageService.getMessage(CustomerConstants.Locale.UUID_NOT_EXISTS));
			}
		}
		
		if(target instanceof String) {
			String id = (String) target;

			boolean existsCustomer = customerService.existsById(id);
			if(existsCustomer == false) {
				errors.reject(Customer.ID, localMessageService.getMessage(CustomerConstants.Locale.ID_NOT_EXISTS));
			}
		}
		
		
	}

}

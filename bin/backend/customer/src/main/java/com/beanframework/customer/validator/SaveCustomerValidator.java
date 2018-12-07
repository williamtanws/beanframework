package com.beanframework.customer.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.beanframework.customer.CustomerConstants;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;
import com.beanframework.common.service.LocaleMessageService;

@Component
public class SaveCustomerValidator implements Validator {

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
		final Customer customer = (Customer) target;

		if (customer.getUuid() == null) {
			// Save new
			if (StringUtils.isEmpty(customer.getId())) {
				errors.reject(Customer.ID, localMessageService.getMessage(CustomerConstants.Locale.ID_REQUIRED));
			} else if (StringUtils.isEmpty(customer.getPassword())) {
				errors.reject(Customer.PASSWORD, localMessageService.getMessage(CustomerConstants.Locale.PASSWORD_REQUIRED));
			} else {
				boolean existsCustomer = customerService.existsById(customer.getId());
				if (existsCustomer) {
					errors.reject(Customer.ID, localMessageService.getMessage(CustomerConstants.Locale.ID_EXISTS));
				}
			}

		} else {
			// Update exists
			if (StringUtils.isNotEmpty(customer.getId())) {
				Customer existsCustomer = customerService.findById(customer.getId());
				if (existsCustomer != null) {
					if (!customer.getUuid().equals(existsCustomer.getUuid())) {
						errors.reject(Customer.ID, localMessageService.getMessage(CustomerConstants.Locale.ID_EXISTS));
					}
				}
			}
		}
	}

}

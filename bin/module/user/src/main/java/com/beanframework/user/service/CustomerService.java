package com.beanframework.user.service;

import com.beanframework.user.domain.Customer;

public interface CustomerService {

	Customer updatePrincipal(Customer model);

	Customer getCurrentUser() throws Exception;
}

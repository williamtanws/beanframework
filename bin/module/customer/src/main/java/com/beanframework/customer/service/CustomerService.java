package com.beanframework.customer.service;

import java.util.List;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.customer.domain.Customer;

public interface CustomerService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	Customer updatePrincipal(Customer model);

	Customer getCurrentUser() throws Exception;
}

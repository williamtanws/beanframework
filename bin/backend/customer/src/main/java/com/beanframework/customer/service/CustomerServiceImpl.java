package com.beanframework.customer.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ModelService modelService;

	@Override
	public Customer create() throws Exception {
		return modelService.create(Customer.class);
	}

	@Override
	public Customer saveEntity(Customer model) throws BusinessException {
		return (Customer) modelService.saveEntity(model, Customer.class);
	}

	@Override
	public void deleteById(String id) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Customer.ID, id);
			Customer model = modelService.findOneEntityByProperties(properties, Customer.class);
			modelService.deleteByEntity(model, Customer.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}

package com.beanframework.core.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.InterceptorContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.specification.CustomerSpecification;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;

@Component
public class CustomerFacadeImpl implements CustomerFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CustomerService customerService;

	@Override
	public CustomerDto findOneByUuid(UUID uuid) throws Exception {
		Customer entity = customerService.findOneEntityByUuid(uuid);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, CustomerDto.class);
	}

	@Override
	public CustomerDto findOneProperties(Map<String, Object> properties) throws Exception {
		Customer entity = customerService.findOneEntityByProperties(properties);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(true);
		return modelService.getDto(entity, context, CustomerDto.class);
	}

	@Override
	public CustomerDto create(CustomerDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public CustomerDto update(CustomerDto model) throws BusinessException {
		return save(model);
	}

	public CustomerDto save(CustomerDto dto) throws BusinessException {
		try {
			Customer entity = modelService.getEntity(dto, Customer.class);
			entity = (Customer) customerService.saveEntity(entity);

			InterceptorContext context = new InterceptorContext();
			context.setInitializeCollection(true);
			return modelService.getDto(entity, context, CustomerDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		customerService.deleteByUuid(uuid);
	}

	@Override
	public Page<CustomerDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Customer> page = customerService.findEntityPage(dataTableRequest, CustomerSpecification.getSpecification(dataTableRequest));
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		List<CustomerDto> dtos = modelService.getDto(page.getContent(), context, CustomerDto.class);
		return new PageImpl<CustomerDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return customerService.count();
	}

	@Override
	public List<CustomerDto> findAllDtoCustomers() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Customer.CREATED_DATE, Sort.Direction.DESC);
		
		InterceptorContext context = new InterceptorContext();
		context.setInitializeCollection(false);
		return modelService.getDto(customerService.findEntityBySorts(sorts, false), context, CustomerDto.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = customerService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Customer) {
				
				InterceptorContext context = new InterceptorContext();
				context.setInitializeCollection(false);
				entityObject[0] = modelService.getDto(entityObject[0], context, CustomerDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return customerService.findCountHistory(dataTableRequest);
	}

}

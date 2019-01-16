package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.CustomerDto;
import com.beanframework.backoffice.data.CustomerSearch;
import com.beanframework.backoffice.data.CustomerSpecification;
import com.beanframework.backoffice.data.UserFieldDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;
import com.beanframework.user.domain.UserField;

@Component
public class CustomerFacadeImpl implements CustomerFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private CustomerService customerService;

	@Override
	public Page<CustomerDto> findPage(CustomerSearch search, PageRequest pageable) throws Exception {
		Page<Customer> page = customerService.findEntityPage(search.toString(), CustomerSpecification.findByCriteria(search), pageable);
		List<CustomerDto> dtos = modelService.getDto(page.getContent(), CustomerDto.class);
		return new PageImpl<CustomerDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public CustomerDto findOneByUuid(UUID uuid) throws Exception {
		Customer entity = customerService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, CustomerDto.class);
	}

	@Override
	public CustomerDto findOneProperties(Map<String, Object> properties) throws Exception {
		Customer entity = customerService.findOneEntityByProperties(properties);
		return modelService.getDto(entity, CustomerDto.class);
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

			return modelService.getDto(entity, CustomerDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		customerService.deleteByUuid(uuid);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = customerService.findHistoryByUuid(uuid, firstResult, maxResults);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], CustomerDto.class);
		}

		return revisions;
	}

	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = customerService.findHistoryByRelatedUuid(UserField.USER, uuid, firstResult, maxResults);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserFieldDto.class);
		}

		return revisions;
	}

	@Override
	public List<CustomerDto> findAllDtoCustomers() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(CustomerDto.CREATED_DATE, Sort.Direction.DESC);
		return modelService.getDto(customerService.findEntityBySorts(sorts), CustomerDto.class);
	}

}

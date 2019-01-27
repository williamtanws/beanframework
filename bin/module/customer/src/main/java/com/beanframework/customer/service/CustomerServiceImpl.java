package com.beanframework.customer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
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

	@Cacheable(value = "CustomerOne", key = "#uuid")
	@Override
	public Customer findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Customer.class);
	}

	@Cacheable(value = "CustomerOneProperties", key = "#properties")
	@Override
	public Customer findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Customer.class);
	}

	@Cacheable(value = "CustomersSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Customer> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Customer.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CustomerOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "CustomerOneProperties", allEntries = true), //
			@CacheEvict(value = "CustomersSorts", allEntries = true), //
			@CacheEvict(value = "CustomersPage", allEntries = true), //
			@CacheEvict(value = "CustomersHistory", allEntries = true) }) //
	@Override
	public Customer saveEntity(Customer model) throws BusinessException {
		return (Customer) modelService.saveEntity(model, Customer.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CustomerOne", key = "#uuid"), //
			@CacheEvict(value = "CustomerOneProperties", allEntries = true), //
			@CacheEvict(value = "CustomersSorts", allEntries = true), //
			@CacheEvict(value = "CustomersPage", allEntries = true), //
			@CacheEvict(value = "CustomersHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Customer model = modelService.findOneEntityByUuid(uuid, true, Customer.class);
			modelService.deleteByEntity(model, Customer.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "CustomersPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Customer> findEntityPage(DataTableRequest<T> dataTableRequest) throws Exception {
		return modelService.findEntityPage(dataTableRequest.getSpecification(), dataTableRequest.getPageable(), false, Customer.class);
	}

	@Cacheable(value = "CustomersPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Customer.class);
	}

	@Cacheable(value = "CustomersHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Customer.class);

	}

	@Cacheable(value = "CustomersHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest<Object[]> dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Customer.class);
	}
}

package com.beanframework.customer.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserField;

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
		return modelService.findOneEntityByProperties(properties, true,Customer.class);
	}

	@Cacheable(value = "CustomersSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Customer> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Customer.class);
	}

	@Cacheable(value = "CustomersPage", key = "'query:'+#query+',pageable'+#pageable")
	@Override
	public <T> Page<Customer> findEntityPage(String query, Specification<T> specification, PageRequest pageable) throws Exception {
		return modelService.findEntityPage(specification, pageable, false, Customer.class);
	}

	@Cacheable(value = "CustomersHistory", key = "'uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, Customer.class);
	}

	@Cacheable(value = "CustomersRelatedHistory", key = "'relatedEntity:'+#relatedEntity+',uuid:'+#uuid+',firstResult:'+#firstResult+',maxResults:'+#maxResults")
	@Override
	public List<Object[]> findHistoryByRelatedUuid(String relatedEntity, UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(relatedEntity).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		return modelService.findHistory(false, criterion, order, firstResult, maxResults, UserField.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CustomerOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "CustomerOneProperties", allEntries = true), //
			@CacheEvict(value = "CustomersSorts", allEntries = true), //
			@CacheEvict(value = "CustomersPage", allEntries = true), //
			@CacheEvict(value = "CustomersHistory", allEntries = true), //
			@CacheEvict(value = "CustomersRelatedHistory", allEntries = true) }) //
	@Override
	public Customer saveEntity(Customer model) throws BusinessException {
		return (Customer) modelService.saveEntity(model, Customer.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CustomerOne", key = "#uuid"), //
			@CacheEvict(value = "CustomerOneProperties", allEntries = true), //
			@CacheEvict(value = "CustomersSorts", allEntries = true), //
			@CacheEvict(value = "CustomersPage", allEntries = true), //
			@CacheEvict(value = "CustomersHistory", allEntries = true), //
			@CacheEvict(value = "CustomersRelatedHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Customer model = modelService.findOneEntityByUuid(uuid, true, Customer.class);
			modelService.deleteByEntity(model, Customer.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}

package com.beanframework.enumuration.service;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.enumuration.domain.Enumeration;

@Service
public class EnumerationServiceImpl implements EnumerationService {

	@Autowired
	private ModelService modelService;

	@Override
	public Enumeration create() throws Exception {
		return modelService.create(Enumeration.class);
	}

	@Cacheable(value = "EnumOne", key = "#uuid")
	@Override
	public Enumeration findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Enumeration.class);
	}

	@Cacheable(value = "EnumOneProperties", key = "#properties")
	@Override
	public Enumeration findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Enumeration.class);
	}

	@Cacheable(value = "EnumsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Enumeration> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Enumeration.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "EnumOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "EnumOneProperties", allEntries = true), //
			@CacheEvict(value = "EnumsSorts", allEntries = true), //
			@CacheEvict(value = "EnumsPage", allEntries = true), //
			@CacheEvict(value = "EnumsHistory", allEntries = true) }) //
	@Override
	public Enumeration saveEntity(Enumeration model) throws BusinessException {
		return (Enumeration) modelService.saveEntity(model, Enumeration.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "EnumOne", key = "#uuid"), //
			@CacheEvict(value = "EnumOneProperties", allEntries = true), //
			@CacheEvict(value = "EnumsSorts", allEntries = true), //
			@CacheEvict(value = "EnumsPage", allEntries = true), //
			@CacheEvict(value = "EnumsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Enumeration model = modelService.findOneEntityByUuid(uuid, true, Enumeration.class);
			modelService.deleteByEntity(model, Enumeration.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "EnumsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Enumeration> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Enumeration.class);
	}

	@Cacheable(value = "EnumsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Enumeration.class);
	}

	@Cacheable(value = "EnumsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Enumeration.class);

	}

	@Cacheable(value = "EnumsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Enumeration.class);
	}
}

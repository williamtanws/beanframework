package com.beanframework.vendor.service;

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
import com.beanframework.vendor.domain.Vendor;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private ModelService modelService;

	@Override
	public Vendor create() throws Exception {
		return modelService.create(Vendor.class);
	}

	@Cacheable(value = "VendorOne", key = "#uuid")
	@Override
	public Vendor findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Vendor.class);
	}

	@Cacheable(value = "VendorOneProperties", key = "#properties")
	@Override
	public Vendor findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Vendor.class);
	}

	@Cacheable(value = "VendorsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Vendor> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Vendor.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "VendorOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "VendorOneProperties", allEntries = true), //
			@CacheEvict(value = "VendorsSorts", allEntries = true), //
			@CacheEvict(value = "VendorsPage", allEntries = true), //
			@CacheEvict(value = "VendorsHistory", allEntries = true) }) //
	@Override
	public Vendor saveEntity(Vendor model) throws BusinessException {
		return (Vendor) modelService.saveEntity(model, Vendor.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "VendorOne", key = "#uuid"), //
			@CacheEvict(value = "VendorOneProperties", allEntries = true), //
			@CacheEvict(value = "VendorsSorts", allEntries = true), //
			@CacheEvict(value = "VendorsPage", allEntries = true), //
			@CacheEvict(value = "VendorsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Vendor model = modelService.findOneEntityByUuid(uuid, true, Vendor.class);
			modelService.deleteByEntity(model, Vendor.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "VendorsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Vendor> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Vendor.class);
	}

	@Cacheable(value = "VendorsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Vendor.class);
	}

	@Cacheable(value = "VendorsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Vendor.class);

	}

	@Cacheable(value = "VendorsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Vendor.class);
	}
}

package com.beanframework.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Hibernate;
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

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private ModelService modelService;

	@Override
	public Comment create() throws Exception {
		return modelService.create(Comment.class);
	}

	@Cacheable(value = "CommentOne", key = "#uuid")
	@Override
	public Comment findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Comment.class);
	}

	@Cacheable(value = "CommentOneProperties", key = "#properties")
	@Override
	public Comment findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Comment.class);
	}

	@Cacheable(value = "CommentsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Comment> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Comment.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CommentOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "CommentOneProperties", allEntries = true), //
			@CacheEvict(value = "CommentsSorts", allEntries = true), //
			@CacheEvict(value = "CommentsPage", allEntries = true), //
			@CacheEvict(value = "CommentsHistory", allEntries = true), //
			@CacheEvict(value = "CommentsCount", allEntries = true) })
	@Override
	public Comment saveEntity(Comment model) throws BusinessException {
		return (Comment) modelService.saveEntity(model, Comment.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "CommentOne", key = "#uuid"), //
			@CacheEvict(value = "CommentOneProperties", allEntries = true), //
			@CacheEvict(value = "CommentsSorts", allEntries = true), //
			@CacheEvict(value = "CommentsPage", allEntries = true), //
			@CacheEvict(value = "CommentsHistory", allEntries = true), //
			@CacheEvict(value = "CommentsCount", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Comment model = modelService.findOneEntityByUuid(uuid, true, Comment.class);
			modelService.deleteByEntity(model, Comment.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "CommentsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Comment> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Comment.class);
	}

	@Cacheable(value = "CommentsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Comment.class);
	}

	@Cacheable(value = "CommentsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Comment.class);
		for (Object[] objects : histories) {
			Comment comment = (Comment) objects[0];
			Hibernate.initialize(comment.getLastModifiedBy());
			Hibernate.unproxy(comment.getLastModifiedBy());
		}
		return histories;
	}

	@Cacheable(value = "CommentsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Comment.class);
	}

	@Cacheable(value = "CommentsCount", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int countComment(DataTableRequest dataTableRequest) throws Exception {
		return modelService.count(dataTableRequest.getProperties(), Comment.class);
	}
}

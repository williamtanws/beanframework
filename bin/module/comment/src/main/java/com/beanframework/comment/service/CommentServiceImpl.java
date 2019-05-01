package com.beanframework.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beanframework.comment.domain.Comment;
import com.beanframework.comment.specification.CommentSpecification;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private ModelService modelService;
	
	@Override
	public Comment findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Comment.class);
	}

	@Override
	public Comment findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Comment.class);
	}

	@Override
	public List<Comment> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Comment.class);
	}

	@Override
	public Comment saveEntity(Comment model) throws BusinessException {
		return (Comment) modelService.saveEntity(model, Comment.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Comment model = modelService.findOneEntityByUuid(uuid, Comment.class);
			modelService.deleteByEntity(model, Comment.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Comment> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(CommentSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Comment.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Comment.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Comment.class);
		return histories;
	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Comment.class);
	}

	@Override
	public int countCommentByProperties(Map<String, Object> properties) throws Exception {
		return modelService.count(properties, Comment.class);
	}
}

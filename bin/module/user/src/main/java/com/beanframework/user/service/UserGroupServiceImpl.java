package com.beanframework.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private ModelService modelService;

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), UserGroup.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.countHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), UserGroup.class);
	}

	@Override
	public void removeUserGroupsRel(UserGroup model) throws Exception {
		Specification<User> specification = new Specification<User>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(User.USER_GROUPS, JoinType.LEFT).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<UserGroup> entities = modelService.findBySpecificationBySort(specification, null, UserGroup.class);

		for (int i = 0; i < entities.size(); i++) {

			boolean removed = false;
			for (int j = 0; j < entities.get(i).getUserGroups().size(); j++) {
				if (entities.get(i).getUserGroups().get(j).getUuid().equals(model.getUuid())) {
					entities.get(i).getUserGroups().remove(j);
					removed = true;
					break;
				}
			}

			if (removed)
				modelService.saveEntityQuietly(entities.get(i), UserGroup.class);
		}
	}

	@Override
	public void removeUserRightsRel(UserRight model) throws Exception {
		Specification<UserGroup> specification = new Specification<UserGroup>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(UserGroup.USER_AUTHORITIES, JoinType.LEFT).get(UserAuthority.USER_RIGHT).get(UserRight.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<UserGroup> entities = modelService.findBySpecificationBySort(specification, null, UserGroup.class);

		for (int i = 0; i < entities.size(); i++) {

			boolean removed = false;
			for (int j = 0; j < entities.get(i).getUserAuthorities().size(); j++) {
				if (entities.get(i).getUserAuthorities().get(j).getUserRight().getUuid().equals(model.getUuid())) {
					entities.get(i).getUserAuthorities().get(j).setUserRight(null);
					removed = true;
					break;
				}
			}

			if (removed)
				modelService.saveEntityQuietly(entities.get(i), UserGroup.class);
		}
	}

	@Override
	public void removeUserPermissionsRel(UserPermission model) throws Exception {
		Specification<UserGroup> specification = new Specification<UserGroup>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(UserGroup.USER_AUTHORITIES, JoinType.LEFT).get(UserAuthority.USER_PERMISSION).get(UserPermission.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<UserGroup> entities = modelService.findBySpecificationBySort(specification, null, UserGroup.class);

		for (int i = 0; i < entities.size(); i++) {

			boolean removed = false;
			for (int j = 0; j < entities.get(i).getUserAuthorities().size(); j++) {
				if (entities.get(i).getUserAuthorities().get(j).getUserPermission().getUuid().equals(model.getUuid())) {
					entities.get(i).getUserAuthorities().get(j).setUserPermission(null);
					removed = true;
					break;
				}
			}

			if (removed)
				modelService.saveEntityQuietly(entities.get(i), UserGroup.class);
		}
	}
}

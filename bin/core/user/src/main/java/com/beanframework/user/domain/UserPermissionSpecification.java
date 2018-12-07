package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class UserPermissionSpecification {
	public static Specification<UserPermission> findByCriteria(final UserPermission userPermission) {

		return new Specification<UserPermission>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9019750522524126629L;

			@Override
			public Predicate toPredicate(Root<UserPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(userPermission.getId())) {
					predicates.add(cb.or(cb.like(root.get(UserPermission.ID), "%" + userPermission.getId() + "%")));
				}
				
				if(predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}
}
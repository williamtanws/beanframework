package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.AbstractSpecification;
import com.beanframework.user.domain.UserPermission;

public class UserPermissionSpecification extends AbstractSpecification {
	public static Specification<UserPermission> findByCriteria(final UserPermissionSearch data) {

		return new Specification<UserPermission>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4282172888118261930L;

			@Override
			public Predicate toPredicate(Root<UserPermission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(data.getSearchAll())) {
					addPredicates(data.getSearchAll(), root, cb, predicates);
				} else {
					addPredicates(data.getId(), root, cb, predicates);
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}

	public static void addPredicates(String id, Root<UserPermission> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(id)) {
			predicates.add(cb.or(cb.like(root.get(UserPermission.ID), convertToPattern(id))));
		}
	}
}
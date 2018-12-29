package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.user.domain.UserGroup;

public class UserGroupSpecification {
	public static Specification<UserGroup> findByCriteria(final UserGroup userGroup) {

		return new Specification<UserGroup>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4107506452736175041L;

			@Override
			public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(userGroup.getId())) {
					predicates.add(cb.or(cb.like(root.get(UserGroup.ID), "%" + userGroup.getId() + "%")));
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}
}
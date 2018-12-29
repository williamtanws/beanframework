package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.user.domain.UserRight;

public class UserRightSpecification {
	public static Specification<UserRight> findByCriteria(final UserRight userRight) {

		return new Specification<UserRight>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 9019750522524126629L;

			@Override
			public Predicate toPredicate(Root<UserRight> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(userRight.getId())) {
					predicates.add(cb.or(cb.like(root.get(UserRight.ID), "%" + userRight.getId() + "%")));
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
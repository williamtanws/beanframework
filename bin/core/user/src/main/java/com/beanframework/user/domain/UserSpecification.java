package com.beanframework.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
	public static Specification<User> findByCriteria(final User user) {

		return new Specification<User>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4771629093000498982L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(user.getId())) {
					predicates.add(cb.or(cb.like(root.get(User.ID), "%" + user.getId() + "%")));
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
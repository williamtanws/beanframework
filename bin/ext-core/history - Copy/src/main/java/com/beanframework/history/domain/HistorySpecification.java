package com.beanframework.history.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class HistorySpecification {
	public static Specification<History> findByCriteria(final History history) {

		return new Specification<History>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3162338087352704668L;

			@Override
			public Predicate toPredicate(Root<History> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(history.getId())) {
					predicates.add(cb.or(cb.like(root.get(History.ID), "%" + history.getId() + "%")));
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
package com.beanframework.catalog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CatalogSpecification {
	public static Specification<Catalog> findByCriteria(final Catalog catalog) {

		return new Specification<Catalog>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3162338087352704668L;

			@Override
			public Predicate toPredicate(Root<Catalog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(catalog.getId())) {
					predicates.add(cb.or(cb.like(root.get(Catalog.ID), "%" + catalog.getId() + "%")));
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
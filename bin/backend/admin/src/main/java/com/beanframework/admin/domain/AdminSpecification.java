package com.beanframework.admin.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class AdminSpecification {
	public static Specification<Admin> findByCriteria(final Admin admin) {

		return new Specification<Admin>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6060970738788715423L;

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(admin.getId())) {
					predicates.add(cb.or(cb.like(root.get(Admin.ID), "%" + admin.getId() + "%")));
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
package com.beanframework.console.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.admin.domain.Admin;

public class AdminSpecification {
	public static Specification<Admin> findByCriteria(final AdminSearch data) {

		return new Specification<Admin>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5129973569935531170L;

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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

	public static void addPredicates(String id, Root<Admin> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(id)) {
			if (id.contains("%") == false && id.contains("_") == false) {
				id = "%" + id + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Admin.ID), id)));
		}
	}
}
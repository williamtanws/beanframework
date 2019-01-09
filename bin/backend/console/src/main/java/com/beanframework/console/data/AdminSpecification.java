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
import com.beanframework.common.data.AbstractSpecification;

public class AdminSpecification extends AbstractSpecification {
	public static Specification<Admin> findByCriteria(final AdminSearch data) {

		return new Specification<Admin>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6451054614872238142L;

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(data.getSearchAll())) {
					addPredicates(data.getSearchAll(), data.getSearchAll(), root, cb, predicates);
				} else {
					addPredicates(data.getId(), data.getName(), root, cb, predicates);
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}

	public static void addPredicates(String id, String name, Root<Admin> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotBlank(id)) {
			predicates.add(cb.or(cb.like(root.get(Admin.ID), convertToPattern(id))));
		}
		if (StringUtils.isNotBlank(name)) {
			predicates.add(cb.or(cb.like(root.get(Admin.NAME), convertToPattern(name))));
		}
	}
}
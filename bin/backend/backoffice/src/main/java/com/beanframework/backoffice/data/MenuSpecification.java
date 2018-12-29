package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.menu.domain.Menu;

public class MenuSpecification {
	public static Specification<Menu> findByCriteria(final MenuSearch data) {

		return new Specification<Menu>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3349403867960461011L;

			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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

	public static void addPredicates(String id, Root<Menu> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(id)) {
			if (id.contains("%") == false && id.contains("_") == false) {
				id = "%" + id + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Menu.ID), id)));
		}
	}
}
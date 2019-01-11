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
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldSpecification extends AbstractSpecification {
	public static Specification<DynamicField> findByCriteria(final DynamicFieldSearch data) {

		return new Specification<DynamicField>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3617871778212990102L;

			@Override
			public Predicate toPredicate(Root<DynamicField> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(data.getSearchAll())) {
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

	public static void addPredicates(String id, String name, Root<DynamicField> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(id)) {
			predicates.add(cb.or(cb.like(root.get(DynamicField.ID), convertToPattern(id))));
		}

		if (StringUtils.isNotEmpty(name)) {
			predicates.add(cb.or(cb.like(root.get(DynamicField.NAME), convertToPattern(name))));
		}
	}
}
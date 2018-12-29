package com.beanframework.console.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.configuration.domain.Configuration;

public class ConfigurationSpecification {
	public static Specification<Configuration> findByCriteria(final ConfigurationSearch data) {

		return new Specification<Configuration>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3546335730982207838L;

			@Override
			public Predicate toPredicate(Root<Configuration> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(data.getSearchAll())) {
					addPredicates(data.getSearchAll(), data.getSearchAll(), root, cb, predicates);
				} else {
					addPredicates(data.getId(), data.getValue(), root, cb, predicates);
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}

	public static void addPredicates(String id, String value, Root<Configuration> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(id)) {
			if (id.contains("%") == false && id.contains("_") == false) {
				id = "%" + id + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Configuration.ID), id)));
		}

		if (StringUtils.isNotEmpty(value)) {
			if (value.contains("%") == false && value.contains("_") == false) {
				value = "%" + value + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Configuration.VALUE), value)));
		}
	}
}
package com.beanframework.configuration.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class ConfigurationSpecification {
	public static Specification<Configuration> findByCriteria(final Configuration configuration) {

		return new Specification<Configuration>() {

			private static final long serialVersionUID = 9222701031635293521L;

			@Override
			public Predicate toPredicate(Root<Configuration> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(configuration.getId())) {
					predicates.add(cb.or(cb.like(root.get(Configuration.ID), "%" + configuration.getId() + "%")));
				}
				
				if (StringUtils.isNotBlank(configuration.getValue())) {
					predicates.add(cb.or(cb.like(root.get(Configuration.VALUE), "%" + configuration.getId() + "%")));
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
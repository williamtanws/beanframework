package com.beanframework.cronjob.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CronjobSpecification {
	public static Specification<Cronjob> findByCriteria(final Cronjob cronjob) {

		return new Specification<Cronjob>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3542979795394161099L;

			@Override
			public Predicate toPredicate(Root<Cronjob> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(cronjob.getJobGroup())) {
					predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_GROUP), "%" + cronjob.getJobGroup() + "%")));
				}
				if (StringUtils.isNotEmpty(cronjob.getJobName())) {
					predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_NAME), "%" + cronjob.getJobName() + "%")));
				}
				if (StringUtils.isNotEmpty(cronjob.getDescription())) {
					predicates.add(cb.or(cb.like(root.get(Cronjob.DESCRIPTION), "%" + cronjob.getDescription() + "%")));
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
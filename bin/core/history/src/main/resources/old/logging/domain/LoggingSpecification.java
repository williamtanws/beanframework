package com.beanframework.logging.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class LoggingSpecification {
	public static Specification<Logging> findByCriteria(final Logging logging) {

		return new Specification<Logging>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 4910000106018106021L;

			@Override
			public Predicate toPredicate(Root<Logging> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				Predicate validFrom = cb
						.or(cb.greaterThanOrEqualTo(root.get(Logging.CREATED_DATE), logging.getCreatedDateFrom()));
				Predicate validTo = cb
						.or(cb.lessThanOrEqualTo(root.get(Logging.CREATED_DATE), logging.getCreatedDateTo()));

				if (logging.getCreatedDateFrom() != null && logging.getCreatedDateTo() != null) {

					if (StringUtils.isNotEmpty(logging.getCreatedBy())) {
						predicates.add(cb.and(cb.like(root.get(Logging.CREATED_BY), "%" + logging.getCreatedBy() + "%"),
								cb.and(validFrom, validTo)));
					}
					if (StringUtils.isNotEmpty(logging.getIp())) {
						predicates.add(cb.and(cb.like(root.get(Logging.IP), "%" + logging.getIp() + "%"),
								cb.and(validFrom, validTo)));
					}
					if (StringUtils.isNotEmpty(logging.getChannel())) {
						predicates.add(cb.and(cb.like(root.get(Logging.CHANNEL), "%" + logging.getChannel() + "%"),
								cb.and(validFrom, validTo)));
					}
					if (StringUtils.isNotEmpty(logging.getOperate())) {
						predicates.add(cb.and(cb.like(root.get(Logging.OPERATE), "%" + logging.getOperate() + "%"),
								cb.and(validFrom, validTo)));
					}
					if (StringUtils.isNotEmpty(logging.getResult())) {
						predicates.add(cb.and(cb.like(root.get(Logging.RESULT), "%" + logging.getResult() + "%"),
								cb.and(validFrom, validTo)));
					}

					if (predicates.isEmpty()) {
						predicates.add(cb.and(validFrom, validTo));
					}
				} else {
					if (StringUtils.isNotEmpty(logging.getCreatedBy())) {
						predicates
								.add(cb.or(cb.like(root.get(Logging.CREATED_BY), "%" + logging.getCreatedBy() + "%")));
					}
					if (StringUtils.isNotEmpty(logging.getIp())) {
						predicates.add(cb.or(cb.like(root.get(Logging.IP), "%" + logging.getIp() + "%")));
					}
					if (StringUtils.isNotEmpty(logging.getChannel())) {
						predicates.add(cb.or(cb.like(root.get(Logging.CHANNEL), "%" + logging.getChannel() + "%")));
					}
					if (StringUtils.isNotEmpty(logging.getOperate())) {
						predicates.add(cb.or(cb.like(root.get(Logging.OPERATE), "%" + logging.getOperate() + "%")));
					}
					if (StringUtils.isNotEmpty(logging.getResult())) {
						predicates.add(cb.or(cb.like(root.get(Logging.RESULT), "%" + logging.getResult() + "%")));
					}
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
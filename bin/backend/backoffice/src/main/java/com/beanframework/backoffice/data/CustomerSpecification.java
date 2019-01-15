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
import com.beanframework.customer.domain.Customer;

public class CustomerSpecification extends AbstractSpecification {
	public static Specification<CustomerDto> findByCriteria(final CustomerSearch data) {

		return new Specification<CustomerDto>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6451054614872238142L;

			@Override
			public Predicate toPredicate(Root<CustomerDto> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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

	public static void addPredicates(String id, String name, Root<CustomerDto> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotBlank(id)) {
			predicates.add(cb.or(cb.like(root.get(Customer.ID), convertToPattern(id))));
		}
		if (StringUtils.isNotBlank(name)) {
			predicates.add(cb.or(cb.like(root.get(Customer.NAME), convertToPattern(name))));
		}
	}
}
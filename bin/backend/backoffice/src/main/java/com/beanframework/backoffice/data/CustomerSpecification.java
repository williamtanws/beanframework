package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.customer.domain.Customer;

public class CustomerSpecification {
	public static Specification<Customer> findByCriteria(final Customer customer) {

		return new Specification<Customer>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6060970738788715423L;

			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(customer.getId())) {
					predicates.add(cb.or(cb.like(root.get(Customer.ID), "%" + customer.getId() + "%")));
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
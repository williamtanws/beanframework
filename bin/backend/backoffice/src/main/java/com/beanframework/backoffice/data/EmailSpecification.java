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
import com.beanframework.email.domain.Email;

public class EmailSpecification extends AbstractSpecification {
	public static Specification<EmailDto> findByCriteria(final EmailSearch data) {

		return new Specification<EmailDto>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6116377294874765960L;

			@Override
			public Predicate toPredicate(Root<EmailDto> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(data.getSearchAll())) {
					addPredicates(data.getSearchAll(), data.getSearchAll(), root, cb, predicates);
				} else {
					addPredicates(data.getName(), data.getSubject(), root, cb, predicates);
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}

	public static void addPredicates(String name, String subject, Root<EmailDto> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(name)) {
			predicates.add(cb.or(cb.like(root.get(Email.NAME), convertToPattern(name))));
		}

		if (StringUtils.isNotEmpty(subject)) {
			predicates.add(cb.or(cb.like(root.get(Email.SUBJECT), convertToPattern(subject))));
		}

	}
}
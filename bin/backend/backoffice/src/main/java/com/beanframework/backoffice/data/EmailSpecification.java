package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.email.domain.Email;

public class EmailSpecification {
	public static Specification<Email> findByCriteria(final Email email) {

		return new Specification<Email>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3162338087352704668L;

			@Override
			public Predicate toPredicate(Root<Email> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotBlank(email.getToRecipients())) {
					predicates.add(cb.or(cb.like(root.get(Email.TORECIPIENTS), "%" + email.getToRecipients() + "%")));
				}
				if (StringUtils.isNotBlank(email.getCcRecipients())) {
					predicates.add(cb.or(cb.like(root.get(Email.CCRECIPIENTS), "%" + email.getCcRecipients() + "%")));
				}
				if (StringUtils.isNotBlank(email.getBccRecipients())) {
					predicates.add(cb.or(cb.like(root.get(Email.BCCRECIPIENTS), "%" + email.getBccRecipients() + "%")));
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
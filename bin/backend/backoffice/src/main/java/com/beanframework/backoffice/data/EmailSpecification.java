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
	public static Specification<Email> findByCriteria(final EmailSearch data) {

		return new Specification<Email>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6116377294874765960L;

			@Override
			public Predicate toPredicate(Root<Email> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(data.getSearchAll())) {
					addPredicates(data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), root, cb, predicates);
				} else {
					addPredicates(data.getToRecipients(), data.getCcRecipients(), data.getBccRecipients(), data.getSubject(), data.getText(), data.getHtml(), root, cb, predicates);
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}

	public static void addPredicates(String to, String cc, String bcc, String subject, String text, String html, Root<Email> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(to)) {
			predicates.add(cb.or(cb.like(root.get(Email.TO_RECIPIENTS), convertToPattern(to))));
		}

		if (StringUtils.isNotEmpty(cc)) {
			predicates.add(cb.or(cb.like(root.get(Email.CC_RECIPIENTS), convertToPattern(cc))));
		}

		if (StringUtils.isNotEmpty(bcc)) {
			predicates.add(cb.or(cb.like(root.get(Email.BCC_RECIPIENTS), convertToPattern(bcc))));
		}

		if (StringUtils.isNotEmpty(text)) {
			predicates.add(cb.or(cb.like(root.get(Email.TEXT), convertToPattern(text))));
		}

		if (StringUtils.isNotEmpty(html)) {
			predicates.add(cb.or(cb.like(root.get(Email.HTML), convertToPattern(html))));
		}
	}
}
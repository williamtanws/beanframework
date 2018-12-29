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
			if (to.contains("%") == false && to.contains("_") == false) {
				to = "%" + to + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Email.TO_RECIPIENTS), to)));
		}

		if (StringUtils.isNotEmpty(cc)) {
			if (cc.contains("%") == false && cc.contains("_") == false) {
				cc = "%" + cc + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Email.CC_RECIPIENTS), cc)));
		}

		if (StringUtils.isNotEmpty(bcc)) {
			if (bcc.contains("%") == false && bcc.contains("_") == false) {
				bcc = "%" + bcc + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Email.BCC_RECIPIENTS), bcc)));
		}

		if (StringUtils.isNotEmpty(text)) {
			if (text.contains("%") == false && text.contains("_") == false) {
				text = "%" + text + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Email.TEXT), text)));
		}

		if (StringUtils.isNotEmpty(html)) {
			if (html.contains("%") == false && text.contains("_") == false) {
				html = "%" + html + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Email.HTML), html)));
		}
	}
}
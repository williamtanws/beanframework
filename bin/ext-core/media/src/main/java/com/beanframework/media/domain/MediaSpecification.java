package com.beanframework.media.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.domain.AbstractDomain;

public class MediaSpecification {
	public static Specification<Media> findByCriteria(final Media Media) {

		return new Specification<Media>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3162338087352704668L;

			@Override
			public Predicate toPredicate(Root<Media> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(Media.getId())) {
					predicates.add(cb.or(cb.like(root.get(AbstractDomain.ID), "%" + Media.getId() + "%")));
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
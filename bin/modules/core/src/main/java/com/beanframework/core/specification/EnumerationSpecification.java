package com.beanframework.core.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.enumuration.domain.Enumeration;

public class EnumerationSpecification extends AbstractSpecification {

	public static <T> Specification<T> getSpecification(DataTableRequest dataTableRequest) {
		return new Specification<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				String search = clean(dataTableRequest.getSearch());

				if (StringUtils.isNotBlank(search)) {
					if (isUuid(dataTableRequest.getSearch())) {
						predicates.add(cb.or(cb.equal(root.get(GenericEntity.UUID), UUID.fromString(search))));
					}
					predicates.add(cb.or(cb.like(root.get(GenericEntity.ID), convertToLikePattern(search))));
					predicates.add(cb.or(cb.like(root.get(Enumeration.NAME), convertToLikePattern(search))));

					if (isInteger(dataTableRequest.getSearch())) {
						predicates.add(cb.or(cb.equal(root.get(Enumeration.SORT), search)));
					}
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}

			public String toString() {
				return dataTableRequest.toString();
			}
		};
	}
}

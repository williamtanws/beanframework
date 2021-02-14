package com.beanframework.core.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.dynamicfield.domain.DynamicField;

public class DynamicFieldSpecification extends AbstractSpecification {

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
						predicates.add(cb.or(cb.equal(root.get(DynamicField.LANGUAGE), UUID.fromString(search))));
						predicates.add(cb.and(root.join(DynamicField.ENUMERATIONS, JoinType.LEFT).in(UUID.fromString(search))));
					}
					predicates.add(cb.or(cb.like(root.get(GenericEntity.ID), convertToLikePattern(search))));
					predicates.add(cb.or(cb.like(root.get(DynamicField.NAME), convertToLikePattern(search))));
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

	public static <T> Specification<T> getDynamicFieldByEnumerationUuid(UUID... uuid) {
		if (uuid == null) {
			throw new NullPointerException();
		}

		return new Specification<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.and(root.join(DynamicField.ENUMERATIONS, JoinType.LEFT).in(Arrays.asList(uuid))));

				return cb.and(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			}

			public String toString() {
				return "uuid[" + uuid.toString() + "]" + ", getDynamicFieldByEnumerations";
			}

		};
	}

	public static <T> Specification<T> getDynamicFieldByLanguageUuid(UUID uuid) {
		if (uuid == null) {
			throw new NullPointerException();
		}

		return new Specification<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.or(cb.equal(root.get(DynamicField.LANGUAGE), uuid)));

				return cb.and(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			}

			public String toString() {
				return "uuid[" + uuid.toString() + "]" + ", getDynamicFieldByEnumerations";
			}

		};
	}
}

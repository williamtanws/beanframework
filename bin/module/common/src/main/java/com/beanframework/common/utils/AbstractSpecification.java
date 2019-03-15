package com.beanframework.common.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableColumnSpecs;
import com.beanframework.common.data.DataTableRequest;

public class AbstractSpecification {

	public static String convertToLikePattern(String value) {
		if (value.contains("%") == false) {
			value = "%" + value + "%";
		}
		return value;
	}

	public static <T> Specification<T> getSpecification(DataTableRequest dataTableRequest) {
		return new Specification<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return getPredicate(dataTableRequest, root, query, cb);
			}

		};
	}

	public static <T> Predicate getPredicate(DataTableRequest dataTableRequest, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (dataTableRequest.isGlobalSearch() && StringUtils.isNotEmpty(dataTableRequest.getSearch())) {
			for (DataTableColumnSpecs specs : dataTableRequest.getColumns()) {
				if (specs.getData().contains(".")) {
					String[] objectProperty = specs.getData().split("\\.");
					predicates.add(cb.or(cb.like(root.get(objectProperty[0]).get(objectProperty[1]), convertToLikePattern(dataTableRequest.getSearch()))));
				} else {
					predicates.add(cb.or(cb.like(root.get(specs.getData()), convertToLikePattern(dataTableRequest.getSearch()))));
				}
			}
		} else {
			for (DataTableColumnSpecs specs : dataTableRequest.getColumns()) {
				if (StringUtils.isNotBlank(specs.getSearch())) {
					if (specs.getData().contains(".")) {
						String[] objectProperty = specs.getData().split("\\.");
						predicates.add(cb.or(cb.like(root.get(objectProperty[0]).get(objectProperty[1]), convertToLikePattern(specs.getSearch()))));
					} else {
						predicates.add(cb.or(cb.like(root.get(specs.getData()), convertToLikePattern(specs.getSearch()))));
					}
				}
			}
		}

		if (predicates.isEmpty()) {
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		} else {
			return cb.or(predicates.toArray(new Predicate[predicates.size()]));
		}
	}
}

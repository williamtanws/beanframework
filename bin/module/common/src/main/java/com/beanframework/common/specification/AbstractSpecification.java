package com.beanframework.common.specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.data.DataTableColumnSpecs;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.common.utils.CommonStringUtils;

public abstract class AbstractSpecification {

	public static String convertToLikePattern(String value) {
		if (value.contains("%") == Boolean.FALSE) {
			value = "%" + value + "%";
		}
		return value;
	}

	public static boolean isUuid(String uuid) {
		try {
			UUID.fromString(uuid);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	public static String clean(String s) {
		return s.trim();
	}

	public static <T> Specification<T> getSpecification(DataTableRequest dataTableRequest) {
		return new Specification<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return getPredicate(dataTableRequest, root, query, cb);
			}

			public String toString() {
				return dataTableRequest.toString();
			}
		};
	}

	public static <T> Predicate getPredicate(DataTableRequest dataTableRequest, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (dataTableRequest.isGlobalSearch() && StringUtils.isNotEmpty(dataTableRequest.getSearch())) {
			if (isUuid(dataTableRequest.getSearch())) {
				predicates.add(cb.or(cb.equal(root.get(GenericEntity.UUID), UUID.fromString(dataTableRequest.getSearch()))));
			}
			predicates.add(cb.or(cb.like(root.get(GenericEntity.ID), convertToLikePattern(dataTableRequest.getSearch()))));

			for (DataTableColumnSpecs specs : dataTableRequest.getColumns()) {
				if (specs.getData().equals(GenericEntity.UUID) == Boolean.FALSE && specs.getData().equals(GenericEntity.ID) == Boolean.FALSE) {
					if (specs.getData().contains(".")) {
						String[] objectProperty = specs.getData().split("\\.");
						predicates.add(cb.or(cb.like(root.get(objectProperty[0]).get(objectProperty[1]), convertToLikePattern(dataTableRequest.getSearch()))));
					} else {
						try {
							Field field = root.getJavaType().getDeclaredField(specs.getData());
							field.setAccessible(true);
							if (field.getType().isAssignableFrom(String.class)) {
								predicates.add(cb.or(cb.like(root.get(specs.getData()), convertToLikePattern(dataTableRequest.getSearch()))));
							} else if (field.getType().isAssignableFrom(Integer.class) && CommonStringUtils.isStringInt(dataTableRequest.getSearch())) {
								predicates.add(cb.or(cb.equal(root.get(specs.getData()), Integer.parseInt(dataTableRequest.getSearch()))));
							}
							/*
							 * else if (field.getType().isAssignableFrom(Boolean.class)) { if
							 * (BooleanUtils.parseBoolean(dataTableRequest.getSearch())) {
							 * predicates.add(cb.or(cb.isTrue(root.get(specs.getData())))); } else {
							 * predicates.add(cb.or(cb.isFalse(root.get(specs.getData())))); } }
							 */
						} catch (Exception e) {
							predicates.add(cb.or(cb.like(root.get(specs.getData()), convertToLikePattern(dataTableRequest.getSearch()))));
						}
					}
				}
			}
		} else {
			for (DataTableColumnSpecs specs : dataTableRequest.getColumns()) {
				if (StringUtils.isNotBlank(specs.getSearch())) {
					if (specs.getData().contains(".")) {
						String[] objectProperty = specs.getData().split("\\.");
						predicates.add(cb.or(cb.like(root.get(objectProperty[0]).get(objectProperty[1]), convertToLikePattern(specs.getSearch()))));
					} else {
						try {
							Field field = root.getJavaType().getDeclaredField(specs.getData());
							field.setAccessible(true);
							if (field.getType().isAssignableFrom(String.class)) {
								predicates.add(cb.or(cb.like(root.get(specs.getData()), convertToLikePattern(specs.getSearch()))));
							} else if (field.getType().isAssignableFrom(Integer.class)) {
								predicates.add(cb.or(cb.equal(root.get(specs.getData()), specs.getSearch())));
							} else if (field.getType().isAssignableFrom(Boolean.class)) {
								if (BooleanUtils.parseBoolean(specs.getSearch())) {
									predicates.add(cb.or(cb.isTrue(root.get(specs.getData()))));
								} else {
									predicates.add(cb.or(cb.isFalse(root.get(specs.getData()))));
								}
							}
						} catch (Exception e) {
							predicates.add(cb.or(cb.like(root.get(specs.getData()), convertToLikePattern(specs.getSearch()))));
						}
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

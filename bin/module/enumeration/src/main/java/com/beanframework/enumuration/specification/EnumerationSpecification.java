package com.beanframework.enumuration.specification;

import com.beanframework.common.specification.AbstractSpecification;

public class EnumerationSpecification extends AbstractSpecification {
	
//	public static <T> Specification<T> getSpecification(DataTableRequest dataTableRequest) {
//		return new Specification<T>() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				List<Predicate> predicates = new ArrayList<Predicate>();
//
//				if (dataTableRequest.isGlobalSearch() && StringUtils.isNotEmpty(dataTableRequest.getSearch())) {
//					if (isUuid(dataTableRequest.getSearch())) {
//						predicates.add(cb.or(cb.equal(root.get(GenericEntity.UUID), UUID.fromString(dataTableRequest.getSearch()))));
//					}
//					predicates.add(cb.or(cb.like(root.get(GenericEntity.ID), convertToLikePattern(dataTableRequest.getSearch()))));
//					predicates.add(cb.or(cb.like(root.get(Enumeration.NAME), convertToLikePattern(dataTableRequest.getSearch()))));
//					predicates.add(cb.or(cb.like(root.get(Enumeration.SORT), convertToLikePattern(dataTableRequest.getSearch()))));
//				}
//
//				if (predicates.isEmpty()) {
//					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
//				} else {
//					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
//				}
//			}
//
//			public String toString() {
//				return dataTableRequest.toString();
//			}
//		};
//	}
}

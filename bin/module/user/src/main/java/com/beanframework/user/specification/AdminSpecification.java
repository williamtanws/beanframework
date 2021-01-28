package com.beanframework.user.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.utils.AbstractSpecification;
import com.beanframework.user.domain.User;

public class AdminSpecification extends AbstractSpecification {
	public static <T> Predicate getPredicate(DataTableRequest dataTableRequest, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (dataTableRequest.isGlobalSearch() && StringUtils.isNotEmpty(dataTableRequest.getSearch())) {
			if (isUuid(dataTableRequest.getSearch())) {
				predicates.add(cb.or(cb.equal(root.get(GenericEntity.UUID), UUID.fromString(dataTableRequest.getSearch()))));
			}
			predicates.add(cb.or(cb.like(root.get(GenericEntity.ID), convertToLikePattern(dataTableRequest.getSearch()))));
			predicates.add(cb.or(cb.like(root.get(User.NAME), convertToLikePattern(dataTableRequest.getSearch()))));
		}

		if (predicates.isEmpty()) {
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		} else {
			return cb.or(predicates.toArray(new Predicate[predicates.size()]));
		}
	}
}

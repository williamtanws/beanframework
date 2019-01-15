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
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobEnum.Result;
import com.beanframework.cronjob.domain.CronjobEnum.Status;

public class CronjobSpecification extends AbstractSpecification {
	public static Specification<CronjobDto> findByCriteria(final CronjobSearch data) {

		return new Specification<CronjobDto>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7049600737876405604L;

			@Override
			public Predicate toPredicate(Root<CronjobDto> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(data.getSearchAll())) {
					addPredicates(data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), null, null, null, root, cb, predicates);
				} else {
					addPredicates(data.getId(), data.getJobClass(), data.getJobName(), data.getJobGroup(), data.getStartup(), data.getStatus(), data.getResult(), root, cb, predicates);
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}

	public static void addPredicates(String id, String jobClass, String jobName, String JobGroup, Boolean startup, Status status, Result result, Root<CronjobDto> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(id)) {
			predicates.add(cb.or(cb.like(root.get(Cronjob.ID), convertToPattern(id))));
		}

		if (StringUtils.isNotEmpty(jobClass)) {
			predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_CLASS), convertToPattern(jobClass))));
		}

		if (StringUtils.isNotEmpty(jobName)) {
			predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_NAME), convertToPattern(jobName))));
		}

		if (StringUtils.isNotEmpty(JobGroup)) {
			predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_GROUP), convertToPattern(JobGroup))));
		}
		
		if (startup != null) {
			if(startup == Boolean.TRUE) {
				predicates.add(cb.or(cb.equal(root.get(Cronjob.STARTUP), 1)));
			}
			if(startup == Boolean.FALSE) {
				predicates.add(cb.or(cb.equal(root.get(Cronjob.STARTUP), 0)));
			}
		}

		if (status != null) {
			predicates.add(cb.or(cb.equal(root.get(Cronjob.STATUS), status)));
		}

		if (result != null) {
			predicates.add(cb.or(cb.equal(root.get(Cronjob.RESULT), result)));
		}

	}
}
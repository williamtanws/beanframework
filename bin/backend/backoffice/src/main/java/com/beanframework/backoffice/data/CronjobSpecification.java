package com.beanframework.backoffice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.cronjob.domain.Cronjob;

public class CronjobSpecification {
	public static Specification<Cronjob> findByCriteria(final CronjobSearch data) {

		return new Specification<Cronjob>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7049600737876405604L;

			@Override
			public Predicate toPredicate(Root<Cronjob> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();

				if (StringUtils.isNotEmpty(data.getSearchAll())) {
					addPredicates(data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), data.getSearchAll(), root, cb, predicates);
				} else {
					addPredicates(data.getId(), data.getJobClass(), data.getJobName(), data.getJobGroup(), data.getDescription(), root, cb, predicates);
				}

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
			}
		};
	}

	public static void addPredicates(String id, String jobClass, String jobName, String JobGroup, String description, Root<Cronjob> root, CriteriaBuilder cb, List<Predicate> predicates) {
		if (StringUtils.isNotEmpty(id)) {
			if (id.contains("%") == false && id.contains("_") == false) {
				id = "%" + id + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Cronjob.ID), id)));
		}

		if (StringUtils.isNotEmpty(jobClass)) {
			if (jobClass.contains("%") == false && jobClass.contains("_") == false) {
				jobClass = "%" + jobClass + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_CLASS), jobClass)));
		}

		if (StringUtils.isNotEmpty(jobName)) {
			if (jobName.contains("%") == false && jobName.contains("_") == false) {
				jobName = "%" + jobName + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_NAME), jobName)));
		}

		if (StringUtils.isNotEmpty(JobGroup)) {
			if (JobGroup.contains("%") == false && JobGroup.contains("_") == false) {
				JobGroup = "%" + JobGroup + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_GROUP), JobGroup)));
		}

		if (StringUtils.isNotEmpty(description)) {
			if (description.contains("%") == false && description.contains("_") == false) {
				description = "%" + description + "%";
			}
			predicates.add(cb.or(cb.like(root.get(Cronjob.JOB_CLASS), description)));
		}
	}
}
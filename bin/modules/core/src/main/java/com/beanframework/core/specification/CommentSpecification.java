package com.beanframework.core.specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.cms.domain.Comment;
import com.beanframework.common.context.SpringContext;
import com.beanframework.common.data.DataTableColumnSpecs;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.common.specification.CommonSpecification;
import com.beanframework.user.domain.User;

public class CommentSpecification extends CommonSpecification {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CommentSpecification.class);

	public static <T> AbstractSpecification<T> getPageSpecification(DataTableRequest dataTableRequest) {
		return new AbstractSpecification<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return getPredicate(dataTableRequest, root, query, cb);
			}

			public String toString() {
				return dataTableRequest.toString();
			}

			@Override
			public List<Selection<?>> toSelection(Root<T> root) {
				List<Selection<?>> multiselect = new ArrayList<Selection<?>>();
				multiselect.add(root.get(Comment.UUID));
				multiselect.add(root.get(Comment.USER));
				multiselect.add(root.get(Comment.HTML));
				return multiselect;
			}
		};
	}

	public static <T> Predicate getPredicate(DataTableRequest dataTableRequest, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (dataTableRequest.isGlobalSearch() && StringUtils.isNotBlank(dataTableRequest.getSearch())) {
			if (isUuid(dataTableRequest.getSearch())) {
				predicates.add(cb.or(cb.equal(root.get(Comment.UUID), UUID.fromString(dataTableRequest.getSearch()))));
			}
			predicates.add(cb.or(cb.like(root.get(Comment.HTML), convertToLikePattern(dataTableRequest.getSearch()))));

			ModelService modelService = SpringContext.getBean(ModelService.class);
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(User.NAME, convertToLikePattern(dataTableRequest.getSearch()));
			try {
				List<User> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, User.class);

				List<UUID> entityUuids = new ArrayList<UUID>();
				for (User entity : entities) {
					entityUuids.add(entity.getUuid());
				}

				if (entityUuids.isEmpty() == false) {
					predicates.add(cb.and(root.get(Comment.USER).in(entityUuids)));
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}

			return cb.or(predicates.toArray(new Predicate[predicates.size()]));
		} else {

			for (DataTableColumnSpecs specs : dataTableRequest.getColumns()) {
				if (StringUtils.isNotBlank(specs.getSearch())) {
					if (specs.getData().equals("html")) {
						predicates.add(cb.or(cb.like(root.get(Comment.HTML), convertToLikePattern(specs.getSearch()))));
					} else if (specs.getData().equals("user")) {
						ModelService modelService = SpringContext.getBean(ModelService.class);
						Map<String, Object> properties = new HashMap<String, Object>();
						properties.put(User.NAME, convertToLikePattern(specs.getSearch()));
						try {
							List<User> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, User.class);

							List<UUID> entityUuids = new ArrayList<UUID>();
							for (User entity : entities) {
								entityUuids.add(entity.getUuid());
							}

							if (entityUuids.isEmpty() == false) {
								predicates.add(cb.and(root.get(Comment.USER).in(entityUuids)));
							}
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
					}
				}
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		}
	}

}

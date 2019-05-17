package com.beanframework.core.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.BeforeRemoveEvent;
import com.beanframework.common.registry.BeforeRemoveListener;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

public class CoreBeforeRemoveListener implements BeforeRemoveListener {

	@Autowired
	private ModelService modelService;

	@Override
	public void beforeRemove(final Object model, final BeforeRemoveEvent event) throws ListenerException {

		try {
			if (model instanceof Language) {
				removeDynamicFieldLanguage((Language) model);

			} else if (model instanceof Enumeration) {
				removeDynamicFieldEnumeration((Enumeration) model);

			} else if (model instanceof UserGroup) {
				removeMenuUserGroup((UserGroup) model);

			} else if (model instanceof User) {
				removeCommentUser((User) model);

			}

		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}

	}

	private void removeDynamicFieldLanguage(Language model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DynamicField.LANGUAGE + "." + Language.UUID, model.getUuid());
		List<DynamicField> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, DynamicField.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getLanguage() != null)
					if (entities.get(i).getLanguage().getUuid().equals(model.getUuid())) {
						entities.get(i).setLanguage(null);
						modelService.saveEntityQuietly(entities.get(i), DynamicField.class);
					}
			}
	}

	private void removeDynamicFieldEnumeration(Enumeration model) throws Exception {
		Specification<DynamicField> specification = new Specification<DynamicField>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<DynamicField> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(DynamicField.ENUMERATIONS, JoinType.LEFT).get(GenericEntity.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<DynamicField> entities = modelService.findBySpecificationBySort(specification, null, DynamicField.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				boolean removed = false;
				for (int j = 0; j < entities.get(i).getEnumerations().size(); j++) {
					if (entities.get(i).getEnumerations().get(j).getUuid().equals(model.getUuid())) {
						entities.get(i).getEnumerations().remove(j);
						removed = true;
						break;
					}
				}

				if (removed)
					modelService.saveEntityQuietly(entities.get(i), DynamicField.class);
			}
	}

	private void removeMenuUserGroup(UserGroup model) throws Exception {
		Specification<Menu> specification = new Specification<Menu>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(Menu.USER_GROUPS, JoinType.LEFT).get(GenericEntity.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<Menu> entities = modelService.findBySpecificationBySort(specification, null, Menu.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				boolean removed = false;
				for (int j = 0; j < entities.get(i).getUserGroups().size(); j++) {
					if (entities.get(i).getUserGroups().get(j).getUuid().equals(model.getUuid())) {
						entities.get(i).getUserGroups().remove(j);
						removed = true;
						break;
					}
				}

				if (removed)
					modelService.saveEntityQuietly(entities.get(i), Menu.class);
			}
	}

	private void removeCommentUser(User model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Comment.USER + "." + GenericEntity.UUID, model.getUuid());
		List<Comment> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Comment.class);

		if (entities != null)
			for (Comment comment : entities) {
				modelService.saveEntityQuietly(comment, Comment.class);
			}

	}
}

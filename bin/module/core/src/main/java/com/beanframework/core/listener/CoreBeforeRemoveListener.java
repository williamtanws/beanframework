package com.beanframework.core.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.comment.domain.Comment;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.ListenerException;
import com.beanframework.common.registry.BeforeRemoveEvent;
import com.beanframework.common.registry.BeforeRemoveListener;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.imex.domain.Imex;
import com.beanframework.language.domain.Language;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;

public class CoreBeforeRemoveListener implements BeforeRemoveListener {

	@Autowired
	private ModelService modelService;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Override
	public void beforeRemove(final Object model, final BeforeRemoveEvent event) throws ListenerException {

		try {
			if (model instanceof Language) {
				Language language = (Language) model;
				removeDynamicFieldLanguage(language);

			} else if (model instanceof Enumeration) {
				Enumeration enumeration = (Enumeration) model;
				removeDynamicFieldEnumeration(enumeration);

			} else if (model instanceof UserGroup) {
				UserGroup userGroup = (UserGroup) model;
				removeMenuUserGroup(userGroup);
				removeUser(userGroup);

			} else if (model instanceof User) {
				User user = (User) model;
				removeCommentUser(user);

				File mediaFolder = new File(PROFILE_PICTURE_LOCATION + File.separator + user.getUuid().toString());
				FileUtils.deleteQuietly(mediaFolder);

			} else if (model instanceof Media) {
				Media media = (Media) model;
				removeMedia(media);

			} else if (model instanceof Imex) {
				Imex imex = (Imex) model;
				Hibernate.initialize(imex.getMedias());
				removeMedia(imex.getMedias());

			} else if (model instanceof UserRight) {
				UserRight userRight = (UserRight) model;
				removeUserGroup(userRight);

			} else if (model instanceof UserPermission) {
				UserPermission userPermission = (UserPermission) model;
				removeUserGroup(userPermission);

			} else if (model instanceof DynamicFieldSlot) {
				DynamicFieldSlot dynamicFieldSlot = (DynamicFieldSlot) model;
				removeDynamicFieldTemplate(dynamicFieldSlot);

			}

		} catch (Exception e) {
			throw new ListenerException(e.getMessage(), e);
		}
	}

	private void removeDynamicFieldTemplate(DynamicFieldSlot model) throws Exception {
		Specification<DynamicFieldTemplate> specification = new Specification<DynamicFieldTemplate>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<DynamicFieldTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(DynamicFieldTemplate.DYNAMIC_FIELD_SLOTS, JoinType.LEFT).get(GenericEntity.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<DynamicFieldTemplate> entities = modelService.findBySpecificationBySort(specification, null, DynamicFieldTemplate.class);

		for (int i = 0; i < entities.size(); i++) {

			boolean removed = false;
			for (int j = 0; j < entities.get(i).getDynamicFieldSlots().size(); j++) {
				if (entities.get(i).getDynamicFieldSlots().get(j).getUuid().equals(model.getUuid())) {
					entities.get(i).getDynamicFieldSlots().remove(j);
					removed = true;
					break;
				}
			}

			if (removed)
				modelService.saveEntity(entities.get(i), DynamicFieldTemplate.class);
		}
	}

	private void removeUser(UserGroup model) throws Exception {
		Specification<User> specification = new Specification<User>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(User.USER_GROUPS, JoinType.LEFT).get(GenericEntity.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<User> entities = modelService.findBySpecificationBySort(specification, null, User.class);

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
				modelService.saveEntityQuietly(entities.get(i), User.class);
		}
	}

	private void removeUserGroup(UserPermission model) throws Exception {
		Specification<UserGroup> specification = new Specification<UserGroup>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(UserGroup.USER_AUTHORITIES, JoinType.LEFT).get(UserAuthority.USER_PERMISSION).get(GenericEntity.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<UserGroup> entities = modelService.findBySpecificationBySort(specification, null, UserGroup.class);

		for (int i = 0; i < entities.size(); i++) {

			boolean removed = false;
			for (int j = 0; j < entities.get(i).getUserAuthorities().size(); j++) {
				if (entities.get(i).getUserAuthorities().get(j).getUserPermission().getUuid().equals(model.getUuid())) {
					entities.get(i).getUserAuthorities().get(j).setUserPermission(null);
					removed = true;
					break;
				}
			}

			if (removed)
				modelService.saveEntityQuietly(entities.get(i), UserGroup.class);
		}
	}

	private void removeUserGroup(UserRight model) throws Exception {
		Specification<UserGroup> specification = new Specification<UserGroup>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(UserGroup.USER_AUTHORITIES, JoinType.LEFT).get(UserAuthority.USER_RIGHT).get(GenericEntity.UUID).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<UserGroup> entities = modelService.findBySpecificationBySort(specification, null, UserGroup.class);
		
		for (int i = 0; i < entities.size(); i++) {

			boolean removed = false;
			for (int j = 0; j < entities.get(i).getUserAuthorities().size(); j++) {
				if (entities.get(i).getUserAuthorities().get(j).getUserRight().getUuid().equals(model.getUuid())) {
					entities.get(i).getUserAuthorities().get(j).setUserRight(null);
					removed = true;
					break;
				}
			}

			if (removed)
				modelService.saveEntityQuietly(entities.get(i), UserGroup.class);
		}

	}

	private void removeMedia(List<Media> medias) {
		for (Media media : medias) {
			removeMedia(media);
		}
	}

	private void removeMedia(Media media) {
		File mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getFolder() + File.separator + media.getUuid().toString());
		FileUtils.deleteQuietly(mediaFolder);
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

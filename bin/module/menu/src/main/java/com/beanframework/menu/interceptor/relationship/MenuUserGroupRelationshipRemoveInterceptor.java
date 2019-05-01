package com.beanframework.menu.interceptor.relationship;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;

public class MenuUserGroupRelationshipRemoveInterceptor extends AbstractRemoveInterceptor<UserGroup> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(UserGroup model, InterceptorContext context) throws InterceptorException {

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

		try {
			List<Menu> entities = modelService.findEntityBySpecification(specification, null, Menu.class);

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
					modelService.saveEntity(entities.get(i), Menu.class);
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}

	}
}

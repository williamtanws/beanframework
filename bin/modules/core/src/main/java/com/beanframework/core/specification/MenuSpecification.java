package com.beanframework.core.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.common.specification.CommonSpecification;
import com.beanframework.menu.domain.Menu;

public class MenuSpecification extends CommonSpecification {

	public static <T> AbstractSpecification<T> getMenuByEnabledByUserGroup(UUID parent, Set<UUID> userGroupsUuid) {

		return new AbstractSpecification<T>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				if(parent == null) {
					predicates.add(cb.and(root.get(Menu.PARENT).isNull()));
				}
				else {
					predicates.add(cb.and(cb.equal(root.get(Menu.PARENT).get(Menu.UUID), parent)));
				}
				predicates.add(cb.isTrue(root.get(Menu.ENABLED)));
				
				if (userGroupsUuid != null && userGroupsUuid.isEmpty() == false) {
					predicates.add(cb.and(root.join(Menu.USER_GROUPS, JoinType.LEFT).in(userGroupsUuid)));
				}
				
				return cb.and(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			}

			public String toString() {
				return "userGroupUuid[" + userGroupsUuid.toString() + "]" + ", getPageSpecificationByUserGroup";
			}

			@Override
			public List<Selection<?>> toSelection(Root<T> root) {
				
				return null;
			}

		};
	}
}

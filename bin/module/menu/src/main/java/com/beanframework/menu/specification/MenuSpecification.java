package com.beanframework.menu.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.beanframework.common.utils.AbstractSpecification;
import com.beanframework.menu.domain.Menu;
import com.beanframework.user.domain.UserGroup;

public class MenuSpecification extends AbstractSpecification {

	public static Specification<Menu> getMenuByUserGroup(UUID parent, Set<UUID> userGroupsUuid) {

		return new Specification<Menu>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				if(parent == null) {
					predicates.add(cb.and(root.get(Menu.PARENT).isNull()));
				}
				else {
					predicates.add(cb.and(cb.equal(root.get(Menu.PARENT).get(Menu.UUID), parent)));
				}
				predicates.add(cb.isTrue(root.get(Menu.ENABLED)));
				
				if (userGroupsUuid != null && userGroupsUuid.isEmpty() == false) {
					predicates.add(cb.and(root.join(Menu.USER_GROUPS, JoinType.LEFT).get(UserGroup.UUID).in(userGroupsUuid)));
				}
				
				return cb.and(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			}

			public String toString() {
				return "userGroupUuid[" + userGroupsUuid.toString() + "]" + ", getSpecificationByUserGroup";
			}

		};
	}
}

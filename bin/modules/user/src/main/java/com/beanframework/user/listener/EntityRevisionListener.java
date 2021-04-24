package com.beanframework.user.listener;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.beanframework.common.domain.Auditor;
import com.beanframework.user.domain.RevisionsEntity;
import com.beanframework.user.domain.User;

public class EntityRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object o) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			User user = (User) auth.getPrincipal();
			if (user.getUuid() != null) {
				if (o instanceof RevisionsEntity) {
					Auditor auditor = new Auditor();
					auditor.setUuid(user.getUuid());
					((RevisionsEntity) o).setLastModifiedBy(auditor);
				}
			}
		}
	}
}
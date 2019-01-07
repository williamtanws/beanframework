package com.beanframework.user.domain;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beanframework.common.domain.Auditor;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Auditor> {

	@Override
	public Optional<Auditor> getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			User user = (User) auth.getPrincipal();
			if (user.getUuid() == null) {
				return Optional.empty();
			}
			try {
				Auditor auditor = new Auditor();
				auditor.setUuid(user.getUuid());
				return Optional.of(auditor);
			} catch (Exception e) {
				e.printStackTrace();
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}

}

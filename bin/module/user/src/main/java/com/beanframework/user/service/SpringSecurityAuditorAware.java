package com.beanframework.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.beanframework.common.domain.Auditor;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<Auditor> {

	@Autowired
	private ModelService modelService;

	@Override
	public Optional<Auditor> getCurrentAuditor() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth != null) {
				User user = (User) auth.getPrincipal();

				if (user.getUuid() != null) {
					Auditor auditor = modelService.findOneEntityByUuid(user.getUuid(), Auditor.class);

					if (auditor != null) {
						return Optional.of(auditor);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.empty();
	}

}

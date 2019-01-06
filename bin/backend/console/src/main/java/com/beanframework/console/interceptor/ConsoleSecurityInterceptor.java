package com.beanframework.console.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.LicenseWebConstants;
import com.mchange.v1.lang.BooleanUtils;

public class ConsoleSecurityInterceptor extends HandlerInterceptorAdapter {

	UrlPathHelper urlPathHelper = new UrlPathHelper();
	Logger logger = LoggerFactory.getLogger(ConsoleSecurityInterceptor.class);
	
	@Autowired
	private ModelService modelService;

	@Value(ConsoleWebConstants.Path.LOGIN)
	private String PATH_LOGIN;

	@Value(LicenseWebConstants.Path.LICENSE)
	private String PATH_LICENSE;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.getPrincipal() instanceof Admin) {
			String path = urlPathHelper.getLookupPathForRequest(request);

			if (path != null && path.equalsIgnoreCase(PATH_LICENSE) == false
					&& path.equalsIgnoreCase(PATH_LOGIN) == false) {
				
				
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Configuration.ID, LicenseWebConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
				
				Configuration license = modelService.findOneDtoByProperties(properties, Configuration.class);

				if (license == null || BooleanUtils.parseBoolean(license.getValue()) == false) {
					response.sendRedirect(PATH_LICENSE);
					return false;
				}
			}

		}

		return true;
	}
}

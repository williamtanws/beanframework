package com.beanframework.cockpit.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import com.beanframework.cockpit.WebCockpitConstants;

@Component
public class CockpitSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Value(WebCockpitConstants.Path.COCKPIT)
	private String PATH_COCKPIT;

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);

		if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
			if(StringUtils.isNotBlank(savedRequest.getQueryString())) {
				getRedirectStrategy().sendRedirect(request, response, savedRequest.getRequestURL()+"?"+savedRequest.getQueryString());
			}
			else {
				getRedirectStrategy().sendRedirect(request, response, savedRequest.getRequestURL());
			}
		} else {
			getRedirectStrategy().sendRedirect(request, response, PATH_COCKPIT);
		}
	}
}
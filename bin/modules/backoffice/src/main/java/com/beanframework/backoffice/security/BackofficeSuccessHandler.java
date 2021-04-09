package com.beanframework.backoffice.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.BackofficeWebConstants;

@Component
public class BackofficeSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Value(BackofficeWebConstants.Path.BACKOFFICE)
	private String PATH_BACKOFFICE;

//	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

		// Redirect to requested path
//		DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);
//
//		if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
//			if (StringUtils.isNotBlank(savedRequest.getQueryString())) {
//				getRedirectStrategy().sendRedirect(request, response, savedRequest.getRequestURL() + "?" + savedRequest.getQueryString());
//			} else {
//				getRedirectStrategy().sendRedirect(request, response, savedRequest.getRequestURL());
//			}
//		} else {
//			getRedirectStrategy().sendRedirect(request, response, PATH_BACKOFFICE);
//		}
		
		getRedirectStrategy().sendRedirect(request, response, PATH_BACKOFFICE);
	}
}
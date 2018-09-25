package com.beanframework.backoffice.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.WebBackofficeConstants;

@Component
public class BackofficeSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Value(WebBackofficeConstants.Path.BACKOFFICE)
	private String PATH_BACKOFFICE;

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		DefaultSavedRequest savedRequest = (DefaultSavedRequest) requestCache.getRequest(request, response);

		if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
			//super.onAuthenticationSuccess(request, response, authentication);
			getRedirectStrategy().sendRedirect(request, response, PATH_BACKOFFICE);
		} else {
			getRedirectStrategy().sendRedirect(request, response, PATH_BACKOFFICE);
		}
	}
}

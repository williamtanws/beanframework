package com.beanframework.backoffice.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.facade.MenuFacade;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.employee.domain.Employee;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageService;
import com.beanframework.menu.domain.Menu;

public class BackofficeSecurityInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(BackofficeSecurityInterceptor.class);

	UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	@Autowired
	private MenuFacade menuFacade;
	
	@Autowired
	private LanguageService languageService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.getPrincipal() instanceof Employee && modelAndView != null) {

			getMenuNavigation(request, modelAndView);

			getLanguage(modelAndView);
		}
	}

	protected void getMenuNavigation(HttpServletRequest request, ModelAndView modelAndView) throws BusinessException {
		try {
			List<Menu> menuNavigation = menuFacade.findMenuTreeByCurrentUser();
			modelAndView.getModelMap().addAttribute(BackofficeWebConstants.Model.MENU_NAVIGATION, menuNavigation);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	protected void getLanguage(ModelAndView modelAndView) throws Exception {
		try {
			Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
			sorts.put(Language.SORT, Sort.Direction.ASC);

			List<Language> languages = languageService.findEntityBySorts(sorts, false);
			modelAndView.getModelMap().addAttribute(BackofficeWebConstants.Model.MODULE_LANGUAGES, languages);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}

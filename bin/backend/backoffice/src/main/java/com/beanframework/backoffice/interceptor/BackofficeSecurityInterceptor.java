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
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.language.domain.Language;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuFacade;

public class BackofficeSecurityInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(BackofficeSecurityInterceptor.class);

	UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Autowired
	private MenuFacade menuFacade;

	@Autowired
	private ModelService modelService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Retrieve navigation tree by user group
		if (auth != null && auth.getPrincipal() instanceof Employee && modelAndView != null) {
			Employee employee = (Employee) auth.getPrincipal();

			getMenuNavigation(request, modelAndView, employee);

			getLanguage(modelAndView);
		}
	}

	protected void getMenuNavigation(HttpServletRequest request, ModelAndView modelAndView, Employee employee)
			throws BusinessException {
		if (employee.getUserGroups().isEmpty() == false) {
			List<Menu> menuNavigation = menuFacade.findDtoMenuTreeByCurrentUser();
			modelAndView.getModelMap().addAttribute(BackofficeWebConstants.Model.MENU_NAVIGATION, menuNavigation);
		}
	}

	protected void getLanguage(ModelAndView modelAndView) throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(Language.SORT, Sort.Direction.ASC);

		List<Language> languages = modelService.findDtoByPropertiesAndSorts(null, sorts, null, null, Language.class);
		modelAndView.getModelMap().addAttribute(BackofficeWebConstants.Model.MODULE_LANGUAGES, languages);
	}
}

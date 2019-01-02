package com.beanframework.configuration.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ModelService modelService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if (modelAndView != null) {

			Map<String, String> maps = new HashMap<String, String>();

			List<Configuration> models = modelService.findDtoAll(Configuration.class);
			for (Configuration configuration : models) {
				maps.put(configuration.getId(), configuration.getValue());
			}

			modelAndView.getModelMap().addAttribute("configurationService", maps);
		}
	}
}

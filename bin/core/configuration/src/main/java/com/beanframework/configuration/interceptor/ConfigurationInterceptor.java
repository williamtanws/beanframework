package com.beanframework.configuration.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;

public class ConfigurationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ConfigurationService configurationService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if (modelAndView != null) {

			Map<String, String> maps = new HashMap<String, String>();

			List<Configuration> entities = configurationService.findAllEntity();
						
			for (Configuration configuration : entities) {
				maps.put(configuration.getId(), configuration.getValue());
			}

			modelAndView.getModelMap().addAttribute("configurationService", maps);
		}
	}
}

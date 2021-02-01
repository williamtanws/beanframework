package com.beanframework.console.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beanframework.console.LoggingWebConstants;

@PreAuthorize("isAuthenticated()")
@Controller
public class LoggingController {

	@Autowired
	private LoggersEndpoint loggersEndpoint;

	@Value(LoggingWebConstants.View.LOGGING)
	private String VIEW_LOGGING;

	@RequestMapping(LoggingWebConstants.Path.LOGGING)
	public String loggers(Model model) throws IOException {

		model.addAttribute("loggers", loggersEndpoint.loggers());

		return VIEW_LOGGING;
	}
}

package com.beanframework.console.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.LicenseWebConstants;

@Controller
public class ConsoleController {

	@Value(ConsoleWebConstants.View.LOGIN)
	private String VIEW_CONSOLE_LOGIN;

	@Value(ConsoleWebConstants.View.CONSOLE)
	private String VIEW_CONSOLE;

	@Autowired
	private ModelService modelService;

	@RequestMapping(ConsoleWebConstants.Path.LOGIN)
	public String login() {
		return VIEW_CONSOLE_LOGIN;
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(ConsoleWebConstants.Path.CONSOLE)
	public String console(Model model, RedirectAttributes redirectAttributes) throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, LicenseWebConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);

		Configuration license = modelService.findOneByProperties(properties, Configuration.class);

		if (license == null || BooleanUtils.parseBoolean(license.getValue()) == false) {
			model.addAttribute("showlicense", true);
		}

		return VIEW_CONSOLE;
	}
}

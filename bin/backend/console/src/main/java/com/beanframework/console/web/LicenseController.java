package com.beanframework.console.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.LicenseWebConstants;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.facade.ConfigurationFacade;

@Controller
public class LicenseController extends AbstractController {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@Value(LicenseWebConstants.Path.LICENSE)
	private String PATH_LICENSE;

	@Value(LicenseWebConstants.View.LICENSE)
	private String VIEW_LICENSE;

	public boolean isLicenseAccepted() throws Exception {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, LicenseWebConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
		ConfigurationDto configuration = configurationFacade.findOneProperties(properties);

		if (configuration == null) {
			return false;
		} else if (Boolean.parseBoolean(configuration.getValue())) {
			return true;
		} else {
			return false;
		}
	}

	@GetMapping(value = LicenseWebConstants.Path.LICENSE)
	public String view(@Valid @ModelAttribute(LicenseWebConstants.ModelAttribute.LICENSE) ConfigurationDto configuration, Model model, @RequestParam Map<String, Object> allRequestParams,
			RedirectAttributes redirectAttributes, HttpServletRequest request, BindingResult bindingResult) {
		try {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Configuration.ID, LicenseWebConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			configuration = configurationFacade.findOneProperties(properties);
			if (configuration == null) {
				configuration = new ConfigurationDto();
			}

			model.addAttribute(LicenseWebConstants.Model.LICENSE, configuration);
		} catch (Exception e) {
			model.addAttribute(ConsoleWebConstants.Model.ERROR, e.getMessage());
		}

		return VIEW_LICENSE;
	}

	@PostMapping(value = LicenseWebConstants.Path.LICENSE)
	public RedirectView accept(@Valid @ModelAttribute(LicenseWebConstants.ModelAttribute.LICENSE) ConfigurationDto configuration, Model model, @RequestParam Map<String, Object> allRequestParams,
			RedirectAttributes redirectAttributes, HttpServletRequest request, BindingResult bindingResult) {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Configuration.ID, LicenseWebConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			ConfigurationDto existingConfiguration = configurationFacade.findOneProperties(properties);

			if (existingConfiguration == null) {
				existingConfiguration = new ConfigurationDto();
				existingConfiguration.setId(LicenseWebConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			}

			if (BooleanUtils.parseBoolean(configuration.getValue())) {
				existingConfiguration.setValue("true");
			} else {
				existingConfiguration.setValue("false");
			}

			if (existingConfiguration.getUuid() == null) {
				configurationFacade.create(existingConfiguration);
			} else {
				configurationFacade.update(existingConfiguration);
			}
			addSuccessMessage(redirectAttributes, LicenseWebConstants.Locale.ACCEPT_SUCCESS);
		} catch (Exception e) {
			addErrorMessage(ConfigurationDto.class, e.getMessage(), bindingResult, redirectAttributes);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LICENSE);
		return redirectView;
	}
}

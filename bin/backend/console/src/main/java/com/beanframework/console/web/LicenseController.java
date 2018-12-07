package com.beanframework.console.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationFacade;
import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.WebLicenseConstants;

@Controller
public class LicenseController {

	@Autowired
	private ConfigurationFacade configurationFacade;
	
	@Autowired
	private LocaleMessageService localeMessageService;
	
	@Value(WebLicenseConstants.Path.LICENSE)
	private String PATH_LICENSE;
	
	@Value(WebLicenseConstants.View.LICENSE)
	private String VIEW_LICENSE;

	public boolean isLicenseAccepted() {
		Configuration configuration = configurationFacade.findById(WebLicenseConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);

		if (configuration == null) {
			return false;
		} else if (Boolean.parseBoolean(configuration.getValue())) {
			return true;
		} else {
			return false;
		}
	}

	@GetMapping(value = WebLicenseConstants.Path.LICENSE)
	public String view(@ModelAttribute(WebLicenseConstants.ModelAttribute.LICENSE) Configuration configuration, Model model, @RequestParam Map<String, Object> allRequestParams,
			RedirectAttributes redirectAttributes, HttpServletRequest request, BindingResult bindingResult) {
		try {
			configuration = configurationFacade.findById(WebLicenseConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			if(configuration == null) {
				configuration = configurationFacade.create();
			}

			model.addAttribute(WebLicenseConstants.Model.LICENSE, configuration);
		} catch (Exception e) {
			model.addAttribute(WebConsoleConstants.Model.ERROR, e.getMessage());
		}

		return VIEW_LICENSE;
	}

	@PostMapping(value = WebLicenseConstants.Path.LICENSE)
	public RedirectView accept(@ModelAttribute(WebLicenseConstants.ModelAttribute.LICENSE) Configuration configuration, Model model, @RequestParam Map<String, Object> allRequestParams,
			RedirectAttributes redirectAttributes, HttpServletRequest request, BindingResult bindingResult) {

		try {
			Configuration existingConfiguration = configurationFacade.findById(WebLicenseConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			
			if(existingConfiguration == null) {
				existingConfiguration = configurationFacade.create();
				existingConfiguration.setId(WebLicenseConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			}
			
			if(BooleanUtils.parseBoolean(configuration.getValue())) {
				existingConfiguration.setValue("true");
			}
			else {
				existingConfiguration.setValue("false");
			}
			
			configuration = configurationFacade.save(existingConfiguration, bindingResult);
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.SUCCESS, localeMessageService.getMessage(WebLicenseConstants.Locale.ACCEPT_SUCCESS));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR, e.getMessage());
		}
		
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LICENSE);
		return redirectView;
	}
}

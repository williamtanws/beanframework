package com.beanframework.console.web;

import java.util.HashMap;
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

import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.WebLicenseConstants;

@Controller
public class LicenseController extends AbstractController {
	
	@Autowired
	private ModelService modelService;
	
	@Value(WebLicenseConstants.Path.LICENSE)
	private String PATH_LICENSE;
	
	@Value(WebLicenseConstants.View.LICENSE)
	private String VIEW_LICENSE;

	public boolean isLicenseAccepted() throws Exception {
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, WebLicenseConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
		
		Configuration configuration = modelService.findOneDtoByProperties(properties, Configuration.class);
		
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
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Configuration.ID, WebLicenseConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			
			configuration = modelService.findOneDtoByProperties(properties, Configuration.class);
			if(configuration == null) {
				configuration = modelService.create(Configuration.class);
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
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Configuration.ID, WebLicenseConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			
			Configuration existingConfiguration = modelService.findOneDtoByProperties(properties, Configuration.class);
						
			if(existingConfiguration == null) {
				existingConfiguration = modelService.create(Configuration.class);
				existingConfiguration.setId(WebLicenseConstants.CONFIGURATION_ID_LICENSE_ACCEPTED);
			}
			
			if(BooleanUtils.parseBoolean(configuration.getValue())) {
				existingConfiguration.setValue("true");
			}
			else {
				existingConfiguration.setValue("false");
			}
			
			modelService.saveDto(existingConfiguration, Configuration.class);
			addSuccessMessage(redirectAttributes, WebLicenseConstants.Locale.ACCEPT_SUCCESS);
		} catch (Exception e) {
			addErrorMessage(Configuration.class, e.getMessage(), bindingResult, redirectAttributes);
		}
		
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LICENSE);
		return redirectView;
	}
}

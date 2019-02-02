package com.beanframework.console.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.beanframework.common.exception.BusinessException;
import com.beanframework.console.ConfigurationWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.facade.ConfigurationFacade;
import com.beanframework.core.facade.ConfigurationFacade.ConfigurationPreAuthorizeEnum;

@Controller
public class ConfigurationController extends AbstractController {

	@Autowired
	private ConfigurationFacade configurationFacade;

	@Value(ConfigurationWebConstants.Path.CONFIGURATION)
	private String PATH_CONFIGURATION;

	@Value(ConfigurationWebConstants.View.LIST)
	private String VIEW_CONFIGURATION_LIST;
	
	@ModelAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE)
	public ConfigurationDto update(Model model) throws Exception {
		model.addAttribute("create", false);
		return new ConfigurationDto();
	}

	@PreAuthorize(ConfigurationPreAuthorizeEnum.READ)
	@GetMapping(value = ConfigurationWebConstants.Path.CONFIGURATION)
	public String list(@ModelAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE) ConfigurationDto configurationUpdate, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {

		if (configurationUpdate.getUuid() != null) {

			ConfigurationDto existingConfiguration = configurationFacade.findOneByUuid(configurationUpdate.getUuid());

			if (existingConfiguration != null) {

				model.addAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE, existingConfiguration);
			} else {
				configurationUpdate.setUuid(null);
				addErrorMessage(model, ConsoleWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_CONFIGURATION_LIST;
	}
	
	@GetMapping(value = ConfigurationWebConstants.Path.CONFIGURATION, params = "create")
	public String createView(Model model) throws Exception {
		model.addAttribute("create", true);
		return VIEW_CONFIGURATION_LIST;
	}

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION, params = "create")
	public RedirectView create(@ModelAttribute(ConfigurationWebConstants.ModelAttribute.CREATE) ConfigurationDto configurationCreate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (configurationCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				configurationCreate = configurationFacade.create(configurationCreate);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(ConfigurationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(ConfigurationDto.UUID, configurationCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION);
		return redirectView;
	}

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION, params = "update")
	public RedirectView update(@ModelAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE) ConfigurationDto configurationUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (configurationUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				configurationUpdate = configurationFacade.update(configurationUpdate);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(ConfigurationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(ConfigurationDto.UUID, configurationUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION);
		return redirectView;
	}

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION, params = "delete")
	public RedirectView delete(@ModelAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE) ConfigurationDto configurationUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			configurationFacade.delete(configurationUpdate.getUuid());

			addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(ConfigurationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(ConfigurationWebConstants.ModelAttribute.UPDATE, configurationUpdate);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION);
		return redirectView;

	}
}

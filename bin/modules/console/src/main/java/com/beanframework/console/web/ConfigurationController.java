package com.beanframework.console.web;

import java.util.Map;

import javax.validation.Valid;

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

import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.console.ConfigurationWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.ConfigurationDto;
import com.beanframework.core.facade.ConfigurationFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class ConfigurationController extends AbstractController {

	@Autowired
	private ConfigurationFacade configurationFacade;
	
	@Value(ConfigurationWebConstants.Path.CONFIGURATION)
	private String PATH_CONFIGURATION;
	
	@Value(ConfigurationWebConstants.Path.CONFIGURATION_FORM)
	private String PATH_CONFIGURATION_FORM;

	@Value(ConfigurationWebConstants.View.PAGE)
	private String VIEW_CONFIGURATION;

	@Value(ConfigurationWebConstants.View.FORM)
	private String VIEW_CONFIGURATION_FORM;

	@GetMapping(value = ConfigurationWebConstants.Path.CONFIGURATION)
	public String page(@Valid @ModelAttribute(ConfigurationWebConstants.ModelAttribute.CONFIGURATION_DTO) ConfigurationDto dto, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		return VIEW_CONFIGURATION;
	}

	@GetMapping(value = ConfigurationWebConstants.Path.CONFIGURATION_FORM)
	public String form(@Valid @ModelAttribute(ConfigurationWebConstants.ModelAttribute.CONFIGURATION_DTO) ConfigurationDto dto, Model model) throws Exception {

		if (dto.getUuid() != null) {
			dto = configurationFacade.findOneByUuid(dto.getUuid());
		} else {
			dto = configurationFacade.createDto();
		}
		model.addAttribute(ConfigurationWebConstants.ModelAttribute.CONFIGURATION_DTO, dto);

		return VIEW_CONFIGURATION_FORM;
	}

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(ConfigurationWebConstants.ModelAttribute.CONFIGURATION_DTO) ConfigurationDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				dto = configurationFacade.create(dto);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(ConfigurationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION_FORM);
		return redirectView;
	}

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(ConfigurationWebConstants.ModelAttribute.CONFIGURATION_DTO) ConfigurationDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				dto = configurationFacade.update(dto);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(ConfigurationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION_FORM);
		return redirectView;
	}

	@PostMapping(value = ConfigurationWebConstants.Path.CONFIGURATION_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(ConfigurationWebConstants.ModelAttribute.CONFIGURATION_DTO) ConfigurationDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				configurationFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CONFIGURATION_FORM);
		return redirectView;

	}
}

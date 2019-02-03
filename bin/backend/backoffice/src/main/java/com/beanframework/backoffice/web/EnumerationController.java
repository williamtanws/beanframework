package com.beanframework.backoffice.web;

import java.util.Map;

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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EnumerationWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.facade.EnumerationFacade;

@Controller
public class EnumerationController extends AbstractController {

	@Autowired
	private EnumerationFacade enumerationFacade;

	@Value(EnumerationWebConstants.Path.ENUMERATION)
	private String PATH_ENUMERATION;

	@Value(EnumerationWebConstants.View.LIST)
	private String VIEW_ENUMERATION_LIST;

	@ModelAttribute(EnumerationWebConstants.ModelAttribute.UPDATE)
	public EnumerationDto update(Model model) throws Exception {
		model.addAttribute("create", false);
		return new EnumerationDto();
	}

	@GetMapping(value = EnumerationWebConstants.Path.ENUMERATION)
	public String list(@ModelAttribute(EnumerationWebConstants.ModelAttribute.UPDATE) EnumerationDto updateDto, Model model) throws Exception {
		model.addAttribute("create", false);
		
		if (updateDto.getUuid() != null) {

			EnumerationDto existsDto = enumerationFacade.findOneByUuid(updateDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(EnumerationWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				updateDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_ENUMERATION_LIST;
	}
	
	@GetMapping(value = EnumerationWebConstants.Path.ENUMERATION, params = "create")
	public String createView(Model model) throws Exception {
		model.addAttribute("create", true);
		return VIEW_ENUMERATION_LIST;
	}

	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION, params = "create")
	public RedirectView create(@ModelAttribute(EnumerationWebConstants.ModelAttribute.CREATE) EnumerationDto createDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = enumerationFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EnumerationDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ENUMERATION);
		return redirectView;
	}

	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION, params = "update")
	public RedirectView update(@ModelAttribute(EnumerationWebConstants.ModelAttribute.UPDATE) EnumerationDto updateDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				updateDto = enumerationFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EnumerationDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ENUMERATION);
		return redirectView;
	}

	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION, params = "delete")
	public RedirectView delete(@ModelAttribute(EnumerationWebConstants.ModelAttribute.UPDATE) EnumerationDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			enumerationFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(EnumerationDto.UUID, updateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ENUMERATION);
		return redirectView;

	}
}

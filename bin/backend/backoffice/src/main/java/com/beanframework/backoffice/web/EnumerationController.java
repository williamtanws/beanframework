package com.beanframework.backoffice.web;

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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EnumerationWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.facade.EnumerationFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class EnumerationController extends AbstractController {

	@Autowired
	private EnumerationFacade enumerationFacade;
	
	@Value(EnumerationWebConstants.Path.ENUMERATION_PAGE)
	private String PATH_ENUMERATION_PAGE;
	
	@Value(EnumerationWebConstants.Path.ENUMERATION_FORM)
	private String PATH_ENUMERATION_FORM;

	@Value(EnumerationWebConstants.View.PAGE)
	private String VIEW_ENUMERATION_PAGE;

	@Value(EnumerationWebConstants.View.FORM)
	private String VIEW_ENUMERATION_FORM;

	@GetMapping(value = EnumerationWebConstants.Path.ENUMERATION_PAGE)
	public String list(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		return VIEW_ENUMERATION_PAGE;
	}

	@GetMapping(value = EnumerationWebConstants.Path.ENUMERATION_FORM)
	public String createView(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model) throws Exception {

		if (enumerationDto.getUuid() != null) {
			enumerationDto = enumerationFacade.findOneByUuid(enumerationDto.getUuid());
		} else {
			enumerationDto = enumerationFacade.createDto();
		}
		model.addAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO, enumerationDto);

		return VIEW_ENUMERATION_FORM;
	}

	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (enumerationDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				enumerationDto = enumerationFacade.create(enumerationDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EnumerationDto.UUID, enumerationDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ENUMERATION_FORM);
		return redirectView;
	}

	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (enumerationDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				enumerationDto = enumerationFacade.update(enumerationDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EnumerationDto.UUID, enumerationDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ENUMERATION_FORM);
		return redirectView;
	}

	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (enumerationDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				enumerationFacade.delete(enumerationDto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ENUMERATION_FORM);
		return redirectView;

	}
}

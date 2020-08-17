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
import com.beanframework.core.facade.EnumerationFacade.EnumPreAuthorizeEnum;

@Controller
public class EnumerationController extends AbstractController {

	@Autowired
	private EnumerationFacade enumerationFacade;

	@Value(EnumerationWebConstants.Path.ENUMERATION)
	private String PATH_ENUMERATION;

	@Value(EnumerationWebConstants.View.LIST)
	private String VIEW_ENUMERATION_LIST;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = EnumerationWebConstants.Path.ENUMERATION)
	public String list(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (enumerationDto.getUuid() != null) {

			EnumerationDto existsDto = enumerationFacade.findOneByUuid(enumerationDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO, existsDto);
			} else {
				enumerationDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_ENUMERATION_LIST;
	}

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = EnumerationWebConstants.Path.ENUMERATION, params = "create")
	public String createView(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model) throws Exception {

		enumerationDto = enumerationFacade.createDto();
		model.addAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO, enumerationDto);
		model.addAttribute("create", true);

		return VIEW_ENUMERATION_LIST;
	}

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION, params = "create")
	public RedirectView create(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (enumerationDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
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
		redirectView.setUrl(PATH_ENUMERATION);
		return redirectView;
	}

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION, params = "update")
	public RedirectView update(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (enumerationDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
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
		redirectView.setUrl(PATH_ENUMERATION);
		return redirectView;
	}

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = EnumerationWebConstants.Path.ENUMERATION, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			enumerationFacade.delete(enumerationDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(EnumerationDto.UUID, enumerationDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ENUMERATION);
		return redirectView;

	}
}

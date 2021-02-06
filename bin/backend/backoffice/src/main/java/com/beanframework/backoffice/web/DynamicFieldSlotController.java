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
import com.beanframework.backoffice.DynamicFieldSlotWebConstants;
import com.beanframework.backoffice.DynamicFieldSlotWebConstants.DynamicFieldSlotPreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.facade.DynamicFieldSlotFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class DynamicFieldSlotController extends AbstractController {

	@Autowired
	private DynamicFieldSlotFacade dynamicFieldSlotFacade;

	@Value(DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT)
	private String PATH_DYNAMICFIELDSLOT_PAGE;

	@Value(DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM)
	private String PATH_DYNAMICFIELDSLOT_FORM;

	@Value(DynamicFieldSlotWebConstants.View.DYNAMICFIELDSLOT)
	private String VIEW_DYNAMICFIELDSLOT_PAGE;

	@Value(DynamicFieldSlotWebConstants.View.DYNAMICFIELDSLOT_FORM)
	private String VIEW_DYNAMICFIELDSLOT_FORM;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT)
	public String page(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_DYNAMICFIELDSLOT_PAGE;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM)
	public String form(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model) throws Exception {

		if (dynamicFieldSlotDto.getUuid() != null) {
			dynamicFieldSlotDto = dynamicFieldSlotFacade.findOneByUuid(dynamicFieldSlotDto.getUuid());
		} else {
			dynamicFieldSlotDto = dynamicFieldSlotFacade.createDto();
		}
		model.addAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO, dynamicFieldSlotDto);

		return VIEW_DYNAMICFIELDSLOT_FORM;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dynamicFieldSlotDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				dynamicFieldSlotDto = dynamicFieldSlotFacade.create(dynamicFieldSlotDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldSlotDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldSlotDto.UUID, dynamicFieldSlotDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT_FORM);
		return redirectView;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dynamicFieldSlotDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				dynamicFieldSlotDto = dynamicFieldSlotFacade.update(dynamicFieldSlotDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldSlotDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldSlotDto.UUID, dynamicFieldSlotDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT_FORM);
		return redirectView;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dynamicFieldSlotDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				dynamicFieldSlotFacade.delete(dynamicFieldSlotDto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldSlotDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT_FORM);
		return redirectView;

	}
}

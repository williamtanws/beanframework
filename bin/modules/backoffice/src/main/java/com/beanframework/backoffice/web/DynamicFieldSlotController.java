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
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.facade.DynamicFieldSlotFacade;


@Controller
public class DynamicFieldSlotController extends AbstractController {

	@Autowired
	private DynamicFieldSlotFacade dynamicFieldSlotFacade;

	@Value(DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT)
	private String PATH_DYNAMICFIELDSLOT;

	@Value(DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM)
	private String PATH_DYNAMICFIELDSLOT_FORM;

	@Value(DynamicFieldSlotWebConstants.View.DYNAMICFIELDSLOT)
	private String VIEW_DYNAMICFIELDSLOT;

	@Value(DynamicFieldSlotWebConstants.View.DYNAMICFIELDSLOT_FORM)
	private String VIEW_DYNAMICFIELDSLOT_FORM;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT)
	public String page(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_DYNAMICFIELDSLOT;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM)
	public String form(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dto, Model model) throws Exception {

		if (dto.getUuid() != null) {
			dto = dynamicFieldSlotFacade.findOneByUuid(dto.getUuid());
		} else {
			dto = dynamicFieldSlotFacade.createDto();
		}
		model.addAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO, dto);

		return VIEW_DYNAMICFIELDSLOT_FORM;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				dto = dynamicFieldSlotFacade.create(dto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldSlotDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT_FORM);
		return redirectView;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				dto = dynamicFieldSlotFacade.update(dto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldSlotDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT_FORM);
		return redirectView;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				dynamicFieldSlotFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT_FORM);
		return redirectView;

	}
}

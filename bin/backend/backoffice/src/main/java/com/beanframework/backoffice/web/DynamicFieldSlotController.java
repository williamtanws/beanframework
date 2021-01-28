package com.beanframework.backoffice.web;

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
	private String PATH_DYNAMICFIELDSLOT;

	@Value(DynamicFieldSlotWebConstants.View.LIST)
	private String VIEW_DYNAMICFIELDSLOT_LIST;

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT)
	public String list(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (dynamicFieldSlotDto.getUuid() != null) {

			DynamicFieldSlotDto existsDto = dynamicFieldSlotFacade.findOneByUuid(dynamicFieldSlotDto.getUuid());

			if (existsDto != null) {

				model.addAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO, existsDto);
			} else {
				dynamicFieldSlotDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFIELDSLOT_LIST;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT, params = "create")
	public String createView(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model) throws Exception {

		dynamicFieldSlotDto = dynamicFieldSlotFacade.createDto();
		model.addAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO, dynamicFieldSlotDto);
		model.addAttribute("create", true);

		return VIEW_DYNAMICFIELDSLOT_LIST;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT, params = "create")
	public RedirectView create(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicFieldSlotDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
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
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT);
		return redirectView;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT, params = "update")
	public RedirectView update(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicFieldSlotDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
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
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT);
		return redirectView;
	}

	@PreAuthorize(DynamicFieldSlotPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = DynamicFieldSlotWebConstants.Path.DYNAMICFIELDSLOT, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(DynamicFieldSlotWebConstants.ModelAttribute.DYNAMICFIELDSLOT_DTO) DynamicFieldSlotDto dynamicFieldSlotDto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldSlotFacade.delete(dynamicFieldSlotDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldSlotDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldSlotDto.UUID, dynamicFieldSlotDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELDSLOT);
		return redirectView;

	}
}

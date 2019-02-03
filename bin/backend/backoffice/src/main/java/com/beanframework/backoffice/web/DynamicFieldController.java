package com.beanframework.backoffice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.DynamicFieldWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.facade.DynamicFieldFacade;

@Controller
public class DynamicFieldController extends AbstractController {

	@Autowired
	private DynamicFieldFacade dynamicFieldFacade;

	@Value(DynamicFieldWebConstants.Path.DYNAMICFIELD)
	private String PATH_DYNAMICFIELD;

	@Value(DynamicFieldWebConstants.View.LIST)
	private String VIEW_DYNAMICFIELD_LIST;

	@GetMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD)
	public String list(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (dynamicFieldDto.getUuid() != null) {

			DynamicFieldDto existsDto = dynamicFieldFacade.findOneByUuid(dynamicFieldDto.getUuid());

			if (existsDto != null) {

				model.addAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO, existsDto);
			} else {
				dynamicFieldDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFIELD_LIST;
	}

	@GetMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "create")
	public String createView(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto, Model model) throws Exception {

		dynamicFieldDto = dynamicFieldFacade.createDto();
		model.addAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO, dynamicFieldDto);
		model.addAttribute("create", true);

		return VIEW_DYNAMICFIELD_LIST;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "create")
	public RedirectView create(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicFieldDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				dynamicFieldDto = dynamicFieldFacade.create(dynamicFieldDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldDto.UUID, dynamicFieldDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "update")
	public RedirectView update(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicFieldDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				dynamicFieldDto = dynamicFieldFacade.update(dynamicFieldDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldDto.UUID, dynamicFieldDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "delete")
	public RedirectView delete(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldFacade.delete(dynamicFieldDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldDto.UUID, dynamicFieldDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;

	}
}

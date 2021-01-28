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
import com.beanframework.backoffice.DynamicFieldTemplateWebConstants;
import com.beanframework.backoffice.DynamicFieldTemplateWebConstants.DynamicFieldTemplatePreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.core.facade.DynamicFieldTemplateFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class DynamicFieldTemplateController extends AbstractController {

	@Autowired
	private DynamicFieldTemplateFacade dynamicFieldTemplateFacade;

	@Value(DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE)
	private String PATH_DYNAMICFILEDTEMPLATE;

	@Value(DynamicFieldTemplateWebConstants.View.LIST)
	private String VIEW_DYNAMICFILEDTEMPLATE_LIST;

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_READ)
	@GetMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE)
	public String list(@Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicfieldtemplateDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (dynamicfieldtemplateDto.getUuid() != null) {

			DynamicFieldTemplateDto existsDto = dynamicFieldTemplateFacade.findOneByUuid(dynamicfieldtemplateDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFILEDTEMPLATE_LIST;
	}

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE, params = "create")
	public String createView(@Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicfieldtemplateDto, Model model) throws Exception {

		dynamicfieldtemplateDto = dynamicFieldTemplateFacade.createDto();
		model.addAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO, dynamicfieldtemplateDto);
		model.addAttribute("create", true);

		return VIEW_DYNAMICFILEDTEMPLATE_LIST;
	}

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE, params = "create")
	public RedirectView create(@Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicfieldtemplateDto, Model model,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicfieldtemplateDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				dynamicfieldtemplateDto = dynamicFieldTemplateFacade.create(dynamicfieldtemplateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldTemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID, dynamicfieldtemplateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFILEDTEMPLATE);
		return redirectView;
	}

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE, params = "update")
	public RedirectView update(@Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicfieldtemplateDto, Model model,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicfieldtemplateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				dynamicfieldtemplateDto = dynamicFieldTemplateFacade.update(dynamicfieldtemplateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldTemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID, dynamicfieldtemplateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFILEDTEMPLATE);
		return redirectView;
	}

	@PreAuthorize(DynamicFieldTemplatePreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.DYNAMICFIELDTEMPLATE_DTO) DynamicFieldTemplateDto dynamicfieldtemplateDto, Model model,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldTemplateFacade.delete(dynamicfieldtemplateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldTemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID, dynamicfieldtemplateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFILEDTEMPLATE);
		return redirectView;

	}
}

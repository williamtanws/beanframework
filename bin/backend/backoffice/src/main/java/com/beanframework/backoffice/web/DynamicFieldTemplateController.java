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
import com.beanframework.backoffice.DynamicFieldTemplateWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.core.facade.DynamicFieldTemplateFacade;

@Controller
public class DynamicFieldTemplateController extends AbstractController {

	@Autowired
	private DynamicFieldTemplateFacade dynamicFieldTemplateFacade;

	@Value(DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE)
	private String PATH_DYNAMICFILEDTEMPLATE;

	@Value(DynamicFieldTemplateWebConstants.View.LIST)
	private String VIEW_DYNAMICFILEDTEMPLATE_LIST;

	@ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.UPDATE)
	public DynamicFieldTemplateDto update(Model model) throws Exception {
		model.addAttribute("create", false);
		return new DynamicFieldTemplateDto();
	}

	@GetMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE)
	public String list(@ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.UPDATE) DynamicFieldTemplateDto updateDto, Model model) throws Exception {
		model.addAttribute("create", false);
		
		if (updateDto.getUuid() != null) {

			DynamicFieldTemplateDto existsDto = dynamicFieldTemplateFacade.findOneByUuid(updateDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFILEDTEMPLATE_LIST;
	}

	@GetMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE, params = "create")
	public String createView(Model model) throws Exception {
		model.addAttribute("create", true);
		return VIEW_DYNAMICFILEDTEMPLATE_LIST;
	}

	@PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE, params = "create")
	public RedirectView create(@ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.CREATE) DynamicFieldTemplateDto createDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = dynamicFieldTemplateFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldTemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFILEDTEMPLATE);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE, params = "update")
	public RedirectView update(@ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.UPDATE) DynamicFieldTemplateDto updateDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				updateDto = dynamicFieldTemplateFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldTemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFILEDTEMPLATE);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldTemplateWebConstants.Path.DYNAMICFIELDTEMPLATE, params = "delete")
	public RedirectView delete(@ModelAttribute(DynamicFieldTemplateWebConstants.ModelAttribute.UPDATE) DynamicFieldTemplateDto updateDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldTemplateFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldTemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldTemplateDto.UUID, updateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFILEDTEMPLATE);
		return redirectView;

	}
}

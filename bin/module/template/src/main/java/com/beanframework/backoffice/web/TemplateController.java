package com.beanframework.backoffice.web;

import java.util.Map;

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

import com.beanframework.backoffice.TemplateWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.TemplateDto;
import com.beanframework.core.facade.TemplateFacade;
import com.beanframework.core.facade.TemplateFacade.TemplatePreAuthorizeEnum;

@Controller
public class TemplateController extends AbstractController {

	@Autowired
	private TemplateFacade templateFacade;

	@Value(TemplateWebConstants.Path.TEMPLATE)
	private String PATH_TEMPLATE;

	@Value(TemplateWebConstants.View.LIST)
	private String VIEW_TEMPLATE_LIST;

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_READ)
	@GetMapping(value = TemplateWebConstants.Path.TEMPLATE)
	public String list(@ModelAttribute(TemplateWebConstants.ModelAttribute.TEMPLATE_DTO) TemplateDto templateDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);

		if (templateDto.getUuid() != null) {

			TemplateDto existingTemplate = templateFacade.findOneByUuid(templateDto.getUuid());

			if (existingTemplate != null) {

				model.addAttribute(TemplateWebConstants.ModelAttribute.TEMPLATE_DTO, existingTemplate);
			} else {
				templateDto.setUuid(null);
				addErrorMessage(model, TemplateWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_TEMPLATE_LIST;
	}

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = TemplateWebConstants.Path.TEMPLATE, params = "create")
	public String createView(@ModelAttribute(TemplateWebConstants.ModelAttribute.TEMPLATE_DTO) TemplateDto templateDto, Model model) throws Exception {

		templateDto = templateFacade.createDto();
		model.addAttribute(TemplateWebConstants.ModelAttribute.TEMPLATE_DTO, templateDto);
		model.addAttribute("create", true);

		return VIEW_TEMPLATE_LIST;
	}

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = TemplateWebConstants.Path.TEMPLATE, params = "create")
	public RedirectView create(@ModelAttribute(TemplateWebConstants.ModelAttribute.TEMPLATE_DTO) TemplateDto templateDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (templateDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(TemplateWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				templateDto = templateFacade.create(templateDto);

				addSuccessMessage(redirectAttributes, TemplateWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(TemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(TemplateDto.UUID, templateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_TEMPLATE);
		return redirectView;
	}

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = TemplateWebConstants.Path.TEMPLATE, params = "update")
	public RedirectView update(@ModelAttribute(TemplateWebConstants.ModelAttribute.TEMPLATE_DTO) TemplateDto templateDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (templateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(TemplateWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				templateDto = templateFacade.update(templateDto);

				addSuccessMessage(redirectAttributes, TemplateWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(TemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(TemplateDto.UUID, templateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_TEMPLATE);
		return redirectView;
	}

	@PreAuthorize(TemplatePreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = TemplateWebConstants.Path.TEMPLATE, params = "delete")
	public RedirectView delete(@ModelAttribute(TemplateWebConstants.ModelAttribute.TEMPLATE_DTO) TemplateDto templateDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			templateFacade.delete(templateDto.getUuid());

			addSuccessMessage(redirectAttributes, TemplateWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(TemplateDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(TemplateDto.UUID, templateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_TEMPLATE);
		return redirectView;

	}
}

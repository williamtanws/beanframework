package com.beanframework.backoffice.web;

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
import com.beanframework.backoffice.LanguageWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.core.facade.LanguageFacade;
import com.beanframework.core.facade.LanguageFacade.LanguagePreAuthorizeEnum;

@Controller
public class LanguageController extends AbstractController {

	@Autowired
	private LanguageFacade languageFacade;

	@Value(LanguageWebConstants.Path.LANGUAGE)
	private String PATH_LANGUAGE;

	@Value(LanguageWebConstants.View.LIST)
	private String VIEW_LANGUAGE_LIST;

	@ModelAttribute(LanguageWebConstants.ModelAttribute.CREATE)
	public LanguageDto create() throws Exception {
		return new LanguageDto();
	}

	@ModelAttribute(LanguageWebConstants.ModelAttribute.UPDATE)
	public LanguageDto update() throws Exception {
		return new LanguageDto();
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.READ)
	@GetMapping(value = LanguageWebConstants.Path.LANGUAGE)
	public String list(@ModelAttribute(LanguageWebConstants.ModelAttribute.UPDATE) LanguageDto updateDto, Model model) throws Exception {

		if (updateDto.getUuid() != null) {

			LanguageDto existsDto = languageFacade.findOneByUuid(updateDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(LanguageWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				updateDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_LANGUAGE_LIST;
	}

	@PostMapping(value = LanguageWebConstants.Path.LANGUAGE, params = "create")
	public RedirectView create(@ModelAttribute(LanguageWebConstants.ModelAttribute.CREATE) LanguageDto createDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = languageFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(LanguageDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(LanguageDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PostMapping(value = LanguageWebConstants.Path.LANGUAGE, params = "update")
	public RedirectView update(@ModelAttribute(LanguageWebConstants.ModelAttribute.UPDATE) LanguageDto updateDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				updateDto = languageFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(LanguageDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(LanguageDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PostMapping(value = LanguageWebConstants.Path.LANGUAGE, params = "delete")
	public RedirectView delete(@ModelAttribute(LanguageWebConstants.ModelAttribute.UPDATE) LanguageDto updateDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		try {
			languageFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(LanguageDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(LanguageDto.UUID, updateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;

	}
}

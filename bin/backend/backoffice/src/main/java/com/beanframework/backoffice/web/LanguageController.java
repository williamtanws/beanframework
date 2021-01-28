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
import com.beanframework.backoffice.LanguageWebConstants;
import com.beanframework.backoffice.LanguageWebConstants.LanguagePreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.core.facade.LanguageFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class LanguageController extends AbstractController {

	@Autowired
	private LanguageFacade languageFacade;

	@Value(LanguageWebConstants.Path.LANGUAGE)
	private String PATH_LANGUAGE;

	@Value(LanguageWebConstants.View.LIST)
	private String VIEW_LANGUAGE_LIST;

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
	@GetMapping(value = LanguageWebConstants.Path.LANGUAGE)
	public String list(@Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (languageDto.getUuid() != null) {

			LanguageDto existsDto = languageFacade.findOneByUuid(languageDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_LANGUAGE_LIST;
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = LanguageWebConstants.Path.LANGUAGE, params = "create")
	public String createView(@Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto, Model model) throws Exception {

		languageDto = languageFacade.createDto();
		model.addAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO, languageDto);
		model.addAttribute("create", true);

		return VIEW_LANGUAGE_LIST;
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = LanguageWebConstants.Path.LANGUAGE, params = "create")
	public RedirectView create(@Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (languageDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				languageDto = languageFacade.create(languageDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(LanguageDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(LanguageDto.UUID, languageDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = LanguageWebConstants.Path.LANGUAGE, params = "update")
	public RedirectView update(@Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (languageDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				languageDto = languageFacade.update(languageDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(LanguageDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(LanguageDto.UUID, languageDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PreAuthorize(LanguagePreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = LanguageWebConstants.Path.LANGUAGE, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		try {
			languageFacade.delete(languageDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(LanguageDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(LanguageDto.UUID, languageDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;

	}
}

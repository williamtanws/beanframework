package com.beanframework.backoffice.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebLanguageConstants;
import com.beanframework.backoffice.domain.LanguageSearch;
import com.beanframework.backoffice.service.BackofficeModuleFacade;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageFacade;

@Controller
public class LanguageController {

	@Autowired
	private LanguageFacade languageFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Autowired
	private BackofficeModuleFacade backofficeModuleFacade;

	@Value(WebLanguageConstants.Path.LANGUAGE)
	private String PATH_LANGUAGE;

	@Value(WebLanguageConstants.View.LIST)
	private String VIEW_LANGUAGE_LIST;

	@Value(WebLanguageConstants.LIST_SIZE)
	private int MODULE_LANGUAGE_LIST_SIZE;

	private Page<Language> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_LANGUAGE_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		LanguageSearch languageSearch = (LanguageSearch) model.asMap().get(WebLanguageConstants.ModelAttribute.SEARCH);

		Language language = new Language();
		language.setId(languageSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = Language.SORT;
			direction = Sort.Direction.ASC;
		}

		Page<Language> pagination = languageFacade.page(language, page, size, direction, properties);

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, LanguageSearch languageSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_LANGUAGE_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(LanguageSearch.ID_SEARCH, languageSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebLanguageConstants.ModelAttribute.SEARCH, languageSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebLanguageConstants.ModelAttribute.CREATE)
	public Language populateLanguageCreate(HttpServletRequest request) {
		return languageFacade.create();
	}

	@ModelAttribute(WebLanguageConstants.ModelAttribute.UPDATE)
	public Language populateLanguageForm(HttpServletRequest request) {
		return languageFacade.create();
	}

	@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH)
	public LanguageSearch populateLanguageSearch(HttpServletRequest request) {
		return new LanguageSearch();
	}

	@PreAuthorize(WebLanguageConstants.PreAuthorize.READ)
	@GetMapping(value = WebLanguageConstants.Path.LANGUAGE)
	public String list(@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH) LanguageSearch languageSearch,
			@ModelAttribute(WebLanguageConstants.ModelAttribute.UPDATE) Language languageUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (languageUpdate.getUuid() != null) {
			Language existingLanguage = languageFacade.findByUuid(languageUpdate.getUuid());
			if (existingLanguage != null) {
				model.addAttribute(WebLanguageConstants.ModelAttribute.UPDATE, existingLanguage);
			} else {
				languageUpdate.setUuid(null);
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_LANGUAGE_LIST;
	}

	@PreAuthorize(WebLanguageConstants.PreAuthorize.CREATE)
	@PostMapping(value = WebLanguageConstants.Path.LANGUAGE, params = "create")
	public RedirectView create(
			@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH) LanguageSearch languageSearch,
			@ModelAttribute(WebLanguageConstants.ModelAttribute.CREATE) Language languageCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (languageCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			languageCreate = languageFacade.save(languageCreate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Language.UUID, languageCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PreAuthorize(WebLanguageConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebLanguageConstants.Path.LANGUAGE, params = "update")
	public RedirectView update(
			@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH) LanguageSearch languageSearch,
			@ModelAttribute(WebLanguageConstants.ModelAttribute.UPDATE) Language languageUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (languageUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			languageUpdate = languageFacade.save(languageUpdate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Language.UUID, languageUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PreAuthorize(WebLanguageConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebLanguageConstants.Path.LANGUAGE, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH) LanguageSearch languageSearch,
			@ModelAttribute(WebLanguageConstants.ModelAttribute.UPDATE) Language languageUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		backofficeModuleFacade.deleteAllModuleLanguageByLanguageUuid(languageUpdate.getUuid(), bindingResult);

		if (bindingResult.hasErrors() == false) {
			languageFacade.delete(languageUpdate.getUuid(), bindingResult);
		}

		if (bindingResult.hasErrors()) {

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());
			redirectAttributes.addAttribute(Language.UUID, languageUpdate.getUuid());
		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;

	}
}
package com.beanframework.backoffice.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebLanguageConstants;
import com.beanframework.backoffice.data.LanguageSearch;
import com.beanframework.backoffice.data.LanguageSpecification;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageFacade;

@Controller
public class LanguageController extends AbstractController {

	@Autowired
	private LanguageFacade languageFacade;

	@Value(WebLanguageConstants.Path.LANGUAGE)
	private String PATH_LANGUAGE;

	@Value(WebLanguageConstants.View.LIST)
	private String VIEW_LANGUAGE_LIST;

	@Value(WebLanguageConstants.LIST_SIZE)
	private int MODULE_LANGUAGE_LIST_SIZE;

	private Page<Language> getPagination(LanguageSearch languageSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_LANGUAGE_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = Language.SORT;
			direction = Sort.Direction.ASC;
		}

		Page<Language> pagination = languageFacade.findPage(LanguageSpecification.findByCriteria(languageSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, LanguageSearch languageSearch) {
		
		languageSearch.setSearchAll((String)requestParams.get("languageSearch.searchAll"));
		languageSearch.setId((String)requestParams.get("languageSearch.id"));
		languageSearch.setName((String)requestParams.get("languageSearch.name"));
		
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
		redirectAttributes.addAttribute("searchAll", languageSearch.getSearchAll());
		redirectAttributes.addAttribute("id", languageSearch.getId());
		redirectAttributes.addAttribute("name", languageSearch.getName());

		return redirectAttributes;
	}

	@ModelAttribute(WebLanguageConstants.ModelAttribute.CREATE)
	public Language populateLanguageCreate(HttpServletRequest request) throws Exception {
		return languageFacade.create();
	}

	@ModelAttribute(WebLanguageConstants.ModelAttribute.UPDATE)
	public Language populateLanguageForm(HttpServletRequest request) throws Exception {
		return languageFacade.create();
	}

	@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH)
	public LanguageSearch populateLanguageSearch(HttpServletRequest request) {
		return new LanguageSearch();
	}

	@GetMapping(value = WebLanguageConstants.Path.LANGUAGE)
	public String list(@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH) LanguageSearch languageSearch,
			@ModelAttribute(WebLanguageConstants.ModelAttribute.UPDATE) Language languageUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(languageSearch, model, requestParams));

		if (languageUpdate.getUuid() != null) {

			Language existingLanguage = languageFacade.findOneDtoByUuid(languageUpdate.getUuid());

			if (existingLanguage != null) {
				model.addAttribute(WebLanguageConstants.ModelAttribute.UPDATE, existingLanguage);
			} else {
				languageUpdate.setUuid(null);
				addErrorMessage(model, WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}
		
		return VIEW_LANGUAGE_LIST;
	}

	@PostMapping(value = WebLanguageConstants.Path.LANGUAGE, params = "create")
	public RedirectView create(
			@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH) LanguageSearch languageSearch,
			@ModelAttribute(WebLanguageConstants.ModelAttribute.CREATE) Language languageCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (languageCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			try {
				languageCreate = languageFacade.createDto(languageCreate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Language.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Language.UUID, languageCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PostMapping(value = WebLanguageConstants.Path.LANGUAGE, params = "update")
	public RedirectView update(
			@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH) LanguageSearch languageSearch,
			@ModelAttribute(WebLanguageConstants.ModelAttribute.UPDATE) Language languageUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (languageUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			try {
				languageUpdate = languageFacade.updateDto(languageUpdate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Language.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Language.UUID, languageUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PostMapping(value = WebLanguageConstants.Path.LANGUAGE, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebLanguageConstants.ModelAttribute.SEARCH) LanguageSearch languageSearch,
			@ModelAttribute(WebLanguageConstants.ModelAttribute.UPDATE) Language languageUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			languageFacade.delete(languageUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Language.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(Language.UUID, languageUpdate.getUuid());
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;

	}
}

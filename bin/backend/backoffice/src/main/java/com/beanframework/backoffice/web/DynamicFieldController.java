package com.beanframework.backoffice.web;

import java.util.List;
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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.DynamicFieldWebConstants;
import com.beanframework.backoffice.data.DynamicFieldSearch;
import com.beanframework.backoffice.data.DynamicFieldSpecification;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.service.DynamicFieldFacade;
import com.beanframework.dynamicfield.service.DynamicFieldService;

@Controller
public class DynamicFieldController extends AbstractController {

	@Autowired
	private DynamicFieldFacade dynamicFieldFacade;
	
	@Autowired
	private DynamicFieldService dynamicFieldService;

	@Value(DynamicFieldWebConstants.Path.LANGUAGE)
	private String PATH_LANGUAGE;

	@Value(DynamicFieldWebConstants.View.LIST)
	private String VIEW_LANGUAGE_LIST;

	@Value(DynamicFieldWebConstants.LIST_SIZE)
	private int MODULE_LANGUAGE_LIST_SIZE;

	private Page<DynamicField> getPagination(DynamicFieldSearch languageSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_LANGUAGE_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = DynamicField.SORT;
			direction = Sort.Direction.ASC;
		}

		Page<DynamicField> pagination = dynamicFieldFacade.findDtoPage(DynamicFieldSpecification.findByCriteria(languageSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, DynamicFieldSearch languageSearch) {
		
		languageSearch.setSearchAll((String)requestParams.get("languageSearch.searchAll"));
		languageSearch.setId((String)requestParams.get("languageSearch.id"));
		languageSearch.setName((String)requestParams.get("languageSearch.name"));
		
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_LANGUAGE_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", languageSearch.getSearchAll());
		redirectAttributes.addAttribute("id", languageSearch.getId());
		redirectAttributes.addAttribute("name", languageSearch.getName());

		return redirectAttributes;
	}

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.CREATE)
	public DynamicField populateDynamicFieldCreate(HttpServletRequest request) throws Exception {
		return dynamicFieldService.create();
	}

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE)
	public DynamicField populateDynamicFieldForm(HttpServletRequest request) throws Exception {
		return dynamicFieldService.create();
	}

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH)
	public DynamicFieldSearch populateDynamicFieldSearch(HttpServletRequest request) {
		return new DynamicFieldSearch();
	}

	@GetMapping(value = DynamicFieldWebConstants.Path.LANGUAGE)
	public String list(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH) DynamicFieldSearch languageSearch,
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicField languageUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(languageSearch, model, requestParams));

		if (languageUpdate.getUuid() != null) {

			DynamicField existingDynamicField = dynamicFieldFacade.findOneDtoByUuid(languageUpdate.getUuid());
			
			List<Object[]> revisions = dynamicFieldFacade.findHistoryByUuid(languageUpdate.getUuid(), null, null);
			model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);

			if (existingDynamicField != null) {
				model.addAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE, existingDynamicField);
			} else {
				languageUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}
		
		return VIEW_LANGUAGE_LIST;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.LANGUAGE, params = "create")
	public RedirectView create(
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH) DynamicFieldSearch languageSearch,
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.CREATE) DynamicField languageCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (languageCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			try {
				languageCreate = dynamicFieldFacade.createDto(languageCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicField.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicField.UUID, languageCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.LANGUAGE, params = "update")
	public RedirectView update(
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH) DynamicFieldSearch languageSearch,
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicField languageUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (languageUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			try {
				languageUpdate = dynamicFieldFacade.updateDto(languageUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicField.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicField.UUID, languageUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.LANGUAGE, params = "delete")
	public RedirectView delete(
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH) DynamicFieldSearch languageSearch,
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicField languageUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldFacade.delete(languageUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicField.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicField.UUID, languageUpdate.getUuid());
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, languageSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_LANGUAGE);
		return redirectView;

	}
}

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
import com.beanframework.backoffice.WebCatalogConstants;
import com.beanframework.backoffice.domain.CatalogSearch;
import com.beanframework.catalog.domain.Catalog;
import com.beanframework.catalog.service.CatalogFacade;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;

@Controller
public class CatalogController {

	@Autowired
	private CatalogFacade catalogFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebCatalogConstants.Path.CATALOG)
	private String PATH_CATALOG;

	@Value(WebCatalogConstants.View.LIST)
	private String VIEW_CATALOG_LIST;

	@Value(WebCatalogConstants.LIST_SIZE)
	private int MODULE_CATALOG_LIST_SIZE;

	private Page<Catalog> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CATALOG_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		CatalogSearch catalogSearch = (CatalogSearch) model.asMap().get(WebCatalogConstants.ModelAttribute.SEARCH);

		Catalog catalog = new Catalog();
		catalog.setId(catalogSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = Catalog.SORT;
			direction = Sort.Direction.ASC;
		}

		Page<Catalog> pagination = catalogFacade.page(catalog, page, size, direction, properties);

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, CatalogSearch catalogSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CATALOG_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(CatalogSearch.ID_SEARCH, catalogSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebCatalogConstants.ModelAttribute.SEARCH, catalogSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebCatalogConstants.ModelAttribute.CREATE)
	public Catalog populateCatalogCreate(HttpServletRequest request) {
		return catalogFacade.create();
	}

	@ModelAttribute(WebCatalogConstants.ModelAttribute.UPDATE)
	public Catalog populateCatalogForm(HttpServletRequest request) {
		return catalogFacade.create();
	}

	@ModelAttribute(WebCatalogConstants.ModelAttribute.SEARCH)
	public CatalogSearch populateCatalogSearch(HttpServletRequest request) {
		return new CatalogSearch();
	}

	@PreAuthorize(WebCatalogConstants.PreAuthorize.READ)
	@GetMapping(value = WebCatalogConstants.Path.CATALOG)
	public String list(@ModelAttribute(WebCatalogConstants.ModelAttribute.SEARCH) CatalogSearch catalogSearch,
			@ModelAttribute(WebCatalogConstants.ModelAttribute.UPDATE) Catalog catalogUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (catalogUpdate.getUuid() != null) {
			Catalog existingCatalog = catalogFacade.findByUuid(catalogUpdate.getUuid());
			if (existingCatalog != null) {
				model.addAttribute(WebCatalogConstants.ModelAttribute.UPDATE, existingCatalog);
			} else {
				catalogUpdate.setUuid(null);
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_CATALOG_LIST;
	}

	@PreAuthorize(WebCatalogConstants.PreAuthorize.CREATE)
	@PostMapping(value = WebCatalogConstants.Path.CATALOG, params = "create")
	public RedirectView create(
			@ModelAttribute(WebCatalogConstants.ModelAttribute.SEARCH) CatalogSearch catalogSearch,
			@ModelAttribute(WebCatalogConstants.ModelAttribute.CREATE) Catalog catalogCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (catalogCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			catalogCreate = catalogFacade.save(catalogCreate, bindingResult);
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

		redirectAttributes.addAttribute(Catalog.UUID, catalogCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, catalogSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CATALOG);
		return redirectView;
	}

	@PreAuthorize(WebCatalogConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebCatalogConstants.Path.CATALOG, params = "update")
	public RedirectView update(
			@ModelAttribute(WebCatalogConstants.ModelAttribute.SEARCH) CatalogSearch catalogSearch,
			@ModelAttribute(WebCatalogConstants.ModelAttribute.UPDATE) Catalog catalogUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (catalogUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			catalogUpdate = catalogFacade.save(catalogUpdate, bindingResult);
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

		redirectAttributes.addAttribute(Catalog.UUID, catalogUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, catalogSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CATALOG);
		return redirectView;
	}

	@PreAuthorize(WebCatalogConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebCatalogConstants.Path.CATALOG, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebCatalogConstants.ModelAttribute.SEARCH) CatalogSearch catalogSearch,
			@ModelAttribute(WebCatalogConstants.ModelAttribute.UPDATE) Catalog catalogUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors() == false) {
			catalogFacade.delete(catalogUpdate.getUuid(), bindingResult);
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
			redirectAttributes.addAttribute(Catalog.UUID, catalogUpdate.getUuid());
		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, catalogSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CATALOG);
		return redirectView;

	}
}

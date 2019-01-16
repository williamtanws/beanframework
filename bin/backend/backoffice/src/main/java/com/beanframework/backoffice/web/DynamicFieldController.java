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
import com.beanframework.backoffice.data.DynamicFieldDto;
import com.beanframework.backoffice.data.DynamicFieldSearch;
import com.beanframework.backoffice.facade.DynamicFieldFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;

@Controller
public class DynamicFieldController extends AbstractController {

	@Autowired
	private DynamicFieldFacade dynamicFieldFacade;

	@Value(DynamicFieldWebConstants.Path.DYNAMICFIELD)
	private String PATH_DYNAMICFIELD;

	@Value(DynamicFieldWebConstants.View.LIST)
	private String VIEW_DYNAMICFIELD_LIST;

	@Value(DynamicFieldWebConstants.LIST_SIZE)
	private int MODULE_DYNAMICFIELD_LIST_SIZE;

	private Page<DynamicFieldDto> getPagination(DynamicFieldSearch dynamicFieldSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_DYNAMICFIELD_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null : propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = DynamicFieldDto.SORT;
			direction = Sort.Direction.ASC;
		}

		Page<DynamicFieldDto> pagination = dynamicFieldFacade.findPage(dynamicFieldSearch, PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes, @RequestParam Map<String, Object> requestParams, DynamicFieldSearch dynamicFieldSearch) {

		dynamicFieldSearch.setSearchAll((String) requestParams.get("dynamicFieldSearch.searchAll"));
		dynamicFieldSearch.setId((String) requestParams.get("dynamicFieldSearch.id"));
		dynamicFieldSearch.setName((String) requestParams.get("dynamicFieldSearch.name"));

		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_DYNAMICFIELD_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", dynamicFieldSearch.getSearchAll());
		redirectAttributes.addAttribute("id", dynamicFieldSearch.getId());
		redirectAttributes.addAttribute("name", dynamicFieldSearch.getName());

		return redirectAttributes;
	}

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.CREATE)
	public DynamicFieldDto populateDynamicFieldCreate(HttpServletRequest request) throws Exception {
		return new DynamicFieldDto();
	}

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE)
	public DynamicFieldDto populateDynamicFieldForm(HttpServletRequest request) throws Exception {
		return new DynamicFieldDto();
	}

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH)
	public DynamicFieldSearch populateDynamicFieldSearch(HttpServletRequest request) {
		return new DynamicFieldSearch();
	}

	@GetMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD)
	public String list(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH) DynamicFieldSearch dynamicFieldSearch,
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto dynamicFieldUpdate, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(dynamicFieldSearch, model, requestParams));

		if (dynamicFieldUpdate.getUuid() != null) {

			DynamicFieldDto existingDynamicField = dynamicFieldFacade.findOneByUuid(dynamicFieldUpdate.getUuid());

			List<Object[]> revisions = dynamicFieldFacade.findHistoryByUuid(dynamicFieldUpdate.getUuid(), null, null);
			model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);

			if (existingDynamicField != null) {
				model.addAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE, existingDynamicField);
			} else {
				dynamicFieldUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFIELD_LIST;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "create")
	public RedirectView create(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH) DynamicFieldSearch dynamicFieldSearch,
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.CREATE) DynamicFieldDto dynamicFieldCreate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicFieldCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				dynamicFieldCreate = dynamicFieldFacade.create(dynamicFieldCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldDto.UUID, dynamicFieldCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, dynamicFieldSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "update")
	public RedirectView update(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH) DynamicFieldSearch dynamicFieldSearch,
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto dynamicFieldUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (dynamicFieldUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				dynamicFieldUpdate = dynamicFieldFacade.update(dynamicFieldUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldDto.UUID, dynamicFieldUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, dynamicFieldSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "delete")
	public RedirectView delete(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.SEARCH) DynamicFieldSearch dynamicFieldSearch,
			@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto dynamicFieldUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldFacade.delete(dynamicFieldUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldDto.UUID, dynamicFieldUpdate.getUuid());
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, dynamicFieldSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;

	}
}

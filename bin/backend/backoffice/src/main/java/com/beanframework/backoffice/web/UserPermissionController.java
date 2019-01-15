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
import com.beanframework.backoffice.UserPermissionWebConstants;
import com.beanframework.backoffice.data.UserPermissionDto;
import com.beanframework.backoffice.data.UserPermissionSearch;
import com.beanframework.backoffice.data.UserPermissionSpecification;
import com.beanframework.backoffice.facade.UserPermissionFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;

@Controller
public class UserPermissionController extends AbstractController {

	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@Value(UserPermissionWebConstants.Path.USERPERMISSION)
	private String PATH_USERPERMISSION;

	@Value(UserPermissionWebConstants.View.LIST)
	private String VIEW_USERPERMISSION_LIST;

	@Value(UserPermissionWebConstants.LIST_SIZE)
	private int MODULE_USERPERMISSION_LIST_SIZE;

	private Page<UserPermissionDto> getPagination(UserPermissionSearch userPermissionSearch, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERPERMISSION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = UserPermissionDto.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<UserPermissionDto> pagination = userPermissionFacade.findPage(
				UserPermissionSpecification.findByCriteria(userPermissionSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, UserPermissionSearch userpermissionSearch) {
		
		userpermissionSearch.setSearchAll((String)requestParams.get("userpermissionSearch.searchAll"));
		userpermissionSearch.setId((String)requestParams.get("userpermissionSearch.id"));
		
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERPERMISSION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", userpermissionSearch.getSearchAll());
		redirectAttributes.addAttribute("id", userpermissionSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(UserPermissionWebConstants.ModelAttribute.CREATE)
	public UserPermissionDto populateUserPermissionCreate(HttpServletRequest request) throws Exception {
		return new UserPermissionDto();
	}

	@ModelAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE)
	public UserPermissionDto populateUserPermissionForm(HttpServletRequest request) throws Exception {
		return new UserPermissionDto();
	}

	@ModelAttribute(UserPermissionWebConstants.ModelAttribute.SEARCH)
	public UserPermissionSearch populateUserPermissionSearch(HttpServletRequest request) {
		return new UserPermissionSearch();
	}

	@GetMapping(value = UserPermissionWebConstants.Path.USERPERMISSION)
	public String list(
			@ModelAttribute(UserPermissionWebConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE) UserPermissionDto userpermissionUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(userpermissionSearch, model, requestParams));

		if (userpermissionUpdate.getUuid() != null) {

			UserPermissionDto existingUserPermission = userPermissionFacade.findOneByUuid(userpermissionUpdate.getUuid());
			
			List<Object[]> revisions = userPermissionFacade.findHistoryByUuid(userpermissionUpdate.getUuid(), null, null);
			model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);
			
			List<Object[]> fieldRevisions = userPermissionFacade.findFieldHistoryByUuid(userpermissionUpdate.getUuid(), null, null);
			model.addAttribute(BackofficeWebConstants.Model.FIELD_REVISIONS, fieldRevisions);

			if (existingUserPermission != null) {
				model.addAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE, existingUserPermission);
			} else {
				userpermissionUpdate.setUuid(null);
				model.addAttribute(BackofficeWebConstants.Model.ERROR,
						localeMessageService.getMessage(BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_USERPERMISSION_LIST;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "create")
	public RedirectView create(
			@ModelAttribute(UserPermissionWebConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(UserPermissionWebConstants.ModelAttribute.CREATE) UserPermissionDto userpermissionCreate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userpermissionCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			try {
				userpermissionCreate = userPermissionFacade.create(userpermissionCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermissionDto.UUID, userpermissionCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "update")
	public RedirectView update(
			@ModelAttribute(UserPermissionWebConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE) UserPermissionDto userpermissionUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userpermissionUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				userpermissionUpdate = userPermissionFacade.update(userpermissionUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermissionDto.UUID, userpermissionUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "delete")
	public RedirectView delete(
			@ModelAttribute(UserPermissionWebConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE) UserPermissionDto userpermissionUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {

			userPermissionFacade.delete(userpermissionUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE,
					userpermissionUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;

	}
}

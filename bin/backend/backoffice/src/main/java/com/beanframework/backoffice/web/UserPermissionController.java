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
import com.beanframework.backoffice.WebUserPermissionConstants;
import com.beanframework.backoffice.data.UserPermissionSearch;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionSpecification;
import com.beanframework.user.service.UserPermissionFacade;

@Controller
public class UserPermissionController extends AbstractCommonController {

	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebUserPermissionConstants.Path.USERPERMISSION)
	private String PATH_USERPERMISSION;

	@Value(WebUserPermissionConstants.View.LIST)
	private String VIEW_USERPERMISSION_LIST;

	@Value(WebUserPermissionConstants.LIST_SIZE)
	private int MODULE_USERPERMISSION_LIST_SIZE;

	private Page<UserPermission> getPagination(Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERPERMISSION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		UserPermissionSearch userpermissionSearch = (UserPermissionSearch) model.asMap()
				.get(WebUserPermissionConstants.ModelAttribute.SEARCH);

		UserPermission userPermission = new UserPermission();
		userPermission.setId(userpermissionSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = UserPermission.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<UserPermission> pagination = userPermissionFacade.findPage(
				UserPermissionSpecification.findByCriteria(userPermission),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, UserPermissionSearch userpermissionSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERPERMISSION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(UserPermissionSearch.ID_SEARCH, userpermissionSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH, userpermissionSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebUserPermissionConstants.ModelAttribute.CREATE)
	public UserPermission populateUserPermissionCreate(HttpServletRequest request) throws Exception {
		return userPermissionFacade.create();
	}

	@ModelAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE)
	public UserPermission populateUserPermissionForm(HttpServletRequest request) throws Exception {
		return userPermissionFacade.create();
	}

	@ModelAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH)
	public UserPermissionSearch populateUserPermissionSearch(HttpServletRequest request) {
		return new UserPermissionSearch();
	}

	@GetMapping(value = WebUserPermissionConstants.Path.USERPERMISSION)
	public String list(
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE) UserPermission userpermissionUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (userpermissionUpdate.getUuid() != null) {

			UserPermission existingUserPermission = userPermissionFacade.findOneDtoByUuid(userpermissionUpdate.getUuid());

			if (existingUserPermission != null) {
				model.addAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE, existingUserPermission);
			} else {
				userpermissionUpdate.setUuid(null);
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_USERPERMISSION_LIST;
	}

	@PostMapping(value = WebUserPermissionConstants.Path.USERPERMISSION, params = "create")
	public RedirectView create(
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.CREATE) UserPermission userpermissionCreate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userpermissionCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			try {
				userpermissionCreate = userPermissionFacade.createDto(userpermissionCreate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermission.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermission.UUID, userpermissionCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PostMapping(value = WebUserPermissionConstants.Path.USERPERMISSION, params = "update")
	public RedirectView update(
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE) UserPermission userpermissionUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (userpermissionUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				userpermissionUpdate = userPermissionFacade.updateDto(userpermissionUpdate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermission.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermission.UUID, userpermissionUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PostMapping(value = WebUserPermissionConstants.Path.USERPERMISSION, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE) UserPermission userpermissionUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {

			userPermissionFacade.delete(userpermissionUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserPermission.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE,
					userpermissionUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;

	}
}

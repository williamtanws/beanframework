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
import com.beanframework.backoffice.WebUserPermissionConstants;
import com.beanframework.backoffice.domain.UserPermissionSearch;
import com.beanframework.backoffice.service.BackofficeModuleFacade;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionFacade;

@Controller
public class UserPermissionController {

	@Autowired
	private UserPermissionFacade userpermissionFacade;

	@Autowired
	private LocaleMessageService localeMessageService;
	
	@Autowired
	private BackofficeModuleFacade backofficeModuleFacade;

	@Value(WebUserPermissionConstants.Path.USERPERMISSION)
	private String PATH_USERPERMISSION;

	@Value(WebUserPermissionConstants.View.LIST)
	private String VIEW_USERPERMISSION_LIST;

	@Value(WebUserPermissionConstants.LIST_SIZE)
	private int MODULE_USERPERMISSION_LIST_SIZE;

	private Page<UserPermission> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERPERMISSION_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		UserPermissionSearch userpermissionSearch = (UserPermissionSearch) model.asMap()
				.get(WebUserPermissionConstants.ModelAttribute.SEARCH);

		UserPermission userpermission = new UserPermission();
		userpermission.setId(userpermissionSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = UserPermission.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<UserPermission> pagination = userpermissionFacade.page(userpermission, page, size, direction, properties);

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
	public UserPermission populateUserPermissionCreate(HttpServletRequest request) {
		return userpermissionFacade.create();
	}

	@ModelAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE)
	public UserPermission populateUserPermissionForm(HttpServletRequest request) {
		return userpermissionFacade.create();
	}

	@ModelAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH)
	public UserPermissionSearch populateUserPermissionSearch(HttpServletRequest request) {
		return new UserPermissionSearch();
	}

	@PreAuthorize(WebUserPermissionConstants.PreAuthorize.READ)
	@GetMapping(value = WebUserPermissionConstants.Path.USERPERMISSION)
	public String list(
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE) UserPermission userpermissionUpdate,
			Model model, @RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (userpermissionUpdate.getUuid() != null) {
			UserPermission existingUserPermission = userpermissionFacade.findByUuid(userpermissionUpdate.getUuid());
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

	@PreAuthorize(WebUserPermissionConstants.PreAuthorize.CREATE)
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
			userpermissionCreate = userpermissionFacade.save(userpermissionCreate, bindingResult);
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

		redirectAttributes.addAttribute(UserPermission.UUID, userpermissionCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PreAuthorize(WebUserPermissionConstants.PreAuthorize.UPDATE)
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
			userpermissionUpdate = userpermissionFacade.save(userpermissionUpdate, bindingResult);
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

		redirectAttributes.addAttribute(UserPermission.UUID, userpermissionUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PreAuthorize(WebUserPermissionConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebUserPermissionConstants.Path.USERPERMISSION, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.SEARCH) UserPermissionSearch userpermissionSearch,
			@ModelAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE) UserPermission userpermissionUpdate,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		backofficeModuleFacade.deleteAllModuleUserPermissionByUserPermissionUuid(userpermissionUpdate.getUuid(), bindingResult);

		if (bindingResult.hasErrors() == false) {
			userpermissionFacade.delete(userpermissionUpdate.getUuid(), bindingResult);
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
			redirectAttributes.addFlashAttribute(WebUserPermissionConstants.ModelAttribute.UPDATE, userpermissionUpdate);
		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, userpermissionSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;

	}
}

package com.beanframework.backoffice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebUserGroupConstants;
import com.beanframework.backoffice.domain.UserGroupSearch;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupSpecification;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserGroupFacade;
import com.beanframework.user.service.UserPermissionFacade;
import com.beanframework.user.service.UserRightFacade;

@Controller
public class UserGroupController extends AbstractCommonController {

	@Autowired
	private UserGroupFacade userGroupFacade;
	
	@Autowired
	private UserRightFacade userRightFacade;
	
	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@Value(WebUserGroupConstants.Path.USERGROUP)
	private String PATH_USERGROUP;

	@Value(WebUserGroupConstants.View.LIST)
	private String VIEW_USERGROUP_LIST;

	@Value(WebUserGroupConstants.LIST_SIZE)
	private int MODULE_USERGROUP_LIST_SIZE;

	private Page<UserGroup> getPagination(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERGROUP_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		UserGroupSearch usergroupSearch = (UserGroupSearch) model.asMap()
				.get(WebUserGroupConstants.ModelAttribute.SEARCH);

		UserGroup usergroup = new UserGroup();
		usergroup.setId(usergroupSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = UserGroup.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<UserGroup> pagination = userGroupFacade.findPage(UserGroupSpecification.findByCriteria(usergroup),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, UserGroupSearch usergroupSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERGROUP_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(UserGroupSearch.ID_SEARCH, usergroupSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebUserGroupConstants.ModelAttribute.SEARCH, usergroupSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebUserGroupConstants.ModelAttribute.CREATE)
	public UserGroup populateUserGroupCreate(HttpServletRequest request) throws Exception {
		return userGroupFacade.create();
	}

	@ModelAttribute(WebUserGroupConstants.ModelAttribute.UPDATE)
	public UserGroup populateUserGroupForm(HttpServletRequest request) throws Exception {
		return userGroupFacade.create();
	}

	@ModelAttribute(WebUserGroupConstants.ModelAttribute.SEARCH)
	public UserGroupSearch populateUserGroupSearch(HttpServletRequest request) {
		return new UserGroupSearch();
	}

	@GetMapping(value = WebUserGroupConstants.Path.USERGROUP)
	public String list(@ModelAttribute(WebUserGroupConstants.ModelAttribute.SEARCH) UserGroupSearch usergroupSearch,
			@ModelAttribute(WebUserGroupConstants.ModelAttribute.UPDATE) UserGroup usergroupUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (usergroupUpdate.getUuid() != null) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(UserGroup.UUID, usergroupUpdate.getUuid());

			UserGroup existingUserGroup = userGroupFacade.findOneDtoByProperties(properties);

			if (existingUserGroup != null) {
				
				// UserGroups
				
				Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
				sorts.put(UserGroup.CREATED_DATE, Sort.Direction.DESC);

				List<UserGroup> userGroups = userGroupFacade.findDtoBySorts(sorts);
				
				for (Iterator<UserGroup> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext(); ) {
					if(userGroupsIterator.next().getUuid().equals(existingUserGroup.getUuid())) {
						userGroupsIterator.remove();
					}
				}

				for (int i = 0; i < userGroups.size(); i++) {
					for (UserGroup userGroup : existingUserGroup.getUserGroups()) {
						if (userGroups.get(i).getUuid().equals(userGroup.getUuid())) {
							userGroups.get(i).setSelected("true");
						}
					}
				}
				existingUserGroup.setUserGroups(userGroups);
				
				// User Authority
				
				Map<String, Sort.Direction> userRightSorts = new HashMap<String, Sort.Direction>();
				userRightSorts.put(UserRight.SORT, Sort.Direction.ASC);
				List<UserRight> userRights = userRightFacade.findDtoBySorts(userRightSorts);
				
				Map<String, Sort.Direction> userPermissionSorts = new HashMap<String, Sort.Direction>();
				userPermissionSorts.put(UserPermission.SORT, Sort.Direction.ASC);
				List<UserPermission> userPermissions = userPermissionFacade.findDtoBySorts(userPermissionSorts);

				model.addAttribute("userRights", userRights);
				model.addAttribute("userPermissions", userPermissions);
				
				model.addAttribute(WebUserGroupConstants.ModelAttribute.UPDATE, existingUserGroup);
			} else {
				usergroupUpdate.setUuid(null);
				addErrorMessage(model, WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}
		return VIEW_USERGROUP_LIST;
	}

	@PostMapping(value = WebUserGroupConstants.Path.USERGROUP, params = "create")
	public RedirectView create(
			@ModelAttribute(WebUserGroupConstants.ModelAttribute.SEARCH) UserGroupSearch usergroupSearch,
			@ModelAttribute(WebUserGroupConstants.ModelAttribute.CREATE) UserGroup usergroupCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (usergroupCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			try {
				userGroupFacade.createDto(usergroupCreate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserGroup.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserGroup.UUID, usergroupCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, usergroupSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;
	}

	@PostMapping(value = WebUserGroupConstants.Path.USERGROUP, params = "update")
	public RedirectView update(
			@ModelAttribute(WebUserGroupConstants.ModelAttribute.SEARCH) UserGroupSearch usergroupSearch,
			@ModelAttribute(WebUserGroupConstants.ModelAttribute.UPDATE) UserGroup usergroupUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (usergroupUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			
			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UserGroup userGroup : usergroupUpdate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			usergroupUpdate.setUserGroups(userGroups);

			try {
				userGroupFacade.updateDto(usergroupUpdate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserGroup.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserGroup.UUID, usergroupUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, usergroupSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;
	}

	@PostMapping(value = WebUserGroupConstants.Path.USERGROUP, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebUserGroupConstants.ModelAttribute.SEARCH) UserGroupSearch usergroupSearch,
			@ModelAttribute(WebUserGroupConstants.ModelAttribute.UPDATE) UserGroup usergroupUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			userGroupFacade.delete(usergroupUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserGroup.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebUserGroupConstants.ModelAttribute.UPDATE, usergroupUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, usergroupSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;

	}
}

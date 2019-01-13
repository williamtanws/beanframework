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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.UserGroupWebConstants;
import com.beanframework.backoffice.data.UserGroupSearch;
import com.beanframework.backoffice.data.UserGroupSpecification;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserGroupFacade;
import com.beanframework.user.service.UserGroupService;
import com.beanframework.user.service.UserPermissionService;
import com.beanframework.user.service.UserRightService;

@Controller
public class UserGroupController extends AbstractController {

	@Autowired
	private UserGroupFacade userGroupFacade;

	@Autowired
	private UserGroupService userGroupService;
	
	@Autowired
	private UserRightService userRightService;
	
	@Autowired
	private UserPermissionService userPermissionService;

	@Value(UserGroupWebConstants.Path.USERGROUP)
	private String PATH_USERGROUP;

	@Value(UserGroupWebConstants.View.LIST)
	private String VIEW_USERGROUP_LIST;

	@Value(UserGroupWebConstants.LIST_SIZE)
	private int MODULE_USERGROUP_LIST_SIZE;

	private Page<UserGroup> getPagination(UserGroupSearch userGroupSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERGROUP_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);
		
		if (properties == null) {
			properties = new String[1];
			properties[0] = UserGroup.CREATED_DATE;
			direction = Sort.Direction.ASC;
		}

		Page<UserGroup> pagination = userGroupFacade.findPage(UserGroupSpecification.findByCriteria(userGroupSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, UserGroupSearch usergroupSearch) {
		
		usergroupSearch.setSearchAll((String)requestParams.get("usergroupSearch.searchAll"));
		usergroupSearch.setId((String)requestParams.get("usergroupSearch.id"));
		
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_USERGROUP_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", usergroupSearch.getSearchAll());
		redirectAttributes.addAttribute("id", usergroupSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(UserGroupWebConstants.ModelAttribute.CREATE)
	public UserGroup populateUserGroupCreate(HttpServletRequest request) throws Exception {
		return userGroupService.create();
	}

	@ModelAttribute(UserGroupWebConstants.ModelAttribute.UPDATE)
	public UserGroup populateUserGroupForm(HttpServletRequest request) throws Exception {
		return userGroupService.create();
	}

	@ModelAttribute(UserGroupWebConstants.ModelAttribute.SEARCH)
	public UserGroupSearch populateUserGroupSearch(HttpServletRequest request) {
		return new UserGroupSearch();
	}

	@GetMapping(value = UserGroupWebConstants.Path.USERGROUP)
	public String list(@ModelAttribute(UserGroupWebConstants.ModelAttribute.SEARCH) UserGroupSearch usergroupSearch,
			@ModelAttribute(UserGroupWebConstants.ModelAttribute.UPDATE) UserGroup usergroupUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(usergroupSearch, model, requestParams));

		if (usergroupUpdate.getUuid() != null) {

			UserGroup existingUserGroup = userGroupFacade.findOneDtoByUuid(usergroupUpdate.getUuid());

			if (existingUserGroup != null) {
				
				// UserGroups
				
				Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
				sorts.put(UserGroup.CREATED_DATE, Sort.Direction.DESC);

				List<UserGroup> userGroups = userGroupService.findDtoBySorts(sorts);
				
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
				List<UserRight> userRights = userRightService.findDtoBySorts(userRightSorts);
				
				Map<String, Sort.Direction> userPermissionSorts = new HashMap<String, Sort.Direction>();
				userPermissionSorts.put(UserPermission.SORT, Sort.Direction.ASC);
				List<UserPermission> userPermissions = userPermissionService.findDtoBySorts(userPermissionSorts);

				model.addAttribute("userRights", userRights);
				model.addAttribute("userPermissions", userPermissions);
				
				List<Object[]> revisions = userGroupFacade.findHistoryByUuid(usergroupUpdate.getUuid(), null, null);
				model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);
				
				List<Object[]> fieldRevisions = userGroupFacade.findFieldHistoryByUuid(usergroupUpdate.getUuid(), null, null);
				model.addAttribute(BackofficeWebConstants.Model.FIELD_REVISIONS, fieldRevisions);
				
				model.addAttribute(UserGroupWebConstants.ModelAttribute.UPDATE, existingUserGroup);
			} else {
				usergroupUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}
		return VIEW_USERGROUP_LIST;
	}

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "create")
	public RedirectView create(
			@ModelAttribute(UserGroupWebConstants.ModelAttribute.SEARCH) UserGroupSearch usergroupSearch,
			@ModelAttribute(UserGroupWebConstants.ModelAttribute.CREATE) UserGroup usergroupCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (usergroupCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			try {
				usergroupCreate = userGroupFacade.createDto(usergroupCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "update")
	public RedirectView update(
			@ModelAttribute(UserGroupWebConstants.ModelAttribute.SEARCH) UserGroupSearch usergroupSearch,
			@ModelAttribute(UserGroupWebConstants.ModelAttribute.UPDATE) UserGroup usergroupUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (usergroupUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
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
				usergroupUpdate = userGroupFacade.updateDto(usergroupUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "delete")
	public RedirectView delete(
			@ModelAttribute(UserGroupWebConstants.ModelAttribute.SEARCH) UserGroupSearch usergroupSearch,
			@ModelAttribute(UserGroupWebConstants.ModelAttribute.UPDATE) UserGroup usergroupUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			userGroupFacade.delete(usergroupUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserGroup.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserGroupWebConstants.ModelAttribute.UPDATE, usergroupUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, usergroupSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;

	}
}

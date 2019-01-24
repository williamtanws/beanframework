package com.beanframework.backoffice.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.backoffice.data.UserPermissionDto;
import com.beanframework.backoffice.data.UserRightDto;
import com.beanframework.backoffice.facade.UserGroupFacade;
import com.beanframework.backoffice.facade.UserPermissionFacade;
import com.beanframework.backoffice.facade.UserRightFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;

@Controller
public class UserGroupController extends AbstractController {

	@Autowired
	private UserGroupFacade userGroupFacade;

	@Autowired
	private UserRightFacade userRightFacade;

	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@Value(UserGroupWebConstants.Path.USERGROUP)
	private String PATH_USERGROUP;

	@Value(UserGroupWebConstants.View.LIST)
	private String VIEW_USERGROUP_LIST;

	@ModelAttribute(UserGroupWebConstants.ModelAttribute.CREATE)
	public UserGroupDto create() throws Exception {
		return new UserGroupDto();
	}

	@ModelAttribute(UserGroupWebConstants.ModelAttribute.UPDATE)
	public UserGroupDto update() throws Exception {
		return new UserGroupDto();
	}

	@GetMapping(value = UserGroupWebConstants.Path.USERGROUP)
	public String list(@ModelAttribute(UserGroupWebConstants.ModelAttribute.UPDATE) UserGroupDto usergroupUpdate, Model model) throws Exception {

		if (usergroupUpdate.getUuid() != null) {

			UserGroupDto existingUserGroup = userGroupFacade.findOneByUuid(usergroupUpdate.getUuid());

			if (existingUserGroup != null) {

				// User Authority
				List<UserRightDto> userRights = userRightFacade.findAllDtoUserRights();
				model.addAttribute("userRights", userRights);

				List<UserPermissionDto> userPermissions = userPermissionFacade.findAllDtoUserPermissions();
				model.addAttribute("userPermissions", userPermissions);

				model.addAttribute(UserGroupWebConstants.ModelAttribute.UPDATE, existingUserGroup);
			} else {
				usergroupUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}
		return VIEW_USERGROUP_LIST;
	}

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "create")
	public RedirectView create(@ModelAttribute(UserGroupWebConstants.ModelAttribute.CREATE) UserGroupDto createDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				createDto = userGroupFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserGroupDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserGroupDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;
	}

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "update")
	public RedirectView update(@ModelAttribute(UserGroupWebConstants.ModelAttribute.UPDATE) UserGroupDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			// UserGroup
			if (updateDto.getTableUserGroups() != null) {
				List<UserGroupDto> userGroups = userGroupFacade.findOneByUuid(updateDto.getUuid()).getUserGroups();

				for (int i = 0; i < updateDto.getTableUserGroups().length; i++) {

					boolean remove = true;
					if (updateDto.getTableSelectedUserGroups() != null && updateDto.getTableSelectedUserGroups().length > i && BooleanUtils.parseBoolean(updateDto.getTableSelectedUserGroups()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<UserGroupDto> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(updateDto.getTableUserGroups()[i]))) {
								userGroupsIterator.remove();
							}
						}
					} else {
						boolean add = true;
						for (Iterator<UserGroupDto> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(updateDto.getTableUserGroups()[i]))) {
								add = false;
							}
						}

						if (add) {
							UserGroupDto userGroup = new UserGroupDto();
							userGroup.setUuid(UUID.fromString(updateDto.getTableUserGroups()[i]));
							userGroups.add(userGroup);
						}
					}
				}
				updateDto.setUserGroups(userGroups);
			}

			try {
				updateDto = userGroupFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserGroupDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserGroupDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;
	}

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "delete")
	public RedirectView delete(@ModelAttribute(UserGroupWebConstants.ModelAttribute.UPDATE) UserGroupDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			userGroupFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserGroupDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserGroupWebConstants.ModelAttribute.UPDATE, updateDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;

	}
}

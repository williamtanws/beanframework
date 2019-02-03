package com.beanframework.backoffice.web;

import java.util.List;
import java.util.Map;

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
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.facade.UserGroupFacade;
import com.beanframework.core.facade.UserPermissionFacade;
import com.beanframework.core.facade.UserRightFacade;

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

	@GetMapping(value = UserGroupWebConstants.Path.USERGROUP)
	public String list(@ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto, Model model) throws Exception {
		model.addAttribute("create", false);
		
		// User Authority
		List<UserRightDto> userRights = userRightFacade.findAllDtoUserRights();
		model.addAttribute("userRights", userRights);

		List<UserPermissionDto> userPermissions = userPermissionFacade.findAllDtoUserPermissions();
		model.addAttribute("userPermissions", userPermissions);
		
		if (usergroupDto.getUuid() != null) {

			UserGroupDto existingUserGroup = userGroupFacade.findOneByUuid(usergroupDto.getUuid());

			if (existingUserGroup != null) {
				model.addAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO, existingUserGroup);
			} else {
				usergroupDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}
		return VIEW_USERGROUP_LIST;
	}
	
	@GetMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "create")
	public String createView(Model model, @ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto) throws Exception {
		
		// User Authority
		List<UserRightDto> userRights = userRightFacade.findAllDtoUserRights();
		model.addAttribute("userRights", userRights);

		List<UserPermissionDto> userPermissions = userPermissionFacade.findAllDtoUserPermissions();
		model.addAttribute("userPermissions", userPermissions);
		
		usergroupDto = userGroupFacade.createDto();
		model.addAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO, usergroupDto);
		model.addAttribute("create", true);
		
		return VIEW_USERGROUP_LIST;
	}

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "create")
	public RedirectView create(@ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (usergroupDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				usergroupDto = userGroupFacade.create(usergroupDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserGroupDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserGroupDto.UUID, usergroupDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;
	}

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "update")
	public RedirectView update(@ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (usergroupDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				usergroupDto = userGroupFacade.update(usergroupDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserGroupDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserGroupDto.UUID, usergroupDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;
	}

	@PostMapping(value = UserGroupWebConstants.Path.USERGROUP, params = "delete")
	public RedirectView delete(@ModelAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO) UserGroupDto usergroupDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			userGroupFacade.delete(usergroupDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserGroupDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserGroupWebConstants.ModelAttribute.USERGROUP_DTO, usergroupDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERGROUP);
		return redirectView;

	}
}

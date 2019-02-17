package com.beanframework.backoffice.web;

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
import com.beanframework.backoffice.UserPermissionWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.facade.UserPermissionFacade;

@Controller
public class UserPermissionController extends AbstractController {

	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@Value(UserPermissionWebConstants.Path.USERPERMISSION)
	private String PATH_USERPERMISSION;

	@Value(UserPermissionWebConstants.View.LIST)
	private String VIEW_USERPERMISSION_LIST;

	@GetMapping(value = UserPermissionWebConstants.Path.USERPERMISSION)
	public String list(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userpermissionDto, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		model.addAttribute("create", false);

		if (userpermissionDto.getUuid() != null) {

			UserPermissionDto existsDto = userPermissionFacade.findOneByUuid(userpermissionDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO, existsDto);
			} else {
				userpermissionDto.setUuid(null);
				model.addAttribute(BackofficeWebConstants.Model.ERROR, localeMessageService.getMessage(BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_USERPERMISSION_LIST;
	}

	@GetMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "create")
	public String createView(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userpermissionDto, Model model) throws Exception {

		userpermissionDto = userPermissionFacade.createDto();
		model.addAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO, userpermissionDto);
		model.addAttribute("create", true);

		return VIEW_USERPERMISSION_LIST;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "create")
	public RedirectView create(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userpermissionDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userpermissionDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				userpermissionDto = userPermissionFacade.create(userpermissionDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermissionDto.UUID, userpermissionDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "update")
	public RedirectView update(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userpermissionDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userpermissionDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				userpermissionDto = userPermissionFacade.update(userpermissionDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermissionDto.UUID, userpermissionDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "delete")
	public RedirectView delete(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userpermissionDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {

			userPermissionFacade.delete(userpermissionDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO, userpermissionDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;

	}
}

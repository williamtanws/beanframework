package com.beanframework.backoffice.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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

@PreAuthorize("isAuthenticated()")
@Controller
public class UserPermissionController extends AbstractController {

	@Autowired
	private UserPermissionFacade userPermissionFacade;
	
	@Value(UserPermissionWebConstants.Path.USERPERMISSION_PAGE)
	private String PATH_USERPERMISSION_PAGE;
	
	@Value(UserPermissionWebConstants.Path.USERPERMISSION_FORM)
	private String PATH_USERPERMISSION_FORM;

	@Value(UserPermissionWebConstants.View.PAGE)
	private String VIEW_USERPERMISSION_PAGE;

	@Value(UserPermissionWebConstants.View.FORM)
	private String VIEW_USERPERMISSION_FORM;

	@GetMapping(value = UserPermissionWebConstants.Path.USERPERMISSION_PAGE)
	public String list(@Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userPermissionDto, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		return VIEW_USERPERMISSION_PAGE;
	}

	@GetMapping(value = UserPermissionWebConstants.Path.USERPERMISSION_FORM)
	public String createView(@Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userPermissionDto, Model model) throws Exception {

		if (userPermissionDto.getUuid() != null) {
			userPermissionDto = userPermissionFacade.findOneByUuid(userPermissionDto.getUuid());
		} else {
			userPermissionDto = userPermissionFacade.createDto();
		}
		model.addAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO, userPermissionDto);

		return VIEW_USERPERMISSION_FORM;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userPermissionDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userPermissionDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				userPermissionDto = userPermissionFacade.create(userPermissionDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermissionDto.UUID, userPermissionDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION_FORM);
		return redirectView;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userPermissionDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userPermissionDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				userPermissionDto = userPermissionFacade.update(userPermissionDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermissionDto.UUID, userPermissionDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION_FORM);
		return redirectView;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(UserPermissionWebConstants.ModelAttribute.USERPERMISSION_DTO) UserPermissionDto userPermissionDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userPermissionDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				userPermissionFacade.delete(userPermissionDto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION_FORM);
		return redirectView;

	}
}

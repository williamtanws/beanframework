package com.beanframework.backoffice.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.beanframework.backoffice.data.UserPermissionDto;
import com.beanframework.backoffice.facade.UserPermissionFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;

@Controller
public class UserPermissionController extends AbstractController {

	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@Value(UserPermissionWebConstants.Path.USERPERMISSION)
	private String PATH_USERPERMISSION;

	@Value(UserPermissionWebConstants.View.LIST)
	private String VIEW_USERPERMISSION_LIST;

	@ModelAttribute(UserPermissionWebConstants.ModelAttribute.CREATE)
	public UserPermissionDto populateUserPermissionCreate(HttpServletRequest request) throws Exception {
		return new UserPermissionDto();
	}

	@ModelAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE)
	public UserPermissionDto populateUserPermissionForm(HttpServletRequest request) throws Exception {
		return new UserPermissionDto();
	}

	@GetMapping(value = UserPermissionWebConstants.Path.USERPERMISSION)
	public String list(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE) UserPermissionDto userpermissionUpdate, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {

		if (userpermissionUpdate.getUuid() != null) {

			UserPermissionDto existingUserPermission = userPermissionFacade.findOneByUuid(userpermissionUpdate.getUuid());

//			List<Object[]> revisions = userPermissionFacade.findHistoryByUuid(userpermissionUpdate.getUuid(), null, null);
//			model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);
//
//			List<Object[]> fieldRevisions = userPermissionFacade.findFieldHistoryByUuid(userpermissionUpdate.getUuid(), null, null);
//			model.addAttribute(BackofficeWebConstants.Model.FIELD_REVISIONS, fieldRevisions);

			if (existingUserPermission != null) {
				model.addAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE, existingUserPermission);
			} else {
				userpermissionUpdate.setUuid(null);
				model.addAttribute(BackofficeWebConstants.Model.ERROR, localeMessageService.getMessage(BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_USERPERMISSION_LIST;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "create")
	public RedirectView create(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.CREATE) UserPermissionDto userpermissionCreate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userpermissionCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				userpermissionCreate = userPermissionFacade.create(userpermissionCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermissionDto.UUID, userpermissionCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "update")
	public RedirectView update(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE) UserPermissionDto userpermissionUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userpermissionUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				userpermissionUpdate = userPermissionFacade.update(userpermissionUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserPermissionDto.UUID, userpermissionUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;
	}

	@PostMapping(value = UserPermissionWebConstants.Path.USERPERMISSION, params = "delete")
	public RedirectView delete(@ModelAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE) UserPermissionDto userpermissionUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {

			userPermissionFacade.delete(userpermissionUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserPermissionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserPermissionWebConstants.ModelAttribute.UPDATE, userpermissionUpdate);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERPERMISSION);
		return redirectView;

	}
}

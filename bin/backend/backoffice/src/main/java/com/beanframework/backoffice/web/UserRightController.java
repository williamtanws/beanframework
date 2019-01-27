package com.beanframework.backoffice.web;

import java.util.Map;

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
import com.beanframework.backoffice.UserRightWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.facade.UserRightFacade;
import com.beanframework.core.facade.UserRightFacade.UserRightPreAuthorizeEnum;

@Controller
public class UserRightController extends AbstractController {

	@Autowired
	private UserRightFacade userRightFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(UserRightWebConstants.Path.USERRIGHT)
	private String PATH_USERRIGHT;

	@Value(UserRightWebConstants.View.LIST)
	private String VIEW_USERRIGHT_LIST;

	@ModelAttribute(UserRightWebConstants.ModelAttribute.CREATE)
	public UserRightDto populateUserRightCreate() throws Exception {
		return new UserRightDto();
	}

	@ModelAttribute(UserRightWebConstants.ModelAttribute.UPDATE)
	public UserRightDto populateUserRightForm() throws Exception {
		return new UserRightDto();
	}

	@PreAuthorize(UserRightPreAuthorizeEnum.READ)
	@GetMapping(value = UserRightWebConstants.Path.USERRIGHT)
	public String list(@ModelAttribute(UserRightWebConstants.ModelAttribute.UPDATE) UserRightDto updateDto, Model model) throws Exception {

		if (updateDto.getUuid() != null) {

			UserRightDto existsDto = userRightFacade.findOneByUuid(updateDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(UserRightWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				updateDto.setUuid(null);
				model.addAttribute(BackofficeWebConstants.Model.ERROR, localeMessageService.getMessage(BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_USERRIGHT_LIST;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "create")
	public RedirectView create(@ModelAttribute(UserRightWebConstants.ModelAttribute.CREATE) UserRightDto createDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = userRightFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserRightDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "update")
	public RedirectView update(@ModelAttribute(UserRightWebConstants.ModelAttribute.UPDATE) UserRightDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				updateDto = userRightFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserRightDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "delete")
	public RedirectView delete(@ModelAttribute(UserRightWebConstants.ModelAttribute.UPDATE) UserRightDto userrightUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {

			userRightFacade.delete(userrightUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserRightWebConstants.ModelAttribute.UPDATE, userrightUpdate);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;

	}
}

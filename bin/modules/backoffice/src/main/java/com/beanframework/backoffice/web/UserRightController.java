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
import com.beanframework.backoffice.UserRightWebConstants;
import com.beanframework.backoffice.UserRightWebConstants.UserRightPreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.facade.UserRightFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class UserRightController extends AbstractController {

	@Autowired
	private UserRightFacade dynamicFieldFacade;

	@Value(UserRightWebConstants.Path.USERRIGHT)
	private String PATH_USERRIGHT;

	@Value(UserRightWebConstants.Path.USERRIGHT_FORM)
	private String PATH_USERRIGHT_FORM;

	@Value(UserRightWebConstants.View.USERRIGHT)
	private String VIEW_USERRIGHT;

	@Value(UserRightWebConstants.View.USERRIGHT_FORM)
	private String VIEW_USERRIGHT_FORM;

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = UserRightWebConstants.Path.USERRIGHT)
	public String page(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto dynamicFieldDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_USERRIGHT;
	}

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = UserRightWebConstants.Path.USERRIGHT_FORM)
	public String form(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto dynamicFieldDto, Model model) throws Exception {

		if (dynamicFieldDto.getUuid() != null) {
			dynamicFieldDto = dynamicFieldFacade.findOneByUuid(dynamicFieldDto.getUuid());
		} else {
			dynamicFieldDto = dynamicFieldFacade.createDto();
		}
		model.addAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO, dynamicFieldDto);

		return VIEW_USERRIGHT_FORM;
	}

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto dynamicFieldDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dynamicFieldDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				dynamicFieldDto = dynamicFieldFacade.create(dynamicFieldDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dynamicFieldDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT_FORM);
		return redirectView;
	}

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto dynamicFieldDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dynamicFieldDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				dynamicFieldDto = dynamicFieldFacade.update(dynamicFieldDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dynamicFieldDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT_FORM);
		return redirectView;
	}

	@PreAuthorize(UserRightPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				dynamicFieldFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT_FORM);
		return redirectView;

	}
}

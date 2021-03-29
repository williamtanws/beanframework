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
import com.beanframework.backoffice.EmailWebConstants;
import com.beanframework.backoffice.EmailWebConstants.EmailPreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.core.facade.EmailFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class EmailController extends AbstractController {

	@Autowired
	private EmailFacade emailFacade;

	@Value(EmailWebConstants.Path.EMAIL)
	private String PATH_EMAIL;

	@Value(EmailWebConstants.Path.EMAIL_FORM)
	private String PATH_EMAIL_FORM;

	@Value(EmailWebConstants.View.EMAIL)
	private String VIEW_EMAIL;

	@Value(EmailWebConstants.View.EMAIL_FORM)
	private String VIEW_EMAIL_FORM;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = EmailWebConstants.Path.EMAIL)
	public String page(@Valid @ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_EMAIL;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = EmailWebConstants.Path.EMAIL_FORM)
	public String form(@Valid @ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model) throws Exception {

		if (emailDto.getUuid() != null) {
			emailDto = emailFacade.findOneByUuid(emailDto.getUuid());
		} else {
			emailDto = emailFacade.createDto();
		}
		model.addAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO, emailDto);

		return VIEW_EMAIL_FORM;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = EmailWebConstants.Path.EMAIL_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (emailDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				emailDto = emailFacade.create(emailDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, emailDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL_FORM);
		return redirectView;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = EmailWebConstants.Path.EMAIL_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (emailDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				emailDto = emailFacade.update(emailDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, emailDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL_FORM);
		return redirectView;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = EmailWebConstants.Path.EMAIL_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				emailFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL_FORM);
		return redirectView;
	}
}

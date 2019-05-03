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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmailWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmailDto;
import com.beanframework.core.facade.EmailFacade;
import com.beanframework.core.facade.EmailFacade.EmailPreAuthorizeEnum;

@Controller
public class EmailController extends AbstractController {

	@Autowired
	private EmailFacade emailFacade;

	@Value(EmailWebConstants.Path.EMAIL)
	private String PATH_EMAIL;

	@Value(EmailWebConstants.View.LIST)
	private String VIEW_EMAIL_LIST;

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = EmailWebConstants.Path.EMAIL)
	public String list(@ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);

		if (emailDto.getUuid() != null) {

			EmailDto existingEmail = emailFacade.findOneByUuid(emailDto.getUuid());

			if (existingEmail != null) {

				model.addAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO, existingEmail);
			} else {
				emailDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_EMAIL_LIST;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = EmailWebConstants.Path.EMAIL, params = "create")
	public String createView(@ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model) throws Exception {

		emailDto = emailFacade.createDto();
		model.addAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO, emailDto);
		model.addAttribute("create", true);

		return VIEW_EMAIL_LIST;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "create")
	public RedirectView create(@ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (emailDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				emailDto = emailFacade.create(emailDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmailDto.UUID, emailDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "update")
	public RedirectView update(@ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (emailDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				emailDto = emailFacade.update(emailDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmailDto.UUID, emailDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "delete")
	public RedirectView delete(@ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			emailFacade.delete(emailDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(EmailDto.UUID, emailDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;

	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "createattachment")
	public RedirectView createAttachment(@ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, @RequestParam("uploadAttachments") MultipartFile[] uploadAttachments) {

		if (emailDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				emailFacade.saveAttachment(emailDto, uploadAttachments);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmailDto.UUID, emailDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PreAuthorize(EmailPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "deleteattachment")
	public RedirectView deleteAttachment(@ModelAttribute(EmailWebConstants.ModelAttribute.EMAIL_DTO) EmailDto emailDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, @RequestParam("filename") String filename) {

		if (emailDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				emailFacade.deleteAttachment(emailDto.getUuid(), filename);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmailDto.UUID, emailDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}
}

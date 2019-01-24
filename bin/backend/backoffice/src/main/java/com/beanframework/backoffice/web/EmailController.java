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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmailWebConstants;
import com.beanframework.backoffice.data.EmailDto;
import com.beanframework.backoffice.facade.EmailFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;

@Controller
public class EmailController extends AbstractController {

	@Autowired
	private EmailFacade emailFacade;

	@Value(EmailWebConstants.Path.EMAIL)
	private String PATH_EMAIL;

	@Value(EmailWebConstants.View.LIST)
	private String VIEW_EMAIL_LIST;

	@ModelAttribute(EmailWebConstants.ModelAttribute.CREATE)
	public EmailDto populateEmailCreate(HttpServletRequest request) throws Exception {
		return new EmailDto();
	}

	@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE)
	public EmailDto populateEmailForm(HttpServletRequest request) throws Exception {
		return new EmailDto();
	}

	@GetMapping(value = EmailWebConstants.Path.EMAIL)
	public String list(@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) EmailDto emailUpdate, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		if (emailUpdate.getUuid() != null) {

			EmailDto existingEmail = emailFacade.findOneByUuid(emailUpdate.getUuid());

			if (existingEmail != null) {

//				List<Object[]> revisions = emailFacade.findHistoryByUuid(emailUpdate.getUuid(), null, null);
//				model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);

				model.addAttribute(EmailWebConstants.ModelAttribute.UPDATE, existingEmail);
			} else {
				emailUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_EMAIL_LIST;
	}

	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "create")
	public RedirectView create(@ModelAttribute(EmailWebConstants.ModelAttribute.CREATE) EmailDto emailCreate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (emailCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				emailCreate = emailFacade.create(emailCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmailDto.UUID, emailCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "update")
	public RedirectView update(@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) EmailDto emailUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				emailUpdate = emailFacade.update(emailUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmailDto.UUID, emailUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "delete")
	public RedirectView delete(@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) EmailDto emailUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			emailFacade.delete(emailUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(EmailDto.UUID, emailUpdate.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;

	}

	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "createattachment")
	public RedirectView createAttachment(@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) EmailDto emailUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, @RequestParam("uploadAttachments") MultipartFile[] uploadAttachments) {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				emailFacade.saveAttachment(emailUpdate, uploadAttachments);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmailDto.UUID, emailUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "deleteattachment")
	public RedirectView deleteAttachment(@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) EmailDto emailUpdate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, @RequestParam("filename") String filename) {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				emailFacade.deleteAttachment(emailUpdate.getUuid(), filename);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmailDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmailDto.UUID, emailUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}
}

package com.beanframework.backoffice.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebEmailConstants;
import com.beanframework.backoffice.domain.EmailSearch;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum.Status;
import com.beanframework.email.service.EmailFacade;

@Controller
public class EmailController {

	@Autowired
	private EmailFacade emailFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebEmailConstants.Path.EMAIL)
	private String PATH_EMAIL;

	@Value(WebEmailConstants.View.LIST)
	private String VIEW_EMAIL_LIST;

	@Value(WebEmailConstants.LIST_SIZE)
	private int MODULE_EMAIL_LIST_SIZE;

	private Page<Email> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_EMAIL_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		EmailSearch emailSearch = (EmailSearch) model.asMap().get(WebEmailConstants.ModelAttribute.SEARCH);

		Email email = new Email();
		email.setToRecipients(emailSearch.getEmailSearch());
		email.setBccRecipients(emailSearch.getEmailSearch());
		email.setStatus(Status.fromName(emailSearch.getEmailSearch()));

		if (properties == null) {
			properties = new String[1];
			properties[0] = Email.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Email> pagination = emailFacade.page(email, page, size, direction, properties);

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, EmailSearch emailSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_EMAIL_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("emailSearch",emailSearch.getEmailSearch());
		redirectAttributes.addFlashAttribute(WebEmailConstants.ModelAttribute.SEARCH, emailSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebEmailConstants.ModelAttribute.CREATE)
	public Email populateEmailCreate(HttpServletRequest request) {
		return emailFacade.create();
	}

	@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE)
	public Email populateEmailForm(HttpServletRequest request) {
		return emailFacade.create();
	}

	@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH)
	public EmailSearch populateEmailSearch(HttpServletRequest request) {
		return new EmailSearch();
	}

	@PreAuthorize(WebEmailConstants.PreAuthorize.READ)
	@GetMapping(value = WebEmailConstants.Path.EMAIL)
	public String list(@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (emailUpdate.getUuid() != null) {
			Email existingEmail = emailFacade.findByUuid(emailUpdate.getUuid());
			if (existingEmail != null) {
				model.addAttribute(WebEmailConstants.ModelAttribute.UPDATE, existingEmail);
			} else {
				emailUpdate.setUuid(null);
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_EMAIL_LIST;
	}

	@PreAuthorize(WebEmailConstants.PreAuthorize.CREATE)
	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "create")
	public RedirectView create(
			@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.CREATE) Email emailCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (emailCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			emailCreate = emailFacade.save(emailCreate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Email.UUID, emailCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PreAuthorize(WebEmailConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "update")
	public RedirectView update(
			@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			emailUpdate = emailFacade.save(emailUpdate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Email.UUID, emailUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}
	
	@PreAuthorize(WebEmailConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "createattachment")
	public RedirectView createAttachment(
			@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes, @RequestParam("uploadAttachments") MultipartFile[] uploadAttachments) {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			emailFacade.saveAttachment(emailUpdate, uploadAttachments, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Email.UUID, emailUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}
	
	@PreAuthorize(WebEmailConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "deleteattachment")
	public RedirectView deleteAttachment(
			@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes, @RequestParam("filename") String filename) {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			emailFacade.deleteAttachment(emailUpdate.getUuid(), filename, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Email.UUID, emailUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PreAuthorize(WebEmailConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors() == false) {
			emailFacade.delete(emailUpdate.getUuid(), bindingResult);
		}

		if (bindingResult.hasErrors()) {

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());
			redirectAttributes.addAttribute(Email.UUID, emailUpdate.getUuid());
		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;

	}
}

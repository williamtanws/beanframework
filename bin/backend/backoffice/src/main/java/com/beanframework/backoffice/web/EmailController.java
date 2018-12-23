package com.beanframework.backoffice.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebEmailConstants;
import com.beanframework.backoffice.domain.EmailSearch;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.email.domain.Email;
import com.beanframework.email.domain.EmailEnum.Status;
import com.beanframework.email.domain.EmailSpecification;
import com.beanframework.email.service.EmailFacade;

@Controller
public class EmailController extends AbstractCommonController {

	@Autowired
	private EmailFacade emailFacade;

	@Value(WebEmailConstants.Path.EMAIL)
	private String PATH_EMAIL;

	@Value(WebEmailConstants.View.LIST)
	private String VIEW_EMAIL_LIST;

	@Value(WebEmailConstants.LIST_SIZE)
	private int MODULE_EMAIL_LIST_SIZE;

	private Page<Email> getPagination(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
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

		Page<Email> pagination = emailFacade.findPage(EmailSpecification.findByCriteria(email),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

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
		redirectAttributes.addAttribute("emailSearch", emailSearch.getEmailSearch());
		redirectAttributes.addFlashAttribute(WebEmailConstants.ModelAttribute.SEARCH, emailSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebEmailConstants.ModelAttribute.CREATE)
	public Email populateEmailCreate(HttpServletRequest request) throws Exception {
		return emailFacade.create();
	}

	@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE)
	public Email populateEmailForm(HttpServletRequest request) throws Exception {
		return emailFacade.create();
	}

	@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH)
	public EmailSearch populateEmailSearch(HttpServletRequest request) {
		return new EmailSearch();
	}

	@GetMapping(value = WebEmailConstants.Path.EMAIL)
	public String list(@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (emailUpdate.getUuid() != null) {

			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Email.UUID, emailUpdate.getUuid());

			Email existingEmail = emailFacade.findOneDtoByProperties(properties);

			if (existingEmail != null) {
				model.addAttribute(WebEmailConstants.ModelAttribute.UPDATE, existingEmail);
			} else {
				emailUpdate.setUuid(null);
				addErrorMessage(model, WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_EMAIL_LIST;
	}

	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "create")
	public RedirectView create(@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.CREATE) Email emailCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (emailCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			try {
				emailFacade.createDto(emailCreate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Email.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Email.UUID, emailCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "update")
	public RedirectView update(@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				emailFacade.updateDto(emailUpdate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Email.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Email.UUID, emailUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "createattachment")
	public RedirectView createAttachment(
			@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes,
			@RequestParam("uploadAttachments") MultipartFile[] uploadAttachments) {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				emailFacade.saveAttachment(emailUpdate, uploadAttachments);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Email.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Email.UUID, emailUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

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
			try {
				emailFacade.deleteAttachment(emailUpdate.getUuid(), filename);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Email.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Email.UUID, emailUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;
	}

	@PostMapping(value = WebEmailConstants.Path.EMAIL, params = "delete")
	public RedirectView delete(@ModelAttribute(WebEmailConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(WebEmailConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			emailFacade.delete(emailUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Email.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(Email.UUID, emailUpdate.getUuid());
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, emailSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMAIL);
		return redirectView;

	}
}

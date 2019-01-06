package com.beanframework.backoffice.web;

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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmailWebConstants;
import com.beanframework.backoffice.data.EmailSearch;
import com.beanframework.backoffice.data.EmailSpecification;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.email.domain.Email;
import com.beanframework.email.service.EmailFacade;

@Controller
public class EmailController extends AbstractController {

	@Autowired
	private EmailFacade emailFacade;

	@Value(EmailWebConstants.Path.EMAIL)
	private String PATH_EMAIL;

	@Value(EmailWebConstants.View.LIST)
	private String VIEW_EMAIL_LIST;

	@Value(EmailWebConstants.LIST_SIZE)
	private int MODULE_EMAIL_LIST_SIZE;

	private Page<Email> getPagination(EmailSearch emailSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_EMAIL_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = Email.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Email> pagination = emailFacade.findPage(EmailSpecification.findByCriteria(emailSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, EmailSearch emailSearch) {
		
		emailSearch.setSearchAll((String)requestParams.get("emailSearch.searchAll"));
		emailSearch.setId((String)requestParams.get("emailSearch.id"));
		
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_EMAIL_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", emailSearch.getSearchAll());
		redirectAttributes.addAttribute("id", emailSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(EmailWebConstants.ModelAttribute.CREATE)
	public Email populateEmailCreate(HttpServletRequest request) throws Exception {
		return emailFacade.create();
	}

	@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE)
	public Email populateEmailForm(HttpServletRequest request) throws Exception {
		return emailFacade.create();
	}

	@ModelAttribute(EmailWebConstants.ModelAttribute.SEARCH)
	public EmailSearch populateEmailSearch(HttpServletRequest request) {
		return new EmailSearch();
	}

	@GetMapping(value = EmailWebConstants.Path.EMAIL)
	public String list(@ModelAttribute(EmailWebConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(emailSearch, model, requestParams));

		if (emailUpdate.getUuid() != null) {

			Email existingEmail = emailFacade.findOneDtoByUuid(emailUpdate.getUuid());

			if (existingEmail != null) {
				model.addAttribute(EmailWebConstants.ModelAttribute.UPDATE, existingEmail);
			} else {
				emailUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_EMAIL_LIST;
	}

	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "create")
	public RedirectView create(@ModelAttribute(EmailWebConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(EmailWebConstants.ModelAttribute.CREATE) Email emailCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (emailCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {
			try {
				emailCreate = emailFacade.createDto(emailCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "update")
	public RedirectView update(@ModelAttribute(EmailWebConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				emailUpdate = emailFacade.updateDto(emailUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
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

	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "delete")
	public RedirectView delete(@ModelAttribute(EmailWebConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			emailFacade.delete(emailUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
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
	
	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "createattachment")
	public RedirectView createAttachment(
			@ModelAttribute(EmailWebConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes,
			@RequestParam("uploadAttachments") MultipartFile[] uploadAttachments) {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				emailFacade.saveAttachment(emailUpdate, uploadAttachments);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
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
	
	@PostMapping(value = EmailWebConstants.Path.EMAIL, params = "deleteattachment")
	public RedirectView deleteAttachment(
			@ModelAttribute(EmailWebConstants.ModelAttribute.SEARCH) EmailSearch emailSearch,
			@ModelAttribute(EmailWebConstants.ModelAttribute.UPDATE) Email emailUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes, @RequestParam("filename") String filename) {

		if (emailUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {
			try {
				emailFacade.deleteAttachment(emailUpdate.getUuid(), filename);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
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
}

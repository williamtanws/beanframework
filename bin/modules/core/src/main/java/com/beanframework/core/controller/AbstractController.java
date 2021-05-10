package com.beanframework.core.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.common.data.GenericDto;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.facade.CommonFacade;

import io.micrometer.core.instrument.util.StringUtils;

public abstract class AbstractController {

	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	public static final String INFO = "info";
	public static final String PAGINATION = "pagination";
	public static final String MENU_NAVIGATION = "menuNavigation";
	public static final String MODULE_LANGUAGES = "moduleLanguages";
	public static final String REVISIONS = "revisions";
	public static final String FIELD_REVISIONS = "fieldRevisions";
	public static final String LOGIN_URL = "loginUrl";
	
	public static final String SAVE_SUCCESS = "module.common.save.success";
	public static final String SAVE_FAIL = "module.common.save.fail";
	public static final String DELETE_SUCCESS = "module.common.delete.success";
	public static final String DELETE_FAIL = "module.common.delete.fail";
	public static final String RECORD_UUID_NOT_FOUND = "module.common.record.uuid.notfound";

	@Autowired
	protected LocaleMessageService localeMessageService;
	
	@Autowired
	protected CommonFacade commonFacade;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RedirectView create(GenericDto dto, Class entityClass, Class dtoClass, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {
		if (dto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				dto = commonFacade.createDto(entityClass, dtoClass);

				addSuccessMessage(redirectAttributes, SAVE_SUCCESS);
			} catch (Exception e) {
				addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl("path."+entityClass.getSimpleName().toLowerCase()+".form");
		return redirectView;
	}

	@SuppressWarnings("rawtypes")
	public void addErrorMessage(Class objectClass, String message, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		StringBuilder errorMessage = new StringBuilder();
		List<ObjectError> errors = bindingResult.getAllErrors();
		for (ObjectError error : errors) {
			if (errorMessage.length() != 0) {
				errorMessage.append("<br>");
			}
			if(error.getDefaultMessage().contains("ConstraintViolationException")) {
				errorMessage.append(localeMessageService.getMessage("error.ConstraintViolationException"));
			}
			else {
				errorMessage.append(localeMessageService.getMessage(error.getDefaultMessage()));				
			}
		}
		
		if(StringUtils.isNotBlank(message)) {
			bindingResult.reject(objectClass.getSimpleName(), message);
			errorMessage.append("<br>");
			errorMessage.append(message);
		}

		redirectAttributes.addFlashAttribute(ERROR, errorMessage.toString());
	}

	public void addSuccessMessage(RedirectAttributes redirectAttributes, String messageCode) {
		redirectAttributes.addFlashAttribute(SUCCESS, localeMessageService.getMessage(messageCode));
	}

	public void addErrorMessage(Model model, String messageCode) {
		model.addAttribute(ERROR, localeMessageService.getMessage(messageCode));
	}
}

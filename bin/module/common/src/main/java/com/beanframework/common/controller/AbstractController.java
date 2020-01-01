package com.beanframework.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.service.LocaleMessageService;

import io.micrometer.core.instrument.util.StringUtils;

public class AbstractController {

	public static final String ERROR = "error";
	public static final String SUCCESS = "success";

	@Autowired
	protected LocaleMessageService localeMessageService;

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

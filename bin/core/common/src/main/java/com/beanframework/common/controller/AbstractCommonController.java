package com.beanframework.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.service.LocaleMessageService;

public class AbstractCommonController {

	public static final String ERROR = "error";
	public static final String SUCCESS = "success";

	@Autowired
	private LocaleMessageService localeMessageService;

	@SuppressWarnings("rawtypes")
	public void addErrorMessage(Class objectClass, String message, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		bindingResult.reject(objectClass.getSimpleName(), message);

		StringBuilder errorMessage = new StringBuilder();
		List<ObjectError> errors = bindingResult.getAllErrors();
		for (ObjectError error : errors) {
			if (errorMessage.length() != 0) {
				errorMessage.append("<br>");
			}
			errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
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

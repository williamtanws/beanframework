package com.beanframework.backoffice.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmployeeWebConstants;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.facade.EmployeeFacade;
import com.beanframework.media.MediaConstants;
import com.beanframework.user.UserConstants;

@Controller
public class EmployeeProfileController {

	@Autowired
	private EmployeeFacade employeeFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(EmployeeWebConstants.Path.PROFILE)
	private String PATH_PROFILE;

	@Value(EmployeeWebConstants.View.PROFILE)
	private String VIEW_EMPLOYEE_PROFILE;
	
	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Value(UserConstants.USER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(BackofficeWebConstants.Configuration.DEFAULT_AVATAR)
	public String CONFIGURATION_DEFAULT_AVATAR;

	@ModelAttribute(EmployeeWebConstants.ModelAttribute.PROFILE)
	public EmployeeDto populateEmployeeForm(HttpServletRequest request) throws Exception {
		return new EmployeeDto();
	}

	@GetMapping(value = EmployeeWebConstants.Path.PROFILE)
	public String profile(@ModelAttribute(EmployeeWebConstants.ModelAttribute.PROFILE) EmployeeDto employeeProfile, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		employeeProfile = employeeFacade.getCurrentUser();

		model.addAttribute(EmployeeWebConstants.ModelAttribute.PROFILE, employeeProfile);

		return VIEW_EMPLOYEE_PROFILE;
	}

	@PostMapping(value = EmployeeWebConstants.Path.PROFILE, params = "update")
	public RedirectView update(@ModelAttribute(EmployeeWebConstants.ModelAttribute.PROFILE) EmployeeDto employeeProfile, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		try {
			EmployeeDto employee = employeeFacade.getCurrentUser();
			if (employee.getUuid().equals(employeeProfile.getUuid()) == false)
				throw new Exception("Invalid attempted employee profile update.");

			employeeProfile = employeeFacade.saveProfile(employeeProfile);

			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.SUCCESS, localeMessageService.getMessage(BackofficeWebConstants.Locale.SAVE_SUCCESS));
		} catch (BusinessException e) {
			bindingResult.reject(EmployeeDto.class.getSimpleName(), e.getMessage());

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, errorMessage.toString());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_PROFILE);
		return redirectView;
	}
}

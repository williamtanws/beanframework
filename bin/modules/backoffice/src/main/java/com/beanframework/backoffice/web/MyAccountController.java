package com.beanframework.backoffice.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.beanframework.backoffice.MyAccountWebConstants;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.facade.UserFacade;


@Controller
public class MyAccountController {

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(MyAccountWebConstants.Path.MYACCOUNT)
	private String PATH_PROFILE;

	@Value(MyAccountWebConstants.View.MYACCOUNT)
	private String VIEW_USER_PROFILE;

	@Value(BackofficeWebConstants.Configuration.DEFAULT_AVATAR)
	public String CONFIGURATION_DEFAULT_AVATAR;

	@Valid @ModelAttribute(MyAccountWebConstants.ModelAttribute.MYACCOUNT)
	public UserDto populateUserForm(HttpServletRequest request) throws Exception {
		return new UserDto();
	}

	@GetMapping(value = MyAccountWebConstants.Path.MYACCOUNT)
	public String profile(@Valid @ModelAttribute(MyAccountWebConstants.ModelAttribute.MYACCOUNT) UserDto userProfile, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		userProfile = userFacade.getCurrentUser();

		model.addAttribute(MyAccountWebConstants.ModelAttribute.MYACCOUNT, userProfile);

		return VIEW_USER_PROFILE;
	}

	@PostMapping(value = MyAccountWebConstants.Path.MYACCOUNT, params = "update")
	public RedirectView update(@Valid @ModelAttribute(MyAccountWebConstants.ModelAttribute.MYACCOUNT) UserDto userProfile, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		try {
			UserDto user = userFacade.getCurrentUser();
			if (user.getUuid().equals(userProfile.getUuid()) == false)
				throw new Exception("Invalid attempted user profile update.");

			userProfile = userFacade.saveProfile(userProfile);

			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.SUCCESS, localeMessageService.getMessage(BackofficeWebConstants.Locale.SAVE_SUCCESS));
		} catch (BusinessException e) {
			bindingResult.reject(UserDto.class.getSimpleName(), e.getMessage());

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

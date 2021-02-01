package com.beanframework.backoffice.web;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmployeeWebConstants;
import com.beanframework.backoffice.EmployeeWebConstants.EmployeeSessionPreAuthorizeEnum;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.facade.UserFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class UserSessionController {

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(EmployeeWebConstants.Session.Path.SESSION)
	private String PATH_SESSION;

	@Value(EmployeeWebConstants.Session.View.SESSION_LIST)
	private String VIEW_EMPLOYEE_SESSIONLIST;

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = EmployeeWebConstants.Session.Path.SESSION)
	public String list(Model model, @RequestParam Map<String, Object> requestParams) {

		model.addAttribute("sessions", userFacade.findAllSessions());

		return VIEW_EMPLOYEE_SESSIONLIST;
	}

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = EmployeeWebConstants.Session.Path.SESSION, params = "delete")
	public RedirectView expireAllSessionsByUuid(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		String uuidStr = (String) requestParams.get("uuid");

		userFacade.expireAllSessionsByUuid(UUID.fromString(uuidStr));

		redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.SUCCESS, localeMessageService.getMessage(BackofficeWebConstants.Locale.DELETE_SUCCESS));

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SESSION);
		return redirectView;

	}

	@PreAuthorize(EmployeeSessionPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = EmployeeWebConstants.Session.Path.SESSION, params = "deleteall")
	public RedirectView expireAllSessions(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		userFacade.expireAllSessions();

		redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.SUCCESS, localeMessageService.getMessage(BackofficeWebConstants.Locale.DELETE_SUCCESS));

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SESSION);
		return redirectView;

	}
}

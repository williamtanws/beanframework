package com.beanframework.backoffice.web;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebEmployeeConstants;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.employee.service.EmployeeFacade;

@Controller
public class EmployeeSessionController {

	@Autowired
	private EmployeeFacade employeeFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebEmployeeConstants.Session.Path.SESSION)
	private String PATH_SESSION;

	@Value(WebEmployeeConstants.Session.View.SESSION_LIST)
	private String VIEW_EMPLOYEE_SESSIONLIST;

	@GetMapping(value = WebEmployeeConstants.Session.Path.SESSION)
	public String list(Model model, @RequestParam Map<String, Object> requestParams) {

		model.addAttribute("sessions", employeeFacade.findAllSessions());

		return VIEW_EMPLOYEE_SESSIONLIST;
	}

	@PostMapping(value = WebEmployeeConstants.Session.Path.SESSION, params = "delete")
	public RedirectView delete(Model model, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		String uuidStr = (String) requestParams.get("uuid");

		employeeFacade.expireAllSessionsByUuid(UUID.fromString(uuidStr));

		redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
				localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SESSION);
		return redirectView;

	}
	
	@PostMapping(value = WebEmployeeConstants.Session.Path.SESSION, params = "deleteall")
	public RedirectView deleteall(Model model, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		employeeFacade.expireAllSessions();

		redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
				localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SESSION);
		return redirectView;

	}
}

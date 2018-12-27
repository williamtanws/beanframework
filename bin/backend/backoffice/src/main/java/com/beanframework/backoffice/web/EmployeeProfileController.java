package com.beanframework.backoffice.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebEmployeeConstants;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeFacade;

@Controller
public class EmployeeProfileController {
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private EmployeeFacade employeeFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(WebEmployeeConstants.Path.PROFILE)
	private String PATH_PROFILE;

	@Value(WebEmployeeConstants.View.PROFILE)
	private String VIEW_EMPLOYEE_PROFILE;
	
	@Value(EmployeeConstants.PROFILE_PICTURE_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@ModelAttribute(WebEmployeeConstants.ModelAttribute.PROFILE)
	public Employee populateEmployeeForm(HttpServletRequest request) throws Exception {
		return modelService.create(Employee.class);
	}

	@GetMapping(value = WebEmployeeConstants.Path.PROFILE)
	public String profile(@ModelAttribute(WebEmployeeConstants.ModelAttribute.PROFILE) Employee employeeProfile,
			Model model, @RequestParam Map<String, Object> requestParams) {

		employeeProfile = employeeFacade.getCurrentEmployee();

		model.addAttribute(WebEmployeeConstants.ModelAttribute.PROFILE, employeeProfile);

		return VIEW_EMPLOYEE_PROFILE;
	}

	@GetMapping(value = WebEmployeeConstants.Path.PROFILE_PICTURE)
	public @ResponseBody byte[] getImage(@RequestParam Map<String, Object> requestParams) throws IOException {
		Employee employee = employeeFacade.getCurrentEmployee();
		
		String type = (String) requestParams.get("type");
		
		if(StringUtils.isBlank(type) || (type.equals("original") == false && type.equals("thumbnail") == false)) {
			type = "thumbnail";
		}
		
		InputStream targetStream;
		File picture = new File(PROFILE_PICTURE_LOCATION+File.separator+employee.getUuid()+File.separator+type+".png");
		
		if(picture.exists()) {
			targetStream = new FileInputStream(picture);
		}
		else {
			ClassPathResource resource = new ClassPathResource("static/common/img/avatar.png"); 
			targetStream = resource.getInputStream();
		}
		
		return IOUtils.toByteArray(targetStream);
	}

	@PostMapping(value = WebEmployeeConstants.Path.PROFILE, params = "update")
	public RedirectView update(@ModelAttribute(WebEmployeeConstants.ModelAttribute.PROFILE) Employee employeeProfile,
			Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes, @RequestParam("picture") MultipartFile picture) {

		try {
			employeeProfile = employeeFacade.saveProfile(employeeProfile, picture);
			
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
		} catch (BusinessException e) {
			bindingResult.reject(Employee.class.getSimpleName(), e.getMessage());
			
			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_PROFILE);
		return redirectView;
	}
}

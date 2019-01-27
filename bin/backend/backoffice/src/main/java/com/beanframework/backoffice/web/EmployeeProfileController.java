package com.beanframework.backoffice.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmployeeWebConstants;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.BackofficeConfigurationDto;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.facade.BackofficeConfigurationFacade;
import com.beanframework.core.facade.EmployeeFacade;
import com.beanframework.employee.EmployeeConstants;

@Controller
public class EmployeeProfileController {

	@Autowired
	private EmployeeFacade employeeFacade;

	@Autowired
	private BackofficeConfigurationFacade configurationFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(EmployeeWebConstants.Path.PROFILE)
	private String PATH_PROFILE;

	@Value(EmployeeWebConstants.View.PROFILE)
	private String VIEW_EMPLOYEE_PROFILE;

	@Value(EmployeeConstants.PROFILE_PICTURE_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(BackofficeWebConstants.Configuration.DEFAULT_AVATAR)
	public String CONFIGURATION_DEFAULT_AVATAR;

	@ModelAttribute(EmployeeWebConstants.ModelAttribute.PROFILE)
	public EmployeeDto populateEmployeeForm(HttpServletRequest request) throws Exception {
		return new EmployeeDto();
	}

	@GetMapping(value = EmployeeWebConstants.Path.PROFILE)
	public String profile(@ModelAttribute(EmployeeWebConstants.ModelAttribute.PROFILE) EmployeeDto employeeProfile, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		employeeProfile = employeeFacade.getProfile();

		model.addAttribute(EmployeeWebConstants.ModelAttribute.PROFILE, employeeProfile);

		return VIEW_EMPLOYEE_PROFILE;
	}

	@GetMapping(value = EmployeeWebConstants.Path.PROFILE_PICTURE, produces = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getImage(@RequestParam Map<String, Object> requestParams) throws Exception {

		UUID uuid = null;

		if (requestParams.get("uuid") != null) {
			uuid = UUID.fromString((String) requestParams.get("uuid"));
		} else {
			EmployeeDto employee = employeeFacade.getProfile();
			uuid = employee.getUuid();
		}

		String type = (String) requestParams.get("type");

		if (StringUtils.isBlank(type) || (type.equals("original") == false && type.equals("thumbnail") == false)) {
			type = "thumbnail";
		}

		File profilePicture = new File(PROFILE_PICTURE_LOCATION + File.separator + uuid + File.separator + type + ".png");
		profilePicture = new File(profilePicture.getAbsolutePath());
		if (profilePicture.exists()) {

			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(Files.readAllBytes(profilePicture.toPath()));
		} else {

			BackofficeConfigurationDto configuration = configurationFacade.findOneDtoById(CONFIGURATION_DEFAULT_AVATAR);

			if (configuration == null) {
				return null;
			} else {

				ClassPathResource resource = new ClassPathResource(configuration.getValue());
				Path defaultImage = Paths.get(resource.getURI());

				return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(Files.readAllBytes(defaultImage));
			}
		}
	}

	@PostMapping(value = EmployeeWebConstants.Path.PROFILE, params = "update")
	public RedirectView update(@ModelAttribute(EmployeeWebConstants.ModelAttribute.PROFILE) EmployeeDto employeeProfile, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, @RequestParam("picture") MultipartFile picture) throws Exception {

		try {
			EmployeeDto employee = employeeFacade.getProfile();
			if (employee.getUuid().equals(employeeProfile.getUuid()) == false)
				throw new Exception("Invalid attempted employee profile update.");

			employeeProfile = employeeFacade.saveProfile(employeeProfile, picture);

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

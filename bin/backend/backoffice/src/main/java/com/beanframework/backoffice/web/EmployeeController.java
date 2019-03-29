package com.beanframework.backoffice.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmployeeWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.facade.EmployeeFacade;

@Controller
public class EmployeeController extends AbstractController {

	@Autowired
	private EmployeeFacade employeeFacade;

	@Value(EmployeeWebConstants.Path.EMPLOYEE)
	private String PATH_EMPLOYEE;

	@Value(EmployeeWebConstants.View.LIST)
	private String VIEW_EMPLOYEE_LIST;

	@GetMapping(value = EmployeeWebConstants.Path.EMPLOYEE)
	public String list(@ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);

		if (employeeDto.getUuid() != null) {

			EmployeeDto existingEmployee = employeeFacade.findOneByUuid(employeeDto.getUuid());

			if (existingEmployee != null) {

				model.addAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO, existingEmployee);
			} else {
				employeeDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_EMPLOYEE_LIST;
	}

	@GetMapping(value = EmployeeWebConstants.Path.EMPLOYEE, params = "create")
	public String createView(@ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto, Model model) throws Exception {

		employeeDto = employeeFacade.createDto();
		model.addAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO, employeeDto);
		model.addAttribute("create", true);

		return VIEW_EMPLOYEE_LIST;
	}

	@PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE, params = "create")
	public RedirectView create(@ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (employeeDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				employeeDto = employeeFacade.create(employeeDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmployeeDto.UUID, employeeDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;
	}

	@PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE, params = "update")
	public RedirectView update(@ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (employeeDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			// UserGroup
			if (employeeDto.getTableUserGroups() != null) {
				List<UserGroupDto> userGroups = employeeFacade.findOneByUuid(employeeDto.getUuid()).getUserGroups();

				for (int i = 0; i < employeeDto.getTableUserGroups().length; i++) {

					boolean remove = true;
					if (employeeDto.getTableSelectedUserGroups() != null && employeeDto.getTableSelectedUserGroups().length > i
							&& BooleanUtils.parseBoolean(employeeDto.getTableSelectedUserGroups()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<UserGroupDto> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(employeeDto.getTableUserGroups()[i]))) {
								userGroupsIterator.remove();
							}
						}
					} else {
						boolean add = true;
						for (Iterator<UserGroupDto> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(employeeDto.getTableUserGroups()[i]))) {
								add = false;
							}
						}

						if (add) {
							UserGroupDto userGroup = new UserGroupDto();
							userGroup.setUuid(UUID.fromString(employeeDto.getTableUserGroups()[i]));
							userGroups.add(userGroup);
						}
					}
				}
				employeeDto.setUserGroups(userGroups);
			}

			try {
				employeeDto = employeeFacade.update(employeeDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);

			}
		}

		redirectAttributes.addAttribute(EmployeeDto.UUID, employeeDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;
	}

	@PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE, params = "delete")
	public RedirectView delete(@ModelAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO) EmployeeDto employeeDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			employeeFacade.delete(employeeDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(EmployeeWebConstants.ModelAttribute.EMPLOYEE_DTO, employeeDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;

	}
}

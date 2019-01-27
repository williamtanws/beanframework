package com.beanframework.backoffice.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.beanframework.core.facade.EmployeeFacade.EmployeePreAuthorizeEnum;

@Controller
public class EmployeeController extends AbstractController {

	@Autowired
	private EmployeeFacade employeeFacade;

	@Value(EmployeeWebConstants.Path.EMPLOYEE)
	private String PATH_EMPLOYEE;

	@Value(EmployeeWebConstants.View.LIST)
	private String VIEW_EMPLOYEE_LIST;

	@ModelAttribute(EmployeeWebConstants.ModelAttribute.CREATE)
	public EmployeeDto create(HttpServletRequest request) throws Exception {
		return new EmployeeDto();
	}

	@ModelAttribute(EmployeeWebConstants.ModelAttribute.UPDATE)
	public EmployeeDto update(HttpServletRequest request) throws Exception {
		return new EmployeeDto();
	}

	@PreAuthorize(EmployeePreAuthorizeEnum.READ)
	@GetMapping(value = EmployeeWebConstants.Path.EMPLOYEE)
	public String list(@ModelAttribute(EmployeeWebConstants.ModelAttribute.UPDATE) EmployeeDto updateDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		if (updateDto.getUuid() != null) {

			EmployeeDto existingEmployee = employeeFacade.findOneByUuid(updateDto.getUuid());

			if (existingEmployee != null) {

				model.addAttribute(EmployeeWebConstants.ModelAttribute.UPDATE, existingEmployee);
			} else {
				updateDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_EMPLOYEE_LIST;
	}

	@PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE, params = "create")
	public RedirectView create(@ModelAttribute(EmployeeWebConstants.ModelAttribute.CREATE) EmployeeDto employeeCreate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (employeeCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				employeeCreate = employeeFacade.create(employeeCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(EmployeeDto.UUID, employeeCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;
	}

	@PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE, params = "update")
	public RedirectView update(@ModelAttribute(EmployeeWebConstants.ModelAttribute.UPDATE) EmployeeDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			// UserGroup
			if (updateDto.getTableUserGroups() != null) {
				List<UserGroupDto> userGroups = employeeFacade.findOneByUuid(updateDto.getUuid()).getUserGroups();

				for (int i = 0; i < updateDto.getTableUserGroups().length; i++) {

					boolean remove = true;
					if (updateDto.getTableSelectedUserGroups() != null && updateDto.getTableSelectedUserGroups().length > i && BooleanUtils.parseBoolean(updateDto.getTableSelectedUserGroups()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<UserGroupDto> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(updateDto.getTableUserGroups()[i]))) {
								userGroupsIterator.remove();
							}
						}
					} else {
						boolean add = true;
						for (Iterator<UserGroupDto> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(updateDto.getTableUserGroups()[i]))) {
								add = false;
							}
						}

						if (add) {
							UserGroupDto userGroup = new UserGroupDto();
							userGroup.setUuid(UUID.fromString(updateDto.getTableUserGroups()[i]));
							userGroups.add(userGroup);
						}
					}
				}
				updateDto.setUserGroups(userGroups);
			}

			try {
				updateDto = employeeFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);

			}
		}

		redirectAttributes.addAttribute(EmployeeDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;
	}

	@PostMapping(value = EmployeeWebConstants.Path.EMPLOYEE, params = "delete")
	public RedirectView delete(@ModelAttribute(EmployeeWebConstants.ModelAttribute.UPDATE) EmployeeDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			employeeFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(EmployeeDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(EmployeeWebConstants.ModelAttribute.UPDATE, updateDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;

	}
}

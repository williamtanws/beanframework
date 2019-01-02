package com.beanframework.backoffice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebEmployeeConstants;
import com.beanframework.backoffice.data.EmployeeSearch;
import com.beanframework.backoffice.data.EmployeeSpecification;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeFacade;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupFacade;

@Controller
public class EmployeeController extends AbstractController {

	@Autowired
	private EmployeeFacade employeeFacade;
	
	@Autowired
	private UserGroupFacade userGroupFacade;

	@Value(WebEmployeeConstants.Path.EMPLOYEE)
	private String PATH_EMPLOYEE;

	@Value(WebEmployeeConstants.View.LIST)
	private String VIEW_EMPLOYEE_LIST;

	@Value(WebEmployeeConstants.LIST_SIZE)
	private int MODULE_EMPLOYEE_LIST_SIZE;

	private Page<Employee> getPagination(EmployeeSearch employeeSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_EMPLOYEE_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);
		
		if (properties == null) {
			properties = new String[1];
			properties[0] = Employee.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Employee> pagination = employeeFacade.findPage(EmployeeSpecification.findByCriteria(employeeSearch),
				PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, EmployeeSearch employeeSearch) {
		
		employeeSearch.setSearchAll((String)requestParams.get("employeeSearch.searchAll"));
		employeeSearch.setId((String)requestParams.get("employeeSearch.id"));
		
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_EMPLOYEE_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", employeeSearch.getSearchAll());
		redirectAttributes.addAttribute("id", employeeSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(WebEmployeeConstants.ModelAttribute.CREATE)
	public Employee populateEmployeeCreate(HttpServletRequest request) throws Exception {
		return employeeFacade.create();
	}

	@ModelAttribute(WebEmployeeConstants.ModelAttribute.UPDATE)
	public Employee populateEmployeeForm(HttpServletRequest request) throws Exception {
		return employeeFacade.create();
	}

	@ModelAttribute(WebEmployeeConstants.ModelAttribute.SEARCH)
	public EmployeeSearch populateEmployeeSearch(HttpServletRequest request) {
		return new EmployeeSearch();
	}

	@GetMapping(value = WebEmployeeConstants.Path.EMPLOYEE)
	public String list(@ModelAttribute(WebEmployeeConstants.ModelAttribute.SEARCH) EmployeeSearch employeeSearch,
			@ModelAttribute(WebEmployeeConstants.ModelAttribute.UPDATE) Employee employeeUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(employeeSearch, model, requestParams));

		if (employeeUpdate.getUuid() != null) {

			Employee existingEmployee = employeeFacade.findOneDtoByUuid(employeeUpdate.getUuid());

			if (existingEmployee != null) {

				Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
				sorts.put(UserGroup.CREATED_DATE, Sort.Direction.DESC);

				List<UserGroup> userGroups = userGroupFacade.findDtoBySorts(sorts);

				for (int i = 0; i < userGroups.size(); i++) {
					for (UserGroup userGroup : existingEmployee.getUserGroups()) {
						if (userGroups.get(i).getUuid().equals(userGroup.getUuid())) {
							userGroups.get(i).setSelected("true");
						}
					}
				}
				existingEmployee.setUserGroups(userGroups);

				model.addAttribute(WebEmployeeConstants.ModelAttribute.UPDATE, existingEmployee);
			} else {
				employeeUpdate.setUuid(null);
				addErrorMessage(model, WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_EMPLOYEE_LIST;
	}

	@PostMapping(value = WebEmployeeConstants.Path.EMPLOYEE, params = "create")
	public RedirectView create(
			@ModelAttribute(WebEmployeeConstants.ModelAttribute.SEARCH) EmployeeSearch employeeSearch,
			@ModelAttribute(WebEmployeeConstants.ModelAttribute.CREATE) Employee employeeCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (employeeCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UserGroup userGroup : employeeCreate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			employeeCreate.setUserGroups(userGroups);

			try {
				employeeCreate = employeeFacade.createDto(employeeCreate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Employee.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Employee.UUID, employeeCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, employeeSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;
	}

	@PostMapping(value = WebEmployeeConstants.Path.EMPLOYEE, params = "update")
	public RedirectView update(
			@ModelAttribute(WebEmployeeConstants.ModelAttribute.SEARCH) EmployeeSearch employeeSearch,
			@ModelAttribute(WebEmployeeConstants.ModelAttribute.UPDATE) Employee employeeUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (employeeUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UserGroup userGroup : employeeUpdate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			employeeUpdate.setUserGroups(userGroups);

			try {
				employeeUpdate = employeeFacade.updateDto(employeeUpdate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Employee.class, e.getMessage(), bindingResult, redirectAttributes);

			}
		}

		redirectAttributes.addAttribute(Employee.UUID, employeeUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, employeeSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;
	}

	@PostMapping(value = WebEmployeeConstants.Path.EMPLOYEE, params = "delete")
	public RedirectView delete(
			@ModelAttribute(WebEmployeeConstants.ModelAttribute.SEARCH) EmployeeSearch employeeSearch,
			@ModelAttribute(WebEmployeeConstants.ModelAttribute.UPDATE) Employee employeeUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			employeeFacade.delete(employeeUpdate.getUuid());
			employeeFacade.deleteEmployeeProfilePictureByUuid(employeeUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Employee.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebEmployeeConstants.ModelAttribute.UPDATE, employeeUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, employeeSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_EMPLOYEE);
		return redirectView;

	}
}

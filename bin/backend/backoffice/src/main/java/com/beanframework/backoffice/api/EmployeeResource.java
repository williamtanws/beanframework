package com.beanframework.backoffice.api;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebEmployeeConstants;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeFacade;

@RestController
public class EmployeeResource {
	@Autowired
	private EmployeeFacade employeeFacade;

	@PreAuthorize(WebEmployeeConstants.PreAuthorize.READ)
	@RequestMapping(WebEmployeeConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) {

		String id = requestParams.get(WebBackofficeConstants.Param.ID).toString();
		Employee employee = employeeFacade.findById(id);

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		if (StringUtils.isNotEmpty(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (employee != null && employee.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return employee != null ? "false" : "true";
	}
}
package com.beanframework.backoffice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmployeeWebConstants;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeFacade;

@RestController
public class EmployeeResource {
	@Autowired
	private EmployeeFacade employeeFacade;

	@RequestMapping(EmployeeWebConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Employee.ID, id);

		Employee employee = employeeFacade.findOneDtoByProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (employee != null && employee.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return employee != null ? "false" : "true";
	}
}
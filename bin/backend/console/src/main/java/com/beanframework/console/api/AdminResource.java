package com.beanframework.console.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebAdminConstants;
import com.beanframework.console.WebConsoleConstants;

@RestController
public class AdminResource {

	@Autowired
	private ModelService modelService;

	@GetMapping(WebAdminConstants.Path.Api.CHECKID)
	public String checkIdExists(Model model, @RequestParam Map<String, Object> requestParams) {

		String id = requestParams.get(WebConsoleConstants.Param.ID).toString();
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Admin.ID, id);
		Admin admin = modelService.findOneEntityByProperties(properties, Admin.class);
		
		String uuidStr = (String) requestParams.get(WebConsoleConstants.Param.UUID);
		if (StringUtils.isNotEmpty(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (admin != null && admin.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return admin != null ? "false" : "true";
	}
}
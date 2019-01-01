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

import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.console.WebConfigurationConstants;
import com.beanframework.console.WebConsoleConstants;

@RestController
public class ConfigurationResource {
	
	@Autowired
	private ModelService modelService;

	@GetMapping(WebConfigurationConstants.Path.Api.CHECKID)
	public String checkIdExists(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(WebConsoleConstants.Param.ID).toString();
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, id);
		
		Configuration configuration = modelService.findOneEntityByProperties(properties, Configuration.class);

		String uuidStr = (String) requestParams.get(WebConsoleConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (configuration != null && configuration.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return configuration != null ? "false" : "true";
	}
}
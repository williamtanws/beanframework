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
import com.beanframework.backoffice.EmailWebConstants;
import com.beanframework.email.domain.Email;
import com.beanframework.email.service.EmailFacade;

@RestController
public class EmailResource {
	@Autowired
	private EmailFacade emailFacade;

	@RequestMapping(EmailWebConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Email.ID, id);

		Email email = emailFacade.findOneDtoByProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (email != null && email.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return email != null ? "false" : "true";
	}
}
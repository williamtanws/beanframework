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

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebUserPermissionConstants;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionFacade;

@RestController
public class UserPermissionResource {

	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@RequestMapping(WebUserPermissionConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(WebBackofficeConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(UserPermission.ID, id);

		UserPermission userPermission = userPermissionFacade.findOneDtoByProperties(properties);

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (userPermission != null && userPermission.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return userPermission != null ? "false" : "true";
	}
}
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
import com.beanframework.backoffice.WebUserPermissionConstants;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.service.UserPermissionFacade;

@RestController
public class UserPermissionResource {
	@Autowired
	private UserPermissionFacade userPermissionFacade;

	@PreAuthorize(WebUserPermissionConstants.PreAuthorize.READ)
	@RequestMapping(WebUserPermissionConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) {

		String id = requestParams.get(WebBackofficeConstants.Param.ID).toString();
		UserPermission userPermission = userPermissionFacade.findById(id);

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		if (StringUtils.isNotEmpty(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (userPermission != null && userPermission.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return userPermission != null ? "false" : "true";
	}
}
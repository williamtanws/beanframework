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
import com.beanframework.backoffice.WebUserRightConstants;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.service.UserRightFacade;

@RestController
public class UserRightResource {
	@Autowired
	private UserRightFacade userRightFacade;

	@PreAuthorize(WebUserRightConstants.PreAuthorize.READ)
	@RequestMapping(WebUserRightConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) {

		String id = requestParams.get(WebBackofficeConstants.Param.ID).toString();
		UserRight userRight = userRightFacade.findById(id);

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		if (StringUtils.isNotEmpty(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (userRight != null && userRight.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return userRight != null ? "false" : "true";
	}
}
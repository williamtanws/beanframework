package com.beanframework.backoffice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.MyAccountWebConstants;
import com.beanframework.backoffice.MyAccountWebConstants.MyAccountPreAuthorizeEnum;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.facade.UserFacade;
import com.beanframework.user.domain.User;

@RestController
public class MyAccountResource extends AbstractResource {
	
	@Autowired
	private UserFacade userFacade;

	@PreAuthorize(MyAccountPreAuthorizeEnum.HAS_READ)
	@RequestMapping(MyAccountWebConstants.Path.Api.MYACCOUNT_CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(User.ID, id);

		UserDto data = userFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}
}
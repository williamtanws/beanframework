package com.beanframework.backoffice.api;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.UserSessionWebConstants;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.facade.UserFacade;
import com.beanframework.user.data.UserSession;

@RestController
public class UserSessionResource extends AbstractResource {

	@Autowired
	private UserFacade userFacade;

	@RequestMapping(value = UserSessionWebConstants.Path.Api.USERSESSION_COUNT, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public int countUserSession(HttpServletRequest request) throws Exception {

		Set<UserSession> userSession = userFacade.findAllSessions();
		if(userSession != null && userSession.size() > 0) {
			return userSession.size();
		}
		else {
			return 0;
		}
	}
}
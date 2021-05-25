package com.beanframework.backoffice.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.common.utils.RequestUtils;
import com.beanframework.user.UserConstants;

@Controller
public class BackofficeController {

  @Value(BackofficeWebConstants.View.LOGIN)
  private String VIEW_BACKOFFICE_LOGIN;

  @Value(BackofficeWebConstants.View.BACKOFFICE)
  private String VIEW_BACKOFFICE;

  @Value(BackofficeWebConstants.View.DASHBOARD)
  private String VIEW_BACKOFFICE_DASHBOARD;

  @GetMapping(BackofficeWebConstants.Path.LOGIN)
  public String login(Model model, HttpServletRequest request) {

    Cookie cookie =
        RequestUtils.getCookie(request.getCookies(), UserConstants.UserSettings.COOKIE_LOGIN_THEME);
    if (cookie != null) {
      model.addAttribute("loginTheme", cookie.getValue());
    }

    return VIEW_BACKOFFICE_LOGIN;
  }


  @GetMapping(BackofficeWebConstants.Path.BACKOFFICE)
  public String backoffice() {
    return VIEW_BACKOFFICE;
  }


  @GetMapping(BackofficeWebConstants.Path.DASHBOARD)
  public String dashboard() {
    return VIEW_BACKOFFICE_DASHBOARD;
  }
}

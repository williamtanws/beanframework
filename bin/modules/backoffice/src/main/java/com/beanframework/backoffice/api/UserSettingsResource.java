package com.beanframework.backoffice.api;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.beanframework.backoffice.UserWebConstants;
import com.beanframework.backoffice.web.BackofficeController;
import com.beanframework.common.utils.RequestUtils;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.facade.UserFacade;
import com.beanframework.user.service.UserService;

@RestController
public class UserSettingsResource extends AbstractResource {

  @Autowired
  private UserService userService;

  @Autowired
  private UserFacade userFacade;

  @RequestMapping(value = UserWebConstants.Path.Api.USER_SETTINGS)
  @ResponseBody
  public ResponseEntity<?> setusersettings(HttpServletRequest request, HttpServletResponse response,
      Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

    String user_settings_header_theme = (String) requestParams.get("userSettingsHeaderTheme");
    String user_settings_sidebar_theme = (String) requestParams.get("userSettingsSidebarTheme");
    String user_settings_sidebar_navflatstyle =
        (String) requestParams.get("userSettingsSidebarNavflatstyle");
    String user_settings_sidebar_navlegacystyle =
        (String) requestParams.get("userSettingsSidebarNavlegacystyle");
    String user_settings_sidebar_navcompact =
        (String) requestParams.get("userSettingsSidebarNavcompact");
    String user_settings_sidebar_navchildindent =
        (String) requestParams.get("userSettingsSidebarNavchildindent");
    String user_settings_body_theme = (String) requestParams.get("userSettingsBodyTheme");
    String user_settings_body_smalltext = (String) requestParams.get("userSettingsBodySmalltext");

    UserDto user = userFacade.getCurrentUser();

    if (user_settings_header_theme != null) {
      user.getParameters().put("user.settings.header.theme", user_settings_header_theme);
    }
    if (user_settings_sidebar_theme != null) {
      user.getParameters().put("user.settings.sidebar.theme", user_settings_sidebar_theme);
    }
    if (user_settings_sidebar_navflatstyle != null) {
      user.getParameters().put("user.settings.sidebar.navflatstyle",
          user_settings_sidebar_navflatstyle);
    }
    if (user_settings_sidebar_navlegacystyle != null) {
      user.getParameters().put("user.settings.sidebar.navlegacystyle",
          user_settings_sidebar_navlegacystyle);
    }
    if (user_settings_sidebar_navcompact != null) {
      user.getParameters().put("user.settings.sidebar.navcompact",
          user_settings_sidebar_navcompact);
    }
    if (user_settings_sidebar_navchildindent != null) {
      user.getParameters().put("user.settings.sidebar.navchildindent",
          user_settings_sidebar_navchildindent);
    }
    if (user_settings_body_theme != null) {
      user.getParameters().put("user.settings.body.theme", user_settings_body_theme);

      RequestUtils.addCookie(request.getContextPath(), response,
          BackofficeController.COOKIE_LOGIN_THEME, user_settings_body_theme, null,
          request.getServerName());
    }
    if (user_settings_body_smalltext != null) {
      user.getParameters().put("user.settings.body.smalltext", user_settings_body_smalltext);
    }

    userFacade.update(user);
    userService.updateCurrentUserSession();

    return new ResponseEntity<>(HttpStatus.OK);
  }
}

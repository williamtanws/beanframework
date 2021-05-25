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
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.RequestUtils;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.user.UserConstants;
import com.beanframework.user.domain.User;
import com.beanframework.user.service.UserService;

@RestController
public class UserSettingsResource extends AbstractResource {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelService modelService;

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

    User user = userService.getCurrentUser();

    if (user_settings_header_theme != null) {
      user.getParameters().put(UserConstants.UserSettings.HEADER_THEME, user_settings_header_theme);
    }
    if (user_settings_sidebar_theme != null) {
      user.getParameters().put(UserConstants.UserSettings.SIDEBAR_THEME,
          user_settings_sidebar_theme);
    }
    if (user_settings_sidebar_navflatstyle != null) {
      user.getParameters().put(UserConstants.UserSettings.SIDEBAR_NAVFLATSTYLE,
          user_settings_sidebar_navflatstyle);
    }
    if (user_settings_sidebar_navlegacystyle != null) {
      user.getParameters().put(UserConstants.UserSettings.SIDEBAR_NAVLEGACYSTYLE,
          user_settings_sidebar_navlegacystyle);
    }
    if (user_settings_sidebar_navcompact != null) {
      user.getParameters().put(UserConstants.UserSettings.SIDEBAR_NAVCOMPACT,
          user_settings_sidebar_navcompact);
    }
    if (user_settings_sidebar_navchildindent != null) {
      user.getParameters().put(UserConstants.UserSettings.SIDEBAR_NAVCHILDINDENT,
          user_settings_sidebar_navchildindent);
    }
    if (user_settings_body_theme != null) {
      user.getParameters().put(UserConstants.UserSettings.BODY_THEME, user_settings_body_theme);

      RequestUtils.addCookie(request.getContextPath(), response,
          UserConstants.UserSettings.COOKIE_LOGIN_THEME, user_settings_body_theme, null,
          request.getServerName());
    }
    if (user_settings_body_smalltext != null) {
      user.getParameters().put(UserConstants.UserSettings.BODY_SMALLTEXT,
          user_settings_body_smalltext);
    }

    modelService.saveEntityByLegacyMode(user);
    userService.updateCurrentUserSession();

    return new ResponseEntity<>(HttpStatus.OK);
  }
}

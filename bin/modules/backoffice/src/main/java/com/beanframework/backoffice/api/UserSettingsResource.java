package com.beanframework.backoffice.api;

import java.util.Map;
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
import com.beanframework.core.api.AbstractResource;
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
  public ResponseEntity<?> setusersettings(Model model,
      @RequestParam Map<String, Object> requestParams) throws Exception {

    String user_settings_header_theme = (String) requestParams.get("userSettingsHeaderTheme");
    String user_settings_sidebar_theme = (String) requestParams.get("userSettingsSidebarTheme");
    String user_settings_body_theme = (String) requestParams.get("userSettingsBodyTheme");

    User user = userService.getCurrentUser();

    if (user_settings_header_theme != null) {
      user.getParameters().put("user.settings.header.theme", user_settings_header_theme);
    }
    if (user_settings_sidebar_theme != null) {
      user.getParameters().put("user.settings.sidebar.theme", user_settings_sidebar_theme);
    }
    if (user_settings_body_theme != null) {
      user.getParameters().put("user.settings.body.theme", user_settings_body_theme);
    }

    modelService.saveEntity(user);
    userService.updatePrincipal(user);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}

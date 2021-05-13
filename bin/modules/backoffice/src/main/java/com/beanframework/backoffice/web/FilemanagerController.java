package com.beanframework.backoffice.web;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.beanframework.backoffice.FilemanagerWebConstants;
import com.beanframework.backoffice.FilemanagerWebConstants.FilemanagerPreAuthorizeEnum;


@Controller
public class FilemanagerController {

  @Value(FilemanagerWebConstants.View.TEMPLATES)
  private String VIEWE_TEMPLATES;

  @Value(FilemanagerWebConstants.View.ANGULARFILEMANAGER)
  private String VIEW_ANGULARFILEMANAGER;

  @PreAuthorize(FilemanagerPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = FilemanagerWebConstants.Path.FILE_MANAGER,
      method = {RequestMethod.GET, RequestMethod.POST})
  public String filemanager(Model model, @RequestParam Map<String, Object> allRequestParams,
      RedirectAttributes redirectAttributes, HttpServletRequest request) {

    model.addAttribute("Windows", System.getProperty("os.name").startsWith("Windows"));
    return VIEW_ANGULARFILEMANAGER;
  }

  @PreAuthorize(FilemanagerPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = FilemanagerWebConstants.Path.TEMPLATES,
      method = {RequestMethod.GET, RequestMethod.POST})
  public String template(@PathVariable("page") String page, Model model,
      @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes,
      HttpServletRequest request) {
    return VIEWE_TEMPLATES + "/" + page;
  }
}

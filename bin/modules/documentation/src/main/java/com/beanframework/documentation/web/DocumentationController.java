package com.beanframework.documentation.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.beanframework.common.utils.RequestUtils;
import com.beanframework.core.controller.GlobalErrorController;
import com.beanframework.documentation.DocumentationConstants;
import com.beanframework.documentation.DocumentationConstants.DocumentationPreAuthorizeEnum;

@Controller
public class DocumentationController {

  @Value(DocumentationConstants.View.DOCUMENTATION)
  private String VIEW_DOCUMENTATION;

  @PreAuthorize(DocumentationPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = DocumentationConstants.Path.DOCUMENTATION)
  public String documentation(HttpServletRequest request, HttpServletResponse response,
      Model model) {

    RequestUtils.addCookie(request.getContextPath(), response,
        GlobalErrorController.COOKIE_DOCUMENTATION_REFERER, request.getHeader("Referer"), null,
        request.getServerName());
    return VIEW_DOCUMENTATION;
  }
}

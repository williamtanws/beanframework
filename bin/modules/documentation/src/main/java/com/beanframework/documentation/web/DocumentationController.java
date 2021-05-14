package com.beanframework.documentation.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    // create a cookie
    Cookie cookie = new Cookie("referer", request.getHeader("Referer"));

    // add cookie to response
    response.addCookie(cookie);

    return VIEW_DOCUMENTATION;
  }
}

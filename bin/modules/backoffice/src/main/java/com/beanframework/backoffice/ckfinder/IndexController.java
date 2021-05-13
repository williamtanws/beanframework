package com.beanframework.backoffice.ckfinder;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
  @RequestMapping("/ckfinder.html")
  public void index(HttpServletResponse response) {
    // Redirect to CKFinder's samples.
    response.setHeader("Location", "/ckfinder/static/samples/index.html");
    response.setStatus(302);
  }
}

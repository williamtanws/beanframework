package com.beanframework.core.controller;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.beanframework.common.utils.RequestUtils;
import io.micrometer.core.instrument.util.StringUtils;

@Controller
@RequestMapping(value = {"/error", "/*/error"})
public class GlobalErrorController implements ErrorController {

  @Value("#{'${webroots}'.split(',')}")
  private List<String> WEBROOTS;

  @Autowired
  private Environment environment;

  public static final String COOKIE_DOCUMENTATION_REFERER = "doc_referer";

  @RequestMapping
  public String handleError(Model model, HttpServletRequest request) {
    String originalUri = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
    String message = (String) request.getAttribute("javax.servlet.error.message");

    Cookie cookie = RequestUtils.getCookie(request.getCookies(), COOKIE_DOCUMENTATION_REFERER);
    if (cookie != null) {
      if (StringUtils.isNotBlank(cookie.getValue())) {
        String[] value = cookie.getValue().split("/");
        originalUri = "/" + value[value.length - 1];
        model.addAttribute("fullpage", true);
        model.addAttribute("homeurl", cookie.getValue());
      }
    }

    if (originalUri == null) {
      originalUri = request.getRequestURI();
    }

    String webroot = null;
    if (originalUri.split("/").length > 1) {
      webroot = originalUri.split("/")[1];

      if (WEBROOTS.contains(webroot)) {

        if (statusCode == 405) {
          return "redirect:/" + webroot;
        }

        model.addAttribute("redirectUrl", "/" + webroot);
      }
    }

    if (statusCode != null)
      model.addAttribute("statusCode", statusCode);

    if (exception != null)
      model.addAttribute("exception", exception);

    if (message != null)
      model.addAttribute("message", message);

    String webrootTheme = environment.getProperty(webroot + ".theme");

    if (StringUtils.isBlank(webrootTheme)) {
      return "/error";
    } else {
      return webrootTheme + "/error";
    }
  }

  @RequestMapping(value = "notfound", method = RequestMethod.GET)
  public ResponseEntity<Object> testNotfound() {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(value = "internalservererror", method = RequestMethod.GET)
  public ResponseEntity<Object> testError() {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(value = "throwable", method = RequestMethod.GET)
  public String testThrowable() throws Throwable {
    throw new Throwable("Throwable");
  }

  @RequestMapping(value = "exception", method = RequestMethod.GET)
  public String testException() throws Exception {
    throw new Exception("Exception");
  }

  @RequestMapping(value = "badgateway", method = RequestMethod.GET)
  public ResponseEntity<Object> testBadGateway() {
    return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
  }

  @Override
  @Deprecated
  public String getErrorPath() {
    return null;
  }
}

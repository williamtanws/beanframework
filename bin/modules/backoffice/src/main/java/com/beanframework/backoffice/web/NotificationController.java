package com.beanframework.backoffice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.beanframework.backoffice.NotificationWebConstants;
import com.beanframework.core.controller.AbstractController;

@Controller
public class NotificationController extends AbstractController {

  @Value(NotificationWebConstants.View.NOTIFICATION)
  private String VIEW_NOTIFICATION;

  @GetMapping(value = NotificationWebConstants.Path.NOTIFICATION)
  public String page(Model model) {
    return VIEW_NOTIFICATION;
  }
}

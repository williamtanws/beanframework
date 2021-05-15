package com.beanframework.console.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.beanframework.console.OverviewWebConstants;
import com.beanframework.console.OverviewWebConstants.OverviewPreAuthorizeEnum;

@Controller
public class OverviewController {

  @Value("${spring.pid.file}")
  private String PID_FILE;

  @Value(OverviewWebConstants.View.APPLICATION_OVERVIEW)
  private String VIEW_CONSOLE_APPLICATION_OVERVIEW;

  @PreAuthorize(OverviewPreAuthorizeEnum.HAS_READ)
  @RequestMapping(OverviewWebConstants.Path.APPLICATION_OVERVIEW)
  public String overview(Model model) throws IOException {

    File pidFile = new File(PID_FILE);

    String pid = new String(Files.readAllBytes(Paths.get(pidFile.getAbsolutePath())));
    model.addAttribute("pid", pid);

    return VIEW_CONSOLE_APPLICATION_OVERVIEW;
  }
}

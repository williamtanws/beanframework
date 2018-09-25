package com.beanframework.console.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beanframework.console.WebConsoleConstants;

@Controller
public class DashboardController {

	@Value(WebConsoleConstants.View.LOGIN)
	private String VIEW_CONSOLE_LOGIN;

	@Value(WebConsoleConstants.View.DASHBOARD)
	private String VIEW_CONSOLE_DASHBOARD;

	@Value("${spring.pid.file}")
	private String PID_FILE;

	@RequestMapping(WebConsoleConstants.Path.DASHBOARD)
	public String dashboard(Model model) throws IOException {
		
		File pidFile = new File(PID_FILE);
		
		String pid = new String(Files.readAllBytes(Paths.get(pidFile.getAbsolutePath())));
		model.addAttribute("pid", pid);
		
		return VIEW_CONSOLE_DASHBOARD;
	}
}

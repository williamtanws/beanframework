package com.beanframework.console.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beanframework.console.WebApplicationConstants;

@Controller
public class DashboardController {

	@Value("${spring.pid.file}")
	private String PID_FILE;
	
	@Value(WebApplicationConstants.View.APPLICATION_OVERVIEW)
	private String VIEW_CONSOLE_APPLICATION_OVERVIEW;
	
	@Value(WebApplicationConstants.View.APPLICATION_METRICS)
	private String VIEW_CONSOLE_APPLICATION_METRICS;
	
	@Value(WebApplicationConstants.View.APPLICATION_ENVIRONMENT)
	private String VIEW_CONSOLE_APPLICATION_ENVIRONMENT;
	
	@Value(WebApplicationConstants.View.APPLICATION_LOGFILE)
	private String VIEW_CONSOLE_APPLICATION_LOGFILE;
	
	@Value(WebApplicationConstants.View.APPLICATION_LOGGERS)
	private String VIEW_CONSOLE_APPLICATION_LOGGERS;
	
	@Value(WebApplicationConstants.View.APPLICATION_THREADS)
	private String VIEW_CONSOLE_APPLICATION_THREADS;
	
	@Value(WebApplicationConstants.View.APPLICATION_HTTPTRACES)
	private String VIEW_CONSOLE_APPLICATION_HTTPTRACES;
	
	@Value(WebApplicationConstants.View.APPLICATION_AUDITLOG)
	private String VIEW_CONSOLE_APPLICATION_AUDITLOG;
	
	@Value(WebApplicationConstants.View.APPLICATION_HEAPDUMP)
	private String VIEW_CONSOLE_APPLICATION_HEAPDUMP;

	@RequestMapping(WebApplicationConstants.Path.APPLICATION_OVERVIEW)
	public String overview(Model model) throws IOException {
		
		File pidFile = new File(PID_FILE);
		
		String pid = new String(Files.readAllBytes(Paths.get(pidFile.getAbsolutePath())));
		model.addAttribute("pid", pid);
		
		return VIEW_CONSOLE_APPLICATION_OVERVIEW;
	}
	
	@RequestMapping(WebApplicationConstants.Path.APPLICATION_METRICS)
	public String metrics(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_METRICS;
	}
	
	@RequestMapping(WebApplicationConstants.Path.APPLICATION_ENVIRONMENT)
	public String environment(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_ENVIRONMENT;
	}
	
	@RequestMapping(WebApplicationConstants.Path.APPLICATION_LOGFILE)
	public String logfile(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_LOGFILE;
	}
	
	@RequestMapping(WebApplicationConstants.Path.APPLICATION_LOGGERS)
	public String loggers(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_LOGGERS;
	}
	
	@RequestMapping(WebApplicationConstants.Path.APPLICATION_THREADS)
	public String threads(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_THREADS;
	}
	
	@RequestMapping(WebApplicationConstants.Path.APPLICATION_HTTPTRACES)
	public String httptraces(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_HTTPTRACES;
	}
	
	@RequestMapping(WebApplicationConstants.Path.APPLICATION_AUDITLOG)
	public String auditlog(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_AUDITLOG;
	}
	
	@RequestMapping(WebApplicationConstants.Path.APPLICATION_HEAPDUMP)
	public String heapdump(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_HEAPDUMP;
	}
}

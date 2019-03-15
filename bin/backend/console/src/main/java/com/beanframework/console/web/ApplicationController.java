package com.beanframework.console.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beanframework.console.ApplicationWebConstants;

@Controller
public class ApplicationController {

	@Value("${spring.pid.file}")
	private String PID_FILE;

	@Value(ApplicationWebConstants.View.APPLICATION_OVERVIEW)
	private String VIEW_CONSOLE_APPLICATION_OVERVIEW;

	@Value(ApplicationWebConstants.View.APPLICATION_METRICS)
	private String VIEW_CONSOLE_APPLICATION_METRICS;

	@Value(ApplicationWebConstants.View.APPLICATION_ENVIRONMENT)
	private String VIEW_CONSOLE_APPLICATION_ENVIRONMENT;

	@Value(ApplicationWebConstants.View.APPLICATION_LOGFILE)
	private String VIEW_CONSOLE_APPLICATION_LOGFILE;

	@Value(ApplicationWebConstants.View.APPLICATION_LOGGERS)
	private String VIEW_CONSOLE_APPLICATION_LOGGERS;

	@Value(ApplicationWebConstants.View.APPLICATION_THREADS)
	private String VIEW_CONSOLE_APPLICATION_THREADS;

	@Value(ApplicationWebConstants.View.APPLICATION_HTTPTRACES)
	private String VIEW_CONSOLE_APPLICATION_HTTPTRACES;

	@Value(ApplicationWebConstants.View.APPLICATION_AUDITLOG)
	private String VIEW_CONSOLE_APPLICATION_AUDITLOG;

	@Value(ApplicationWebConstants.View.APPLICATION_HEAPDUMP)
	private String VIEW_CONSOLE_APPLICATION_HEAPDUMP;

	@Autowired
	private LoggersEndpoint loggersEndpoint;

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_OVERVIEW)
	public String overview(Model model) throws IOException {

		File pidFile = new File(PID_FILE);

		String pid = new String(Files.readAllBytes(Paths.get(pidFile.getAbsolutePath())));
		model.addAttribute("pid", pid);

		return VIEW_CONSOLE_APPLICATION_OVERVIEW;
	}

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_METRICS)
	public String metrics(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_METRICS;
	}

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_ENVIRONMENT)
	public String environment(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_ENVIRONMENT;
	}

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_LOGFILE)
	public String logfile(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_LOGFILE;
	}

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_LOGGERS)
	public String loggers(Model model) throws IOException {

		model.addAttribute("loggers", loggersEndpoint.loggers());

		return VIEW_CONSOLE_APPLICATION_LOGGERS;
	}

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_THREADS)
	public String threads(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_THREADS;
	}

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_HTTPTRACES)
	public String httptraces(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_HTTPTRACES;
	}

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_AUDITLOG)
	public String auditlog(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_AUDITLOG;
	}

	@RequestMapping(ApplicationWebConstants.Path.APPLICATION_HEAPDUMP)
	public String heapdump(Model model) throws IOException {
		return VIEW_CONSOLE_APPLICATION_HEAPDUMP;
	}
}

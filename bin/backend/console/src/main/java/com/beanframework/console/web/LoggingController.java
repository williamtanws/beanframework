package com.beanframework.console.web;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.console.LoggingWebConstants;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

@Controller
public class LoggingController {

	@Value(LoggingWebConstants.Location.LOGGING)
	private String LOG_DIR;

	@Value(LoggingWebConstants.View.LOGGING_LEVEL)
	private String VIEW_LOGGING_LEVEL;
	
	@Value(LoggingWebConstants.View.LOGGING_TAIL)
	private String VIEW_LOGGING_TAIL;
	
	@Value(LoggingWebConstants.Path.LOGGING_TAIL)
	private String Path_LOGGING_TAIL;

	@RequestMapping(value = LoggingWebConstants.Path.LOGGING, method = { RequestMethod.GET, RequestMethod.POST })
	public String logging(Model model, @RequestParam Map<String, Object> allRequestParams,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return "redirect:" + Path_LOGGING_TAIL + "?level=all";
	}

	@RequestMapping(value = LoggingWebConstants.Path.LOGGING_TAIL, method = { RequestMethod.GET, RequestMethod.POST })
	public String tail(Model model, @RequestParam Map<String, Object> allRequestParams,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_LOGGING_TAIL;
	}

	@RequestMapping(value = LoggingWebConstants.Path.LOGGING_DOWNLOAD, method = { RequestMethod.GET,
			RequestMethod.POST })
	public ResponseEntity<byte[]> download(Model model, @RequestParam Map<String, Object> allRequestParams,
			RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {

		String level = request.getParameter("level");

		HttpHeaders headers = new HttpHeaders();

		try {
			String fileName = level + ".log";
			headers.setContentDispositionFormData(fileName, fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			File file = new File(LOG_DIR + File.separator + fileName);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = LoggingWebConstants.Path.LOGGING_LEVEL, method = { RequestMethod.GET, RequestMethod.POST })
	public String level(Model model, @RequestParam Map<String, Object> allRequestParams,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		List<Logger> loggers = loggerContext.getLoggerList();

		model.addAttribute("loggers", loggers);

		return VIEW_LOGGING_LEVEL;
	}
}

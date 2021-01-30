package com.beanframework.core.controller;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorController implements ErrorController {

	@Value("#{'${webroots}'.split(',')}")
	private List<String> WEBROOTS;

	@RequestMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {
		String originalUri = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		String message = (String) request.getAttribute("javax.servlet.error.message");

		if (originalUri == null) {
			originalUri = request.getRequestURI();
		}

		if (originalUri.split("/").length > 1) {
			String webroot = originalUri.split("/")[1];

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

		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}

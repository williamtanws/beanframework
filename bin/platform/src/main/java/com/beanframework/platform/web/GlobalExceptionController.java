package com.beanframework.platform.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public String handleError405(HttpServletRequest request, Exception e) {
		ModelAndView mav = new ModelAndView("/405");
		mav.addObject("exception", e);
		// mav.addObject("errorcode", "405");
		return "redirect:" + request.getContextPath();
	}
}
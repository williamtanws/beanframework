//package com.beanframework.platform.web;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.ui.Model;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//@ControllerAdvice
//public class GlobalExceptionController {
//
//	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//	public String handleError405(HttpServletRequest request, Exception e) {
//		ModelAndView mav = new ModelAndView("/405");
//		mav.addObject("exception", e);
//		// mav.addObject("errorcode", "405");
//		return "redirect:" + request.getContextPath();
//	}
//	
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public String handleError404(Model model, HttpServletRequest request, Exception e) {
//		model.addAttribute("error", e.getMessage());
//		return "error";
//	}
//}
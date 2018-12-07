package com.beanframework.backoffice.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.backoffice.WebFilemanagerConstants;

@Controller
public class FilemanagerController {

	@Value(WebFilemanagerConstants.View.CONTAINER)
	private String VIEW_CONTAINER;
	
	@Value(WebFilemanagerConstants.View.TEMPLATES)
	private String VIEWE_TEMPLATES;
	
	@Value(WebFilemanagerConstants.View.ANGULARFILEMANAGER)
	private String VIEW_ANGULARFILEMANAGER;

	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.READ)
	@RequestMapping(value = WebFilemanagerConstants.Path.FILE_MANAGER, method = { RequestMethod.GET, RequestMethod.POST })
	public String filemanager(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_CONTAINER;
	}
	
	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.READ)
	@RequestMapping(value = WebFilemanagerConstants.Path.ANGULARFILEMANAGER, method = { RequestMethod.GET, RequestMethod.POST })
	public String angularfilemanager(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_ANGULARFILEMANAGER;
	}

	@PreAuthorize(WebFilemanagerConstants.PreAuthorize.READ)
	@RequestMapping(value = WebFilemanagerConstants.Path.TEMPLATES_PAGE, method = { RequestMethod.GET, RequestMethod.POST })
	public String template(@PathVariable("page") String page, Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEWE_TEMPLATES+"/"+page;
	}
}

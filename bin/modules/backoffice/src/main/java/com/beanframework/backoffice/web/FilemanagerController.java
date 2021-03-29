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

import com.beanframework.backoffice.FilemanagerWebConstants;

@PreAuthorize("isAuthenticated()")
@Controller
public class FilemanagerController {

	public static interface FilemanagerPreAuthorizeEnum {
		public static final String AUTHORITY_READ = "filemanager_read";
		public static final String AUTHORITY_CREATE = "filemanager_create";
		public static final String AUTHORITY_UPDATE = "filemanager_update";
		public static final String AUTHORITY_DELETE = "hfilemanager_delete";

		public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
		public static final String HAS_CREATE = "hasAuthority('" + AUTHORITY_CREATE + "')";
		public static final String HAS_UPDATE = "hasAuthority('" + AUTHORITY_UPDATE + "')";
		public static final String HAS_DELETE = "hasAuthority('" + AUTHORITY_DELETE + "')";
	}

	@Value(FilemanagerWebConstants.View.CONTAINER)
	private String VIEW_CONTAINER;

	@Value(FilemanagerWebConstants.View.TEMPLATES)
	private String VIEWE_TEMPLATES;

	@Value(FilemanagerWebConstants.View.ANGULARFILEMANAGER)
	private String VIEW_ANGULARFILEMANAGER;

	@PreAuthorize(FilemanagerPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = FilemanagerWebConstants.Path.FILE_MANAGER, method = { RequestMethod.GET, RequestMethod.POST })
	public String filemanager(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_CONTAINER;
	}

	@PreAuthorize(FilemanagerPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = FilemanagerWebConstants.Path.ANGULARFILEMANAGER, method = { RequestMethod.GET, RequestMethod.POST })
	public String angularfilemanager(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_ANGULARFILEMANAGER;
	}

	@PreAuthorize(FilemanagerPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = FilemanagerWebConstants.Path.TEMPLATES, method = { RequestMethod.GET, RequestMethod.POST })
	public String template(@PathVariable("page") String page, Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEWE_TEMPLATES + "/" + page;
	}
}

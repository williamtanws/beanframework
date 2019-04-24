package com.beanframework.console.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.common.controller.AbstractController;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.PlatformImportWebConstants;
import com.beanframework.console.service.PlatformService;

@Controller
public class PlatformImportController extends AbstractController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PlatformUpdateController.class);

	@Value(PlatformImportWebConstants.Path.IMPORT)
	private String PATH_IMPORT;

	@Value(PlatformImportWebConstants.View.IMPORT)
	private String VIEW_IMPORT;

	@Autowired
	private PlatformService platformService;

	@GetMapping(value = PlatformImportWebConstants.Path.IMPORT)
	public String importView(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_IMPORT;
	}

	@PostMapping(value = PlatformImportWebConstants.Path.IMPORT)
	public RedirectView importUpload(@RequestParam("files") MultipartFile[] files, Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		String[] messages = platformService.importFiles(files);

		if (messages[0].length() != 0) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.SUCCESS, messages[0]);
		}
		if (messages[1].length() != 0) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, messages[1]);
		}

		addSuccessMessage(redirectAttributes, "Successfully Imported");

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_IMPORT);
		return redirectView;
	}

}

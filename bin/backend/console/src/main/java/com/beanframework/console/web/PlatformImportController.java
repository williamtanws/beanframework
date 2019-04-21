package com.beanframework.console.web;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
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
import com.beanframework.console.registry.ImportListener;
import com.beanframework.console.registry.ImportListenerRegistry;

@Controller
public class PlatformImportController extends AbstractController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PlatformUpdateController.class);

	@Value(PlatformImportWebConstants.Path.IMPORT)
	private String PATH_IMPORT;

	@Value(PlatformImportWebConstants.View.IMPORT)
	private String VIEW_IMPORT;

	@Autowired
	private ImportListenerRegistry importerRegistry;

	@GetMapping(value = PlatformImportWebConstants.Path.IMPORT)
	public String importView(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_IMPORT;
	}

	@PostMapping(value = PlatformImportWebConstants.Path.IMPORT)
	public RedirectView importUpload(@RequestParam("files") MultipartFile[] files, Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {

		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		Set<Entry<String, ImportListener>> importListeners = importerRegistry.getListeners().entrySet();
		List<Entry<String, ImportListener>> sortedImportListeners = new LinkedList<Entry<String, ImportListener>>(importListeners);
		Collections.sort(sortedImportListeners, new Comparator<Entry<String, ImportListener>>() {
			@Override
			public int compare(Entry<String, ImportListener> ele1, Entry<String, ImportListener> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		for (Entry<String, ImportListener> entry : sortedImportListeners) {

			for (MultipartFile multipartFile : files) {
				if (multipartFile.isEmpty() == false) {
					try {
						String content = IOUtils.toString(multipartFile.getInputStream(), Charset.defaultCharset());

						String fileName = multipartFile.getOriginalFilename();

						if (fileName.endsWith("_update") && entry.getKey().startsWith(fileName.toLowerCase())) {
							entry.getValue().updateByContent(content);
							successMessages.append(entry.getValue().getName() + " is updated successfully. <br>");
						}
						else if (fileName.endsWith("_remove") && entry.getKey().startsWith(fileName.toLowerCase())) {
							entry.getValue().removeByContent(content);
							successMessages.append(entry.getValue().getName() + " is updated successfully. <br>");
						}
						else {
							errorMessages.append(entry.getValue().getName() + " is updated failed. Reason: File Name not valid. <br>");
						}
						
					} catch (Exception e) {
						errorMessages.append(entry.getValue().getName() + " is updated failed. Reason: " + e.getMessage() + " <br>");
					}
				}
			}
		}

		if (successMessages.length() != 0) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.SUCCESS, successMessages.toString());
		}
		if (errorMessages.length() != 0) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, errorMessages.toString());
		}

		addSuccessMessage(redirectAttributes, "Successfully Imported");

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_IMPORT);
		return redirectView;
	}

}

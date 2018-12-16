package com.beanframework.console.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.common.Updater;
import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.registry.UpdaterRegistry;

@Controller
public class UpdateController {
	
	Logger logger = LoggerFactory.getLogger(UpdateController.class);

	@Value(WebPlatformConstants.Path.UPDATE)
	private String PATH_UPDATE;

	@Value(WebPlatformConstants.View.UPDATE)
	private String VIEW_UPDATE;

	@Autowired
	private UpdaterRegistry updaterRegistry;

	@GetMapping(value = WebPlatformConstants.Path.UPDATE)
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		Set<Entry<String, Updater>> mapEntries = updaterRegistry.getUpdaters().entrySet();
		List<Entry<String, Updater>> aList = new LinkedList<Entry<String, Updater>>(mapEntries);
		Collections.sort(aList, new Comparator<Entry<String, Updater>>() {
			@Override
			public int compare(Entry<String, Updater> ele1, Entry<String, Updater> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		model.addAttribute("updates", aList);

		return VIEW_UPDATE;
	}

	@PostMapping(value = WebPlatformConstants.Path.UPDATE)
	public RedirectView update(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {

		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		Set<Entry<String, Updater>> mapEntries = updaterRegistry.getUpdaters().entrySet();
		List<Entry<String, Updater>> aList = new LinkedList<Entry<String, Updater>>(mapEntries);
		Collections.sort(aList, new Comparator<Entry<String, Updater>>() {
			@Override
			public int compare(Entry<String, Updater> ele1, Entry<String, Updater> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		for (Entry<String, Updater> entry : aList) {
			if(requestParams.get(entry.getKey()) != null){
				String keyValue = requestParams.get(entry.getKey()).toString();
				if (parseBoolean(keyValue)) {
					try {
						entry.getValue().update();
						successMessages.append(entry.getValue().getName() + " is updated successfully. <br>");
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						errorMessages.append(entry.getValue().getName() + " is updated failed. Reason: " + e.getMessage() + " <br>");
					}
				}
			}
		}

		if(successMessages.length() != 0){
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.SUCCESS, successMessages.toString());
		}
		if(errorMessages.length() != 0){
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR, errorMessages.toString());
		}
		
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_UPDATE);
		return redirectView;
	}

	public boolean parseBoolean(String value) {
		return ((value != null) && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")));
	}

}

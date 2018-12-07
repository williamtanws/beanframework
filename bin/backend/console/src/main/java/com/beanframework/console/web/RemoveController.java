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

import com.beanframework.common.domain.Remover;
import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.registry.RemoverRegistry;

@Controller
public class RemoveController {
	
	Logger logger = LoggerFactory.getLogger(RemoveController.class);
	
	@Value(WebPlatformConstants.Path.REMOVE)
	private String PATH_REMOVE;

	@Value(WebPlatformConstants.View.REMOVE)
	private String VIEW_REMOVE;

	@Autowired
	private RemoverRegistry removerRegistry;

	@GetMapping(value = WebPlatformConstants.Path.REMOVE)
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		Set<Entry<String, Remover>> mapEntries = removerRegistry.getRemovers().entrySet();
		List<Entry<String, Remover>> aList = new LinkedList<Entry<String, Remover>>(mapEntries);
		Collections.sort(aList, new Comparator<Entry<String, Remover>>() {
			@Override
			public int compare(Entry<String, Remover> ele1, Entry<String, Remover> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		model.addAttribute("removes", aList);

		return VIEW_REMOVE;
	}

	@PostMapping(value = WebPlatformConstants.Path.REMOVE)
	public RedirectView remove(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {

		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		Set<Entry<String, Remover>> mapEntries = removerRegistry.getRemovers().entrySet();
		List<Entry<String, Remover>> aList = new LinkedList<Entry<String, Remover>>(mapEntries);
		Collections.sort(aList, new Comparator<Entry<String, Remover>>() {
			@Override
			public int compare(Entry<String, Remover> ele1, Entry<String, Remover> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		for (Entry<String, Remover> entry : aList) {
			if(requestParams.get(entry.getKey()) != null){
				String keyValue = requestParams.get(entry.getKey()).toString();
				if (parseBoolean(keyValue)) {
					try {
						entry.getValue().remove();
						successMessages.append(entry.getValue().getName() + " is removed successfully. \n");
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						errorMessages.append(entry.getValue().getName() + " is removed failed. Reason: " + e.getMessage() + " \n");
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
		redirectView.setUrl(PATH_REMOVE);
		return redirectView;
	}

	public boolean parseBoolean(String value) {
		return ((value != null) && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")));
	}

}

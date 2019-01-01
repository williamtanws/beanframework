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
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.WebPlatformUpdateConstants;
import com.beanframework.console.registry.Importer;
import com.beanframework.console.registry.ImporterRegistry;

import net.sf.ehcache.CacheManager;

@Controller
public class PlatformUpdateController {

	Logger logger = LoggerFactory.getLogger(PlatformUpdateController.class);

	@Value(WebPlatformUpdateConstants.Path.UPDATE)
	private String PATH_UPDATE;

	@Value(WebPlatformUpdateConstants.View.UPDATE)
	private String VIEW_UPDATE;

	@Autowired
	private ImporterRegistry importerRegistry;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private SessionRegistry sessionRegistry;

	@GetMapping(value = WebPlatformUpdateConstants.Path.UPDATE)
	public String list(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		Set<Entry<String, Importer>> mapEntries = importerRegistry.getImporters().entrySet();
		List<Entry<String, Importer>> aList = new LinkedList<Entry<String, Importer>>(mapEntries);
		Collections.sort(aList, new Comparator<Entry<String, Importer>>() {
			@Override
			public int compare(Entry<String, Importer> ele1, Entry<String, Importer> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		model.addAttribute("updates", aList);
		
		if(allRequestParams.get("clearsessions") != null) {
			String clearsessions = (String) allRequestParams.get("clearsessions");
			if(clearsessions.equals("1")) {
				for (Object principal : sessionRegistry.getAllPrincipals()) {
					List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
					for (SessionInformation sessionInformation : sessionInformations) {
						sessionInformation.expireNow();
					}
				}
			}
		}

		return VIEW_UPDATE;
	}

	@PostMapping(value = WebPlatformUpdateConstants.Path.UPDATE)
	public RedirectView update(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {

		StringBuilder successMessages = new StringBuilder();
		StringBuilder errorMessages = new StringBuilder();

		Set<Entry<String, Importer>> mapEntries = importerRegistry.getImporters().entrySet();
		List<Entry<String, Importer>> aList = new LinkedList<Entry<String, Importer>>(mapEntries);
		Collections.sort(aList, new Comparator<Entry<String, Importer>>() {
			@Override
			public int compare(Entry<String, Importer> ele1, Entry<String, Importer> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		for (Entry<String, Importer> entry : aList) {
			if (requestParams.get(entry.getKey()) != null) {
				String keyValue = requestParams.get(entry.getKey()).toString();
				if (parseBoolean(keyValue)) {
					try {
						entry.getValue().update();
						entry.getValue().remove();
						successMessages.append(entry.getValue().getName() + " is updated successfully. <br>");
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage(), e);
						errorMessages.append(entry.getValue().getName() + " is updated failed. Reason: " + e.getMessage() + " <br>");
					}
				}
			}
		}

		if (successMessages.length() != 0) {
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.SUCCESS, successMessages.toString());
		}
		if (errorMessages.length() != 0) {
			redirectAttributes.addFlashAttribute(WebConsoleConstants.Model.ERROR, errorMessages.toString());
		}

		cacheManager.clearAll();

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_UPDATE+"?clearsessions=1");
		return redirectView;
	}

	public boolean parseBoolean(String value) {
		return ((value != null) && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")));
	}

}

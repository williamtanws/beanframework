package com.beanframework.console.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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

import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.PlatformUpdateWebConstants;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.console.registry.ImportListenerRegistry;
import com.beanframework.console.service.PlatformService;

import net.sf.ehcache.CacheManager;

@Controller
public class PlatformUpdateController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PlatformUpdateController.class);

	@Value(PlatformUpdateWebConstants.Path.UPDATE)
	private String PATH_UPDATE;

	@Value(PlatformUpdateWebConstants.View.UPDATE)
	private String VIEW_UPDATE;

	@Autowired
	private ImportListenerRegistry importerRegistry;

	@Autowired(required = false)
	private CacheManager cacheManager;

	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private PlatformService platformService;

	@GetMapping(value = PlatformUpdateWebConstants.Path.UPDATE)
	public String list(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		Set<Entry<String, ImportListener>> mapEntries = importerRegistry.getListeners().entrySet();
		List<Entry<String, ImportListener>> aList = new LinkedList<Entry<String, ImportListener>>(mapEntries);
		Collections.sort(aList, new Comparator<Entry<String, ImportListener>>() {
			@Override
			public int compare(Entry<String, ImportListener> ele1, Entry<String, ImportListener> ele2) {
				Integer sort1 = ele1.getValue().getSort();
				Integer sort2 = ele2.getValue().getSort();
				return sort1.compareTo(sort2);
			}
		});

		model.addAttribute("updates", aList);

		if (requestParams.get("clearsessions") != null) {
			String clearsessions = (String) requestParams.get("clearsessions");
			if (clearsessions.equals("1")) {
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

	@PostMapping(value = PlatformUpdateWebConstants.Path.UPDATE)
	public RedirectView update(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {

		Set<String> keysToUpdate = new HashSet<String>();

		for (Entry<String, ImportListener> entry : importerRegistry.getListeners().entrySet()) {
			if (requestParams.get(entry.getKey()) != null) {
				String keyValue = requestParams.get(entry.getKey()).toString();
				if (parseBoolean(keyValue)) {
					keysToUpdate.add(entry.getKey());
				}
			}
		}
		
		String[] messages = platformService.update(keysToUpdate);

		if (messages[0].length() != 0) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.SUCCESS, messages[0]);
		}
		if (messages[1].length() != 0) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, messages[1]);
		}

		clearAllCaches();

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);

		if (requestParams.get("clearsessions") == null) {
			redirectView.setUrl(PATH_UPDATE + "?clearsessions=0");
		} else {
			redirectView.setUrl(PATH_UPDATE + "?clearsessions=1");
		}
		return redirectView;
	}

	public void clearAllCaches() {
		if (cacheManager != null)
			for (String name : cacheManager.getCacheNames()) {
				cacheManager.getCache(name).removeAll();
			}
	}

	public boolean parseBoolean(String value) {
		return ((value != null) && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")));
	}

}

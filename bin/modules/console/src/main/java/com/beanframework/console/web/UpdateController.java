package com.beanframework.console.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.UpdateWebConstants;
import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.service.ImexService;

@Controller
public class UpdateController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(UpdateController.class);

	@Value(UpdateWebConstants.Path.UPDATE)
	private String PATH_UPDATE;

	@Value(UpdateWebConstants.View.UPDATE)
	private String VIEW_UPDATE;

	@Autowired(required = false)
	private CacheManager cacheManager;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private ImexService platformService;

	@Value(ImexConstants.IMEX_IMPORT_UPDATE_LOCATIONS)
	List<String> IMEX_IMPORT_LOCATIONS;

	@GetMapping(value = UpdateWebConstants.Path.UPDATE)
	public String list(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {

		model.addAttribute("updated", false);
		return VIEW_UPDATE;
	}

	@PostMapping(value = UpdateWebConstants.Path.UPDATE)
	public String update(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {

		if (requestParams.get("updateIds") == null) {
			model.addAttribute("updated", false);
			return VIEW_UPDATE;
		}

		String[] updateIds = ((String) requestParams.get("updateIds")).split(",");
		String[] messages = null;

		TreeMap<String, Set<String>> locationAndFolders = new TreeMap<String, Set<String>>();

		for (int i = 0; i < updateIds.length; i++) {

			String[] values = updateIds[i].split(";");

			if (values.length == 2) {
				String location = values[0];
				String folder = values[1];

				if (locationAndFolders.get(location) == null) {
					locationAndFolders.put(location, new HashSet<String>());
				}

				Set<String> folders = locationAndFolders.get(location);
				folders.add(folder);

				locationAndFolders.put(location, folders);
			}
		}

		messages = platformService.importByFoldersByClasspathLocations(locationAndFolders);

		clearAllCaches();
		for (Object principal : sessionRegistry.getAllPrincipals()) {
			List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
			for (SessionInformation sessionInformation : sessionInformations) {
				sessionInformation.expireNow();
			}
		}

		if (messages != null && messages[0].length() != 0)
			model.addAttribute(ConsoleWebConstants.Model.SUCCESS, messages[0].replace("\n", "<br>"));

		if (messages != null && messages[1].length() != 0)
			model.addAttribute(ConsoleWebConstants.Model.ERROR, messages[1].replace("\n", "<br>"));

		model.addAttribute("updated", true);

		return VIEW_UPDATE;

	}

	public void clearAllCaches() {
		if (cacheManager != null)
			for (String name : cacheManager.getCacheNames()) {
				cacheManager.getCache(name).clear();
			}
	}

	public boolean parseBoolean(String value) {
		return ((value != null) && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("on")));
	}

}

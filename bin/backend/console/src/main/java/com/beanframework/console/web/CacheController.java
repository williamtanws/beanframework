package com.beanframework.console.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.console.CacheWebConstants;
import com.beanframework.console.ConsoleWebConstants;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;

@Controller
public class CacheController {

	@Value(CacheWebConstants.Path.CACHE)
	private String PATH_CACHE;

	@Value(CacheWebConstants.View.CACHE)
	private String VIEW_CACHE;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Autowired
	private CacheManager cacheManager;

	@GetMapping(value = { CacheWebConstants.Path.CACHE })
	public String cache(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		List<Cache> caches = new ArrayList<Cache>();

		for (String name : cacheManager.getCacheNames()) {
			Cache cache = cacheManager.getCache(name);
			caches.add(cache);
		}

		model.addAttribute("caches", caches);

		return VIEW_CACHE;
	}

	@PostMapping(value = { CacheWebConstants.Path.CACHE_CLEARALL }, params = "clearall")
	public RedirectView clear(Model model, @RequestParam Map<String, Object> allRequestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		try {
			cacheManager.clearAll();

			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.SUCCESS, localeMessageService.getMessage(CacheWebConstants.Locale.CACHE_CLEARALL_SUCCESS));
		} catch (CacheException e) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, e.toString());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CACHE);
		return redirectView;
	}

}

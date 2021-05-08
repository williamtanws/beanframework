package com.beanframework.console.web;

import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.CsvUtils;
import com.beanframework.console.SearchWebConstants;
import com.beanframework.core.controller.AbstractController;

@Controller
public class SearchController extends AbstractController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(UpdateController.class);

	@Value(SearchWebConstants.Path.SEARCH)
	private String PATH_SEARCH;

	@Value(SearchWebConstants.View.SEARCH)
	private String VIEW_SEARCH;

	@Autowired
	private ModelService modelService;

	@GetMapping(value = SearchWebConstants.Path.SEARCH)
	public String searchView(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_SEARCH;
	}

	@PostMapping(value = SearchWebConstants.Path.SEARCH)
	public String search(@RequestParam("query") String query, Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {

		model.addAttribute("query", query);

		List<?> resultList = modelService.searchByQuery(query);

		StringBuilder resultBuilder = CsvUtils.List2Csv(resultList);

		if (resultBuilder.length() != 0) {
			model.addAttribute("result", resultBuilder.toString());
		}
		return VIEW_SEARCH;
	}
}

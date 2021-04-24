package com.beanframework.console.web;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.service.ModelService;
import com.beanframework.console.PlatformExportWebConstants;
import com.beanframework.core.controller.AbstractController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@PreAuthorize("isAuthenticated()")
@Controller
public class PlatformExportController extends AbstractController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PlatformUpdateController.class);

	@Value(PlatformExportWebConstants.Path.EXPORT)
	private String PATH_EXPORT;

	@Value(PlatformExportWebConstants.View.EXPORT)
	private String VIEW_EXPORT;

	@Autowired
	private ModelService modelService;

	@GetMapping(value = PlatformExportWebConstants.Path.EXPORT)
	public String exportView(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_EXPORT;
	}

//	@PostMapping(value = PlatformImportWebConstants.Path.IMPORT, params="importFile")
//	public RedirectView importFile(@RequestParam("files") MultipartFile[] files, Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes,
//			HttpServletRequest request) {
//
//		String[] messages = platformService.importByMultipartFiles(files);
//		
//		if (messages[0].length() != 0) {
//			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.SUCCESS, messages[0]);
//		}
//		if (messages[1].length() != 0) {
//			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, messages[1]);
//		}
//
//		RedirectView redirectView = new RedirectView();
//		redirectView.setContextRelative(true);
//		redirectView.setUrl(PATH_IMPORT);
//		return redirectView;
//	}

	@PostMapping(value = PlatformExportWebConstants.Path.EXPORT, params = "importQuery")
	public String exportQuery(@RequestParam("query") String query, Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) throws JsonProcessingException {

		Collection<?> models = modelService.search(query);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(models);

		model.addAttribute("query", query);
		model.addAttribute("result", jsonString);
		return VIEW_EXPORT;
	}
	
//	private static List<ObjectNode> toJson(List<Tuple> results) {
//
//	    List<ObjectNode> json = new ArrayList<>();
//
//	    ObjectMapper mapper = new ObjectMapper();
//
//	    for (Tuple tuple : results)
//	    {
//	        List<TupleElement<?>> cols = tuple.getElements();
//
//	        ObjectNode node = mapper.createObjectNode();
//
//	        for (TupleElement col : cols)
//	            node.put(col.getAlias(), tuple.get(col.getAlias()).toString());
//
//	        json.add(node);
//	    }
//	    return json;
//	}

}

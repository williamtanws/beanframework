package com.beanframework.console.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.CsvUtils;
import com.beanframework.console.ExportWebConstants;
import com.beanframework.core.controller.AbstractController;

@Controller
public class ExportController extends AbstractController {

	protected static final Logger LOGGER = LoggerFactory.getLogger(UpdateController.class);

	@Value(ExportWebConstants.Path.EXPORT)
	private String PATH_EXPORT;

	@Value(ExportWebConstants.View.EXPORT)
	private String VIEW_EXPORT;

	@Autowired
	private ModelService modelService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");

	@GetMapping(value = ExportWebConstants.Path.EXPORT)
	public String exportView(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_EXPORT;
	}

	@PostMapping(value = ExportWebConstants.Path.EXPORT)
	public ResponseEntity<InputStreamResource> exportQuery(@RequestParam("query") String query, Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws IOException {

		List<?> resultList = modelService.searchByQuery(query);
		
		StringBuilder resultBuilder = CsvUtils.List2Csv(resultList);

		File temp = File.createTempFile("export", ".csv");

		// Delete temp file when program exits.
		temp.deleteOnExit();

		// Write to temp file
		BufferedWriter out = new BufferedWriter(new FileWriter(temp));
		out.write(resultBuilder.toString());
		out.close();

		InputStreamResource resource = new InputStreamResource(new FileInputStream(temp));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "export_"+sdf.format(new Date())+".csv").contentType(MediaType.valueOf("text/csv")).contentLength(temp.length()).body(resource);
	}

}

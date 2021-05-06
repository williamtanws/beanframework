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
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm:ss");

	@GetMapping(value = PlatformExportWebConstants.Path.EXPORT)
	public String exportView(Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		return VIEW_EXPORT;
	}

	@PostMapping(value = PlatformExportWebConstants.Path.EXPORT)
	public ResponseEntity<InputStreamResource> exportQuery(@RequestParam("query") String query, Model model, @RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws IOException {

		List<?> resultList = modelService.searchByQuery(query);

		final StringBuilder csvBuilder = new StringBuilder();
		for (final Object object : resultList) {
			final Object[] values = (Object[]) object;

			for (int i = 0; i < values.length; i++) {

				if (values[i] == null) {
					csvBuilder.append("\"\"");
				} else {
					csvBuilder.append("\"" + values[i].toString() + "\"");
				}

				if (i != 0 && i != values.length - 1) {
					csvBuilder.append(";");
				}
			}
			csvBuilder.append(System.getProperty("line.separator"));
		}

		File temp = File.createTempFile("export", ".csv");

		// Delete temp file when program exits.
		temp.deleteOnExit();

		// Write to temp file
		BufferedWriter out = new BufferedWriter(new FileWriter(temp));
		out.write(csvBuilder.toString());
		out.close();

		InputStreamResource resource = new InputStreamResource(new FileInputStream(temp));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "export_"+sdf.format(new Date())).contentType(MediaType.valueOf("text/csv")).contentLength(temp.length()).body(resource);
	}

}

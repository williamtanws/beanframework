package com.beanframework.documentation.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.beanframework.documentation.DocumentationConstants;
import com.beanframework.documentation.DocumentationConstants.DocumentationPreAuthorizeEnum;

@Controller
public class DocumentationController {
	
	@Value(DocumentationConstants.View.DOCUMENTATION)
	private String VIEW_DOCUMENTATION;

	@PreAuthorize(DocumentationPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = DocumentationConstants.Path.DOCUMENTATION)
	public String documentation() {
		return VIEW_DOCUMENTATION;
	}
}

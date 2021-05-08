package com.beanframework.documentation.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.beanframework.documentation.DocumentationConstants;
import com.beanframework.documentation.DocumentationConstants.DocumentationPreAuthorizeEnum;

@PreAuthorize("isAuthenticated()")
@Controller
public class DocumentationController {

	@PreAuthorize(DocumentationPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = DocumentationConstants.Path.DOCUMENTATION)
	public String documentation() {
		return DocumentationConstants.View.DOCUMENTATION;
	}
}

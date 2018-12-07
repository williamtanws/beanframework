package com.beanframework.backoffice.api;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebLanguageConstants;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageFacade;

@RestController
public class LanguageResource {
	@Autowired
	private LanguageFacade languageFacade;

	@PreAuthorize(WebLanguageConstants.PreAuthorize.READ)
	@RequestMapping(WebLanguageConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) {

		String id = requestParams.get(WebBackofficeConstants.Param.ID).toString();
		Language language = languageFacade.findById(id);

		String uuidStr = (String) requestParams.get(WebBackofficeConstants.Param.UUID);
		if (StringUtils.isNotEmpty(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (language != null && language.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return language != null ? "false" : "true";
	}
}
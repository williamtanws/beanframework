package com.beanframework.backoffice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.LanguageWebConstants;
import com.beanframework.language.domain.Language;
import com.beanframework.language.service.LanguageFacade;

@RestController
public class LanguageResource {
	@Autowired
	private LanguageFacade languageFacade;

	@RequestMapping(LanguageWebConstants.Path.Api.CHECKID)
	public String checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Language.ID, id);
		
		Language language = languageFacade.findOneDtoByProperties(properties);
		
		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (language != null && language.getUuid().equals(uuid)) {
				return "true";
			}
		}

		return language != null ? "false" : "true";
	}
}
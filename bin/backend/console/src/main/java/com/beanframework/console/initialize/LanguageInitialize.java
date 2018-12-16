package com.beanframework.console.initialize;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.Initializer;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.WebPlatformConstants;
import com.beanframework.console.WebPlatformConstants.Initialize.Language;
import com.beanframework.language.service.LanguageFacade;

public class LanguageInitialize extends Initializer {
	protected final Logger logger = LoggerFactory.getLogger(LanguageInitialize.class);

	@Autowired
	private ModelService modelService;

	@PostConstruct
	public void initializer() {
		setKey(WebPlatformConstants.Initialize.Language.KEY);
		setName(WebPlatformConstants.Initialize.Language.NAME);
		setSort(WebPlatformConstants.Initialize.Language.SORT);
		setDescription(WebPlatformConstants.Initialize.Language.DESCRIPTION);
	}

	@Override
	public void initialize() {
		modelService.removeAll(Language.class);
	}

}

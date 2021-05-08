package com.beanframework.imex.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.service.ImexService;

@Component
public class ImexInit implements ApplicationListener<ApplicationReadyEvent> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ImexInit.class);

	@Autowired
	private ImexService imexService;

	@Value(ImexConstants.IMEX_IMPORT_INIT_LOCATIONS)
	private List<String> IMEX_IMPORT_INIT_LOCATIONS;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		LOGGER.info("Init imex");
		try {

			if (IMEX_IMPORT_INIT_LOCATIONS != null) {
				imexService.importByFoldersByLocations(null, IMEX_IMPORT_INIT_LOCATIONS);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}

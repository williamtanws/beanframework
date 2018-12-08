package com.beanframework.console.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationResource {
	
	@Autowired
	private LoggersEndpoint loggersEndpoint;

	@GetMapping("${path.api.application.setloggerlevel}")
	public ResponseEntity<Void> setLoggerLevel(Model model, @RequestParam Map<String, Object> requestParams) {
		
		String name = requestParams.get("name").toString();
		String configuredLevel = requestParams.get("level").toString();

		loggersEndpoint.configureLogLevel(name, LogLevel.valueOf(configuredLevel));
		
		return ResponseEntity.noContent().build();
	}
}
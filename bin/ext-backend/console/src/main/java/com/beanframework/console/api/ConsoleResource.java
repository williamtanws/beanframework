package com.beanframework.console.api;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsoleResource {

	@GetMapping("${path.console.checktimeout}")
	public ResponseEntity<Void> checktimeout(Model model) {
		return ResponseEntity.noContent().build();
	}
}
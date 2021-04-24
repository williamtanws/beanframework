package com.beanframework.console.api;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.core.api.AbstractResource;

@RestController
public class ConsoleResource extends AbstractResource {

	@GetMapping("${path.console.checktimeout}")
	public ResponseEntity<Void> checktimeout(Model model) {
		return ResponseEntity.noContent().build();
	}
}
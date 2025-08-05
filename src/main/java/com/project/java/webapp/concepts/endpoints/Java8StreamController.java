package com.project.java.webapp.concepts.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.webapp.concepts.service.Java8StreamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("streams")
@RequiredArgsConstructor
public class Java8StreamController {

	private final Java8StreamService java8StreamService;

	@GetMapping
	public ResponseEntity<String> testStreams() {
		return ResponseEntity.ok(java8StreamService.testStreams());
	}

}

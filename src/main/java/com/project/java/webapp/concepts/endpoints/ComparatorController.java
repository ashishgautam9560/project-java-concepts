package com.project.java.webapp.concepts.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.webapp.concepts.service.ComparatorService;

import lombok.RequiredArgsConstructor;

@RequestMapping("comparator")
@RestController
@RequiredArgsConstructor
public class ComparatorController {

	private final ComparatorService comparatorService;

	@GetMapping
	public ResponseEntity<String> learningComparator() {

		return ResponseEntity.ok(comparatorService.learningComparator());
	}

}

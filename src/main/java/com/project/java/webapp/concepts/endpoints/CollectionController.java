package com.project.java.webapp.concepts.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.webapp.concepts.service.CollectionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("collection")
@RequiredArgsConstructor
public class CollectionController {

	private final CollectionService collectionService;

	public ResponseEntity<String> learningArrayList() {
		return ResponseEntity.ok(collectionService.learningArrayList());
	}

}

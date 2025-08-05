package com.project.java.webapp.concepts.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CodeSnippetService {

	public String getResult() {
		try {
			throw new Exception("exception");
		} catch (Exception e) {
			log.info("Inside catch");
			return "catch";
		} finally {
			return "finally";
		}
	}

}

package com.project.java.webapp.concepts.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	
	// REGEX pattern matching example
	public String getEmployeeNameById(Integer id) {
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(id.toString());
		
		if (matcher.matches()) {
			return "Employee Id: " + id;
		} else {
			return "Invalid ID Format";
		}
	}

	
	// + It modifies original string.
	// .concat() - modifies new string created.
	public static void stringQues() {
		String str = "Hello";
		str += "    World";
		str.trim();
		log.info(str);
	}

}

package com.project.java.webapp.concepts.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CodeSnippetService {
	
	/*
	 * Exception questions
	 */
	/*
	 *  We have return in catch. But still the final value return is from finally
	 */
	@SuppressWarnings("finally")
	public static String getResult() {
		try {
			throw new Exception("exception");
		} catch (Exception e) {
			log.info("Inside catch");
			return "catch";
		} finally {
			return "finally";
		}
	}
	
	
	
	
	/*
	 * ---------------------------------------------------------------------------------------------------------------------------
	 * REGEX
	 */
	public static String getEmployeeNameById(Integer id) {
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(id.toString());

		if (matcher.matches()) {
			return "Employee Id: " + id;
		} else {
			return "Invalid ID Format";
		}
	}
	
	/*
	 * ---------------------------------------------------------------------------------------------------------------------------
	 * STRING Questions
	 */
	/*
	 * 1. concat() creates a new string, but result is not assigned.
	 * 2. += It creates a new String object and reassigns the reference.
	 * 			- euqivalent to = new StringBuilder(str).append(" World").toString();
	 */
	public static void stringQues() {
		String str = "Hello";
		str.concat("Concat"); 
		str += "    World";   
		str.trim();
		log.info(str);
	}
	
	
	
	
	/*
	 * ---------------------------------------------------------------------------------------------------------------------------
	 * Java8 Questions
	 */
	
	/* 1. Output - Invoking getDefault
	 * 			   john
	 * 2. orElse() --> Eager evaluation. Hence this method is invoked immediately 
	 * 								     even if Optional already contains a value.
	 * 
	 * 3. or(), orElseGet(), orElseThrow --> For Lazy evaluation.
	 * 			
	 */
	public static void optionalOrElse() {
		String result = Optional.ofNullable("john").orElse(getDefault());
		log.info(result);
	}

	private static String getDefault() {
		log.info("Invoking getDefault");
		return "Default Name";
	}

}


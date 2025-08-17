package com.project.java.webapp.concepts.misc;

import com.project.java.webapp.concepts.enums.Operation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Switch {

	// Switch Block
	// Java 7 - Only Integer was allowed
	public static void switchJava7(int n) {
		switch (n) {
		case 1:
			log.info("1");
			break;
		case 2:
			log.info("2");
			log.info("3");
			break;
		default:
			log.info("-1");
			break;
		}
	}

	// Java 8 - String and Enums are also allowed.
	public static void switchJava8(String str, Operation op) {
		switch (str) {
		case "Hello":
			log.info("");
			break;
		case "world":
			log.info("");
			break;
		case "HeLLo":
			log.info("");
			break;
		default:
			break;
		}

		// -----------------------
		switch (op) {
		case ADD:
			log.info("Addition");
			break;
		case MUL:
			log.info("Multiplication");
			break;
		case DIV:
			log.info("Division");
			break;
		case SUB:
			log.info("Subtraction");
			break;
		default:
			log.info("Invalid..!!");
			break;
		}
	}

	// Java 12
	// Switch Expression (->) means now switch can return value as well
	// No need of break; as return will terminate switch
	// We can have multiple values in each switch
	public static void switchJava12(Operation op) {
		String res = switch (op) {
		case ADD -> "+";
		case SUB -> "-";
		case DIV -> "/";
		case MUL -> "*";
		case UN, BIN, TERN -> "$";
		default -> "-1";
		};
		log.info("Result: {}", res);
	}

	// Java13 - yield = return
	// to have multiple statements inside switch condition
	public static void switchJava13(Operation op) {
		String res = switch (op) {
		case ADD -> {
			log.info("Addition");
			yield "+";
		}
		case SUB -> {
			log.info("Subtraction");
			yield "-";
		}
		case DIV -> {
			log.info("Division");
			yield "/";
		}
		case MUL -> {
			log.info("Multiplication");
			yield "*";
		}
		case UN, BIN, TERN -> {
			log.info("Invalid");
			yield "$";
		}
		default -> "-1";
		};
		log.info("Result: {}", res);
	}

	// Java21 - Null Case, Pattern Matching
	public static void switchJava21(Operation op) {
		Object obj = "Hello";

//		String res = switch(obj) {
//			case null -> "Value is NULL";
//			case String s -> "Value is String";
//			case Integer i -> "Value is Integer";
//			default -> "Unknown Type";
//		};
		log.info("Obj: {}", obj);
	}

}

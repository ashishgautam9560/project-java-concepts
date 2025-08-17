package com.project.java.webapp.concepts.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.webapp.concepts.misc.Switch;

@RestController
@RequestMapping(value = "misc")
public class MiscController {

	@GetMapping(value = "switch/{num}")
	public ResponseEntity<String> testSwitch(@PathVariable Integer num) {
		Switch.switchJava7(num);
		return new ResponseEntity<>("Switch is working", HttpStatus.OK);
	}

}

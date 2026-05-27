package com.project.java.webapp.concepts;

import org.springframework.boot.SpringApplication;

import com.project.java.webapp.concepts.threads.ThreadExecutor16;

public class ProjectJavaConceptsApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ProjectJavaConceptsApplication.class, args);

		ThreadExecutor16.executorServiceSubmit();
	}

}

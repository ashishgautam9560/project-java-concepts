package com.project.java.webapp.concepts.comparable;

import lombok.AllArgsConstructor;
import lombok.Data;

// 1. If we call list.sort() on Integers List - it will work.
// 2. But if we call list.sort() on Students - 
//    Either we have to pass comparator to it.
//    Or provide a natural sorting/ordering in Student itself.

@AllArgsConstructor
@Data
public class Student implements Comparable<Student> {

	private String name;
	private Double gpa;

	// Use in build methods of Wrapper classes
	@Override
	public int compareTo(Student o) {
		int gpaCompare = this.gpa.compareTo(o.gpa);
		if (gpaCompare != 0) {
			return gpaCompare;
		}
		return this.name.compareTo(o.name);
	}

}

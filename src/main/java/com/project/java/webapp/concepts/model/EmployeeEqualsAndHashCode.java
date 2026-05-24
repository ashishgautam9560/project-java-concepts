package com.project.java.webapp.concepts.model;

import java.util.Objects;

import lombok.Data;

@Data
public class EmployeeEqualsAndHashCode {

	private Integer empId;
	private String empName;
	private String department;
	private Double salary;
	private String email;
	private Boolean isActive;
	
	@Override
	public int hashCode() {
		return Objects.hash(department, email, empId, empName, isActive, salary);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeEqualsAndHashCode other = (EmployeeEqualsAndHashCode) obj;
		return Objects.equals(department, other.department) && 
			   Objects.equals(email, other.email) && 
			   Objects.equals(empId, other.empId) && 
			   Objects.equals(empName, other.empName) && 
			   Objects.equals(isActive, other.isActive) && 
			   Objects.equals(salary, other.salary);
	}

}

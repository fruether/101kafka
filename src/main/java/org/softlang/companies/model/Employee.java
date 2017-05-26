package org.softlang.companies.model;


import javax.json.Json;
/**
 * An employee has a name, an address, and a salary.
 */

public class Employee {
	
	private String name;
	private String address;
	private double salary;
	private Department dep;
	
	public Employee(String name, double salary) {
		this.salary = salary;
		this.name = name;
	}
	public Employee(String name, double salary, String address) {
		this.salary = salary;
		this.name = name;
		this.address = address;
	}
	
	public void setDepartment(Department dep) {
		this.dep = dep;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getSalary() {
		return salary;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public  String toJson() {
		
		return Json.createObjectBuilder().add("name", name).add("salary", salary)
		.add("department", dep.getName()).add("company", dep.getCompanyName()).build().toString();
		
	}
}

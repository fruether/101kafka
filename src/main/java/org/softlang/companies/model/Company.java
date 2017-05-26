package org.softlang.companies.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A company has a name and
 * consists of (possibly nested) departments.
 */
public class Company {
	
	private String name;
	private List<Department> depts = new ArrayList<>();
	
	public Company() { }
	public Company(String name) {
		this.name = name;
	}
	public Company(String name, List<Department> deps) {
		this.name = name;
		this.depts = deps;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Department> getDepts() {
		return depts;
	}
	
	public void addDepartment(Department dep) {
		depts.add(dep);
		dep.setCompany(this);
	}
	public void addDepartment(Department ... deps) {
		for(Department dep : deps)
			this.addDepartment(dep);
	}
	
}

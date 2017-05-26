package org.softlang.companies;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.softlang.companies.model.Company;
import org.softlang.companies.model.Department;
import org.softlang.companies.model.Employee;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by freddy on 26.05.17.
 */
public class Producer101 {
	KafkaProducer<String, String> producer;
	
	public Producer101() throws IOException{
		InputStream props = Resources.getResource("producer.props").openStream();
		Properties properties = new Properties();
		properties.load(props);
		producer = new KafkaProducer<>(properties);
	}
	
	public void produceEmployee(Employee emp) {
		producer.send(new ProducerRecord<String, String>("101companies", emp.toJson()));
		System.out.println(emp.toJson());
		producer.flush();
	}
	
	public void produceDepartment(Department department) {
		Employee manager = department.getManager();
		if(manager != null)
			produceEmployee(manager);
		
		for(Department subDepartment :  department.getSubdepts()) {
			produceDepartment(subDepartment);
		}
		for(Employee emp : department.getEmployees()) {
			produceEmployee(emp);
		}
	}
	
	public void produceCompany(Company c) {
		List<Department> departments  = c.getDepts();
		for (Department department : departments) {
			produceDepartment(department);
		}
		
		
	}
	
	public static Company setUp() {
		
		Company comp = new Company("ACME");
		
		Department research = new Department("Research");
		Department development = new Department("Developement");
		comp.addDepartment(research, development);
		
		Department dev1 = new Department("dev1");
		Department dev2 = new Department("dev2");
		
		development.addDepartment(dev1, dev2);
		
		research.setManager(new Employee("Fred", 88888));
		development.setManager(new Employee("Marie", 77777));
		
		dev1.setManager(new Employee("Bob", 77776));
		dev2.setManager(new Employee("Alice", 77775));
		
		Employee ralf = new Employee("Ralf", 4711);
		Employee peter = new Employee("Peter", 2222);
		
		dev2.addEmployee(ralf, peter);
		return comp;
	}
	
	public static void main(String[] args) throws IOException {
		Company company = Producer101.setUp();
		Producer101 producer = new Producer101();
		producer.produceCompany(company);
		
	}
}

package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {

	public List<Department> findAll(){
		List<Department>listaa=new ArrayList<>();
		listaa.add(new Department(1,"Books"));
		listaa.add(new Department(2,"Computers"));
		listaa.add(new Department(3,"Eletronics"));
		listaa.add(new Department(4,"Moveis"));
		return listaa;
		
	}
}

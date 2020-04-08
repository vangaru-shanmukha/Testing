package spring.boot.project.demo2.service;

import java.util.List;

import spring.boot.project.demo2.entity.Department;

public interface DepartmentService {
	
	public List<Department> findAll();

	public Department findById(int theDepartmentId);

	public void save(Department theDepartment);

	public void deleteById(int theDepartmentId);

}

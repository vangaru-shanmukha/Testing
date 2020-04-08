package spring.boot.project.demo2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentRestController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/")
	public List<Department> getDepartments(){
		return departmentService.findAll();
	}
	
	@PutMapping("/")
	public Department updateDepartment(@RequestBody Department theDepartment) {
		departmentService.save(theDepartment);
		return theDepartment;
	}
	
	@PostMapping("/")
	public Department addDepartment(@RequestBody Department theDepartment) {
		departmentService.save(theDepartment);
		return theDepartment;
	}

}

package spring.boot.project.demo2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
	
	List<Student> findByDepartment(Department department);

}

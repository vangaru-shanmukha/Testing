package spring.boot.project.demo2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	List<Course> findByDepartment(Department department);
	
}

package spring.boot.project.demo2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.service.CourseService;
import spring.boot.project.demo2.service.DepartmentService;

@RestController
@RequestMapping("/courses")
public class CourseRestController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/")
	public List<Course> getCourses(){
		return courseService.findAll();
	}
	
	@GetMapping("{id}/")
	public Course getCourse(@PathVariable(value = "id") int courseId) {
		return courseService.findById(courseId);
	}
	
	@GetMapping("{id}/department/")
	public Department getDepartment(@PathVariable(value = "id") int courseId) {
		return courseService.findById(courseId).getDepartment();
	}
	
	@GetMapping("{id}/students/")
	public List<Student> getStudents(@PathVariable(value = "id") int courseId) {
		return courseService.findById(courseId).getStudents();
	}
	
	@PostMapping("/")
	public Course addCourse(@RequestBody String str) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(str);
		String courseName = node.get("name").asText();
		int departmentId = node.get("department").get("id").asInt();
		Department department = departmentService.findById(departmentId);
		Course theCourse = new Course(courseName,department);
		courseService.save(theCourse);
		return theCourse;
	}
	
	@PutMapping("/")
	public Course updateCourse(@RequestBody String str) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(str);
		int courseId = node.get("id").asInt();
		int departmentId = node.get("department").get("id").asInt();
		Department department = departmentService.findById(departmentId);
		Course theCourse = courseService.findById(courseId);
		theCourse.setName(node.get("name").asText());
		theCourse.setDepartment(department);
		courseService.save(theCourse);
		return theCourse;
	}
	
	@DeleteMapping("{id}/")
	public String deleteCourse(@PathVariable(value = "id") int courseId) {
		courseService.deleteById(courseId);
		return "Success";
	}
}

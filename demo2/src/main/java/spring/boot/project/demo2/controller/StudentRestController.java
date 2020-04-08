package spring.boot.project.demo2.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import spring.boot.project.demo2.dao.RoleRepository;
import spring.boot.project.demo2.entity.Address;
import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.entity.User;
import spring.boot.project.demo2.service.AddressService;
import spring.boot.project.demo2.service.CourseService;
import spring.boot.project.demo2.service.DepartmentService;
import spring.boot.project.demo2.service.StudentService;
import spring.boot.project.demo2.service.UserService;

@RestController
@RequestMapping("/students")
public class StudentRestController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("/")
	public List<Student> getStudents(){	
		return studentService.findAll();
	}
	
	@GetMapping("/{id}")
	public Student getStudent(@PathVariable(value = "id") String rollNumber) {
		return studentService.findById(rollNumber);
	}
	
	@GetMapping("/{id}/department")
	public Department getDepartment(@PathVariable(value = "id") String rollNumber) {
		return studentService.findById(rollNumber).getDepartment();
	}
	
	@GetMapping("/{id}/courses")
	public List<Course> getCourses(@PathVariable(value = "id") String rollNumber) {
		return studentService.findById(rollNumber).getCourses();
	}
	
	@PutMapping("{id}/department/{departmentId}/courses/{courseId}")
	public Student addCourse(@PathVariable(value = "id") String rollNumber,
							  @PathVariable(value = "departmentId") int departmentId,
							  @PathVariable(value = "courseId") int courseId) throws Exception {
		
		Student theStudent = studentService.findById(rollNumber);
		Department department = departmentService.findById(departmentId);
		Course theCourse = courseService.findById(courseId);
		theStudent = studentService.addCourse(theStudent, theCourse, department);
		return theStudent;
	}
	
	@DeleteMapping("{id}/department/{departmentId}/courses/{courseId}")
	public Student deleteCourse(@PathVariable(value = "id") String rollNumber,
			  @PathVariable(value = "departmentId") int departmentId,
			  @PathVariable(value = "courseId") int courseId) throws Exception {
		Student theStudent = studentService.findById(rollNumber);
		Department department = departmentService.findById(departmentId);
		Course theCourse = courseService.findById(courseId);
		theStudent = studentService.deleteCourse(theStudent, theCourse, department);
		return theStudent;
	}
	
	@PutMapping("{id}/department/{departmentId}")
	public Student updateDepartment(@PathVariable(value = "id") String rollNumber,
			  						@PathVariable(value = "departmentId") int departmentId) {
		Student theStudent = studentService.findById(rollNumber);
		Department department = departmentService.findById(departmentId);
		theStudent.setDepartment(department);
		theStudent.setCourses(null);
		studentService.save(theStudent);
		return theStudent;
	}
	
	@PutMapping("/update")
	public Student update(@RequestBody String str) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(str);
		int departmentId = node.get("department").get("id").asInt();
		int courseId = node.get("courses").get("id").asInt();
		int zipcode = node.get("address").get("zipCode").asInt();
		Department department = departmentService.findById(departmentId);
		Course course = courseService.findById(courseId);
		Address address = addressService.findById(zipcode);
		Student theStudent = new Student();
		theStudent.setFirstName(node.get("personal").get("firstName").asText());
		theStudent.setLastName(node.get("personal").get("lastName").asText());
		theStudent.setHouseNumber(node.get("personal").get("houseNumber").asText());
		theStudent.setStreet(node.get("personal").get("street").asText());
		theStudent.setRollNumber(node.get("personal").get("rollNumber").asText());
		theStudent.setDateOfBirth(node.get("personal").get("dateOfBirth").asText());
		theStudent.setAddress(address);
		theStudent.setDepartment(department);
		List<Course> courses = new ArrayList<Course>();
		courses.add(course);
		theStudent.setCourses(courses);
		studentService.save(theStudent);
		return theStudent;
	}
	
	@PostMapping("/add")
	public Student add(@RequestBody String str) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(str);
		int departmentId = node.get("department").get("id").asInt();
		int courseId = node.get("courses").get("id").asInt();
		int zipcode = node.get("address").get("zipCode").asInt();
		Department department = departmentService.findById(departmentId);
		Course course = courseService.findById(courseId);
		Address address = addressService.findById(zipcode);
		Student theStudent = new Student();
		theStudent.setFirstName(node.get("personal").get("firstName").asText());
		theStudent.setLastName(node.get("personal").get("lastName").asText());
		theStudent.setHouseNumber(node.get("personal").get("houseNumber").asText());
		theStudent.setStreet(node.get("personal").get("street").asText());
		theStudent.setRollNumber(node.get("personal").get("rollNumber").asText());
		theStudent.setDateOfBirth(node.get("personal").get("dateOfBirth").asText());
		theStudent.setAddress(address);
		theStudent.setDepartment(department);
		List<Course> courses = new ArrayList<Course>();
		courses.add(course);
		theStudent.setCourses(courses);
		studentService.save(theStudent);
		return theStudent;
	}
	
	@DeleteMapping("{id}")
	public String deleteStudent(@PathVariable(value = "id") String rollNumber) {
		studentService.deleteById(rollNumber);
		userService.deleteById(rollNumber);
		return "Success";
	}

}

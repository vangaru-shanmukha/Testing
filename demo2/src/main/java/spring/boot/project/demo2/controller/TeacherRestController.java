package spring.boot.project.demo2.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import spring.boot.project.demo2.entity.Role;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.entity.Teacher;
import spring.boot.project.demo2.entity.User;
import spring.boot.project.demo2.service.AddressService;
import spring.boot.project.demo2.service.CourseService;
import spring.boot.project.demo2.service.DepartmentService;
import spring.boot.project.demo2.service.StudentService;
import spring.boot.project.demo2.service.TeacherService;
import spring.boot.project.demo2.service.UserService;

@RestController
@RequestMapping("/teachers")
public class TeacherRestController {
	
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
	
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/")
	public List<Teacher> getTeachers(){	
		return teacherService.findAll();
	}
	
	@GetMapping("/{id}/departments")
	public List<Department> getDepartments(@PathVariable(value = "id") String email) {
		
		Teacher teacher = teacherService.findById(email);
		List<Department> departments = teacher.getDepartments();
		return departments;
	}
	
	@PutMapping("/{id}/departments/{departmentId}")
	public Teacher addDepartment(@PathVariable(value = "id") String email,
								 @PathVariable(value = "departmentId") int departmentId) {
		Teacher teacher = teacherService.findById(email);
		Department department = departmentService.findById(departmentId);
		return teacherService.addDepartment(teacher,department);
	}
	
	@DeleteMapping("/{id}/departments/{departmentId}")
	public Teacher deleteDepartment(@PathVariable(value = "id") String email,
									@PathVariable(value = "departmentId") int departmentId) throws Exception {
		Teacher theTeacher = teacherService.findById(email);
		Department theDepartment = departmentService.findById(departmentId);
		return teacherService.deleteDepartment(theTeacher,theDepartment);
	}
	
	@GetMapping("/{id}/courses")
	public List<Course> getCourses(@PathVariable(value = "id") String email) {
		
		Teacher teacher = teacherService.findById(email);
		List<Course> courses = teacher.getCourses();
		return courses;
	}
	
	@PutMapping("/{id}/courses/{courseId}")
	public Teacher addCourse(@PathVariable(value = "id") String email,
			@PathVariable(value = "courseId") int courseId) throws Exception {
		Teacher teacher = teacherService.findById(email);
		Course course = courseService.findById(courseId);
		return teacherService.addCourse(teacher,course);
	}
	
	@DeleteMapping("/{id}/courses/{courseId}")
	public Teacher deleteCourse(@PathVariable(value = "id") String email,
								@PathVariable(value = "courseId") int courseId) throws Exception {
		Teacher theTeacher = teacherService.findById(email);
		Course theCourse = courseService.findById(courseId);
		return teacherService.deleteCourse(theTeacher,theCourse);
	}
	
	@GetMapping("/{id}/courses/{courseId}/students")
	public List<Student> getStudents(@PathVariable(value = "id") String email,
								@PathVariable(value = "courseId") int courseId) throws Exception {
		Course course = courseService.findById(courseId);
		return course.getStudents();
	}
	
	@PutMapping("/{id}/courses/{courseId}/students/{rollNumber}")
	public void addStudent(@PathVariable(value = "id") String email,
			@PathVariable(value = "courseId") int courseId,
			@PathVariable(value = "rollNumber") String rollNumber) throws Exception {
		Course course = courseService.findById(courseId);
		Student theStudent = studentService.findById(rollNumber);
		teacherService.addStudent(theStudent,course);
	}
	
	@DeleteMapping("/{id}/courses/{courseId}/students/{rollNumber}")
	public void deleteStudent(@PathVariable(value = "id") String email,
			@PathVariable(value = "courseId") int courseId,
			@PathVariable(value = "rollNumber") String rollNumber) throws Exception {
		Course course = courseService.findById(courseId);
		Student theStudent = studentService.findById(rollNumber);
		teacherService.deleteStudent(theStudent,course);
	}
	
	@PutMapping("/{id}")
	public Teacher update(@PathVariable(value = "id") String email, @RequestBody String str) throws Exception {
		Teacher teacher = new Teacher();
		if(teacherService.findById(email) != null) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(str);
		JsonNode departments = node.get("departments");
		JsonNode courses = node.get("courses");
		List<Department> departmentList = new ArrayList<Department>();
		List<Course> courseList = new ArrayList<Course>();
		if(departments.isArray()) {
			for(final JsonNode objNode : departments) {
				departmentList.add(departmentService.findById(objNode.get("id").asInt()));
			}
		}
		if(courses.isArray()) {
			for(final JsonNode objNode : courses) {
				courseList.add(courseService.findById(objNode.get("id").asInt()));
			}
		}
		int zipcode = node.get("address").get("zipCode").asInt();
		Address address = addressService.findById(zipcode);
		teacher.setFirstName(node.get("personal").get("firstName").asText());
		teacher.setLastName(node.get("personal").get("lastName").asText());
		teacher.setHouseNumber(node.get("personal").get("houseNumber").asText());
		teacher.setStreet(node.get("personal").get("street").asText());
		teacher.setEmail(email);
		teacher.setDateOfBirth(node.get("personal").get("dateOfBirth").asText());
		teacher.setAddress(address);
		teacher.setDepartments(departmentList);
		teacher.setCourses(courseList);
		teacherService.save(teacher);
		}
		else
			throw new Exception("teacher with email not found");
		return teacher;
	}
	
	@PostMapping("/addTeacher")
	public Teacher addTeacher(@RequestBody String str) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(str);
		JsonNode departments = node.get("departments");
		JsonNode courses = node.get("courses");
		List<Department> departmentList = new ArrayList<Department>();
		List<Course> courseList = new ArrayList<Course>();
		if(departments.isArray()) {
			for(final JsonNode objNode : departments) {
				departmentList.add(departmentService.findById(objNode.get("id").asInt()));
			}
		}
		if(courses.isArray()) {
			for(final JsonNode objNode : courses) {
				courseList.add(courseService.findById(objNode.get("id").asInt()));
			}
		}
		int zipcode = node.get("address").get("zipCode").asInt();
		Address address = addressService.findById(zipcode);
		Teacher teacher = new Teacher();
		teacher.setFirstName(node.get("personal").get("firstName").asText());
		teacher.setLastName(node.get("personal").get("lastName").asText());
		teacher.setHouseNumber(node.get("personal").get("houseNumber").asText());
		teacher.setStreet(node.get("personal").get("street").asText());
		teacher.setEmail(node.get("personal").get("email").asText());
		teacher.setDateOfBirth(node.get("personal").get("dateOfBirth").asText());
		teacher.setAddress(address);
		teacher.setDepartments(departmentList);
		teacher.setCourses(courseList);
		teacherService.save(teacher);
		return teacher;
	}
	
	@DeleteMapping("/{id}")
	public String deleteTeacher(@PathVariable(value = "id") String email) {
		teacherService.deleteById(email);
		return "Success";
	}

}

package spring.boot.project.demo2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import spring.boot.project.demo2.dao.RoleRepository;
import spring.boot.project.demo2.dao.StudentRepository;
import spring.boot.project.demo2.dao.UserRepository;
import spring.boot.project.demo2.entity.Address;
import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Role;
import spring.boot.project.demo2.entity.Student;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTests {

	@Mock
	private StudentRepository studentRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private StudentServiceImpl studentService;

	@InjectMocks
	private UserServiceImpl userService;

	@InjectMocks
	private RoleServiceImpl roleService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course));
		doReturn(Arrays.asList(student)).when(studentRepository).findAll();
		List<Student> actualStudents = studentService.findAll();
		assertThat(actualStudents).isEqualTo(Arrays.asList(student));
	}

	@Test
	public void testFindById() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course));
		Mockito.when(studentRepository.findById(student.getRollNumber())).thenReturn(Optional.of(student));
		Student actualStudent = studentService.findById(student.getRollNumber());
		assertThat(actualStudent).isEqualTo(student);
	}

	@Test
	public void testDelete() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course));
		studentService.deleteById(student.getRollNumber());
		verify(studentRepository, times(1)).deleteById(student.getRollNumber());
	}

	@Test
	public void testSave() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course));
		studentService.save(student);
		Role role = new Role(2, "ROLE_STUDENT");
		verify(roleRepository, times(1)).findByRoleName(role.getRoleName());
		verify(studentRepository, times(1)).save(student);
	}

	@Test
	public void testFindByDepartment() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course));
		doReturn(Arrays.asList(student)).when(studentRepository).findByDepartment(department);
		List<Student> actualStudents = studentService.findByDepartment(department);
		assertThat(actualStudents).isEqualTo(Arrays.asList(student));
	}

	@Test
	public void testAddCourse() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course));
		Course addCourse = new Course(5, "DBMS", department);
		Student updatedStudent = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course, addCourse));
		try {
			Mockito.when(studentService.addCourse(student, addCourse, department)).thenReturn(updatedStudent);
			Student actualStudent = studentService.addCourse(student, addCourse, department);
			assertThat(actualStudent).isEqualTo(updatedStudent);
			verify(studentRepository, times(1)).save(updatedStudent);
		} catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo(null);
		}
	}

	@Test
	public void testDeleteCourse() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course1 = new Course(1, "Java", department);
		Course course2 = new Course(2, "C", department);
		Course deleteCourse = new Course(1, "Java", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course1, course2));
		Student updatedStudent = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course2));
		try {
			Mockito.when(studentService.deleteCourse(student, deleteCourse, department)).thenReturn(updatedStudent);
			Student actualStudent = studentService.deleteCourse(student, deleteCourse, department);
			assertThat(actualStudent.getRollNumber()).isEqualTo(updatedStudent.getRollNumber());
			verify(studentRepository, times(1)).save(actualStudent);
		} catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo(null);
		}
	}
}

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
import spring.boot.project.demo2.dao.TeacherRepository;
import spring.boot.project.demo2.dao.UserRepository;
import spring.boot.project.demo2.entity.Address;
import spring.boot.project.demo2.entity.Course;
import spring.boot.project.demo2.entity.Department;
import spring.boot.project.demo2.entity.Role;
import spring.boot.project.demo2.entity.Student;
import spring.boot.project.demo2.entity.Teacher;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTests {

	@Mock
	private TeacherRepository teacherRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private TeacherServiceImpl teacherService;

	@InjectMocks
	private UserServiceImpl userService;

	@InjectMocks
	private RoleServiceImpl roleService;

	@InjectMocks
	private StudentServiceImpl studentService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		doReturn(Arrays.asList(teacher)).when(teacherRepository).findAll();
		List<Teacher> actualTeachers = teacherService.findAll();
		assertThat(actualTeachers).isEqualTo(Arrays.asList(teacher));
	}

	@Test
	public void testFindById() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		Mockito.when(teacherRepository.findById(teacher.getEmail())).thenReturn(Optional.of(teacher));
		Teacher actualTeacher = teacherService.findById(teacher.getEmail());
		assertThat(actualTeacher).isEqualTo(teacher);
	}

	@Test
	public void testDelete() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		teacherService.deleteById(teacher.getEmail());
		verify(teacherRepository, times(1)).deleteById(teacher.getEmail());
		verify(userRepository, times(1)).deleteById(teacher.getEmail());
	}

	@Test
	public void testSave() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		Role role = new Role(3, "ROLE_TEACHER");
		teacherService.save(teacher);
		verify(roleRepository, times(1)).findByRoleName(role.getRoleName());
		verify(teacherRepository, times(1)).save(teacher);
	}

	@Test
	public void testFindByDepartment() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course = new Course(1, "Java", department);
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		doReturn(Arrays.asList(teacher)).when(teacherRepository).findByDepartment(department);
		List<Teacher> actualTeachers = teacherService.findByDepartment(department);
		assertThat(actualTeachers).isEqualTo(Arrays.asList(teacher));
	}

	@Test
	public void testDeleteDepartment() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department1 = new Department(1, "Computer Science");
		Department department2 = new Department(2, "Electrical Engineering");
		Course course = new Course(1, "Java", department1);
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi",
				Arrays.asList(department1, department2), Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address,
				"1999-06-22");
		Teacher updatedTeacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department1),
				Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		try {
			Mockito.when(teacherService.deleteDepartment(teacher, department2)).thenReturn(updatedTeacher);
			Teacher actualTeacher = teacherService.deleteDepartment(teacher, department2);
			assertThat(actualTeacher).isEqualTo(updatedTeacher);
			verify(teacherRepository, times(1)).save(actualTeacher);
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
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course1, course2), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		Teacher updatedTeacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course1), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		try {
			Mockito.when(teacherService.deleteCourse(teacher, course2)).thenReturn(updatedTeacher);
			Teacher actualTeacher = teacherService.deleteCourse(teacher, course2);
			assertThat(actualTeacher).isEqualTo(updatedTeacher);
			verify(teacherRepository, times(1)).save(actualTeacher);
		} catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo(null);
		}
	}

	@Test
	public void testAddDepartment() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department1 = new Department(1, "Computer Science");
		Department department2 = new Department(2, "Electrical Engineering");
		Course course = new Course(1, "Java", department1);
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department1),
				Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		Teacher updatedTeacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi",
				Arrays.asList(department1, department2), Arrays.asList(course), "H.No 12-1-449/2", "Lalapet", address,
				"1999-06-22");
		try {
			Mockito.when(teacherService.addDepartment(teacher, department2)).thenReturn(updatedTeacher);
			Teacher actualTeacher = teacherService.addDepartment(teacher, department2);
			assertThat(actualTeacher).isEqualTo(updatedTeacher);
			verify(teacherRepository, times(1)).save(actualTeacher);
		} catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo(null);
		}
	}

	@Test
	public void testAddCourse() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course1 = new Course(1, "Java", department);
		Course course2 = new Course(2, "C", department);
		Teacher teacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course1), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		Teacher updatedTeacher = new Teacher("vangarutulasi@gmail.com", "Vangaru", "Tulasi", Arrays.asList(department),
				Arrays.asList(course1, course2), "H.No 12-1-449/2", "Lalapet", address, "1999-06-22");
		try {
			Mockito.when(teacherService.addCourse(teacher, course2)).thenReturn(updatedTeacher);
			Teacher actualTeacher = teacherService.addCourse(teacher, course2);
			assertThat(actualTeacher).isEqualTo(updatedTeacher);
			verify(teacherRepository, times(1)).save(actualTeacher);
		} catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo(null);
		}
	}

	@Test
	public void testAddStudent() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course1 = new Course(1, "Java", department);
		Course course2 = new Course(2, "C", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course1));
		Student updatedStudent = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course1, course2));
		try {
			Mockito.when(teacherService.addStudent(student, course2)).thenReturn(updatedStudent);
			Student actualStudent = teacherService.addStudent(student, course2);
			assertThat(actualStudent).isEqualTo(updatedStudent);
			verify(studentRepository, times(1)).save(actualStudent);
		} catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo(null);
		}

	}

	@Test
	public void testDeleteStudent() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1, "Computer Science");
		Course course1 = new Course(1, "Java", department);
		Course course2 = new Course(2, "C", department);
		Student student = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course1, course2));
		Student updatedStudent = new Student("Vangaru", "Shanmukha", department, "H.No 12-1-449/2", "Lalapet", address,
				"16BD1A05CU", "1999-06-22", Arrays.asList(course1));
		try {
			Mockito.when(teacherService.deleteStudent(student, course2)).thenReturn(updatedStudent);
			Student actualStudent = teacherService.deleteStudent(student, course2);
			assertThat(actualStudent).isEqualTo(updatedStudent);
			verify(studentRepository, times(1)).save(actualStudent);
		} catch (Exception e) {
			assertThat(e.getMessage()).isEqualTo(null);
		}

	}

}

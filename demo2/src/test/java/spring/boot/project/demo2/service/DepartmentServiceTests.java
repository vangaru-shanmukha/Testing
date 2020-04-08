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

import spring.boot.project.demo2.dao.DepartmentRepository;
import spring.boot.project.demo2.entity.Department;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTests {

	@Mock
	private DepartmentRepository departmentRepository;
	
	@InjectMocks
	private DepartmentServiceImpl departmentService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindAll() {
		Department department = new Department(1,"Computer Science");
		doReturn(Arrays.asList(department)).when(departmentRepository).findAll();
		List<Department> actualDepartments = departmentService.findAll();
		assertThat(actualDepartments).isEqualTo(Arrays.asList(department));
	}
	
	@Test
	public void testFindById() {
		Department department = new Department(1,"Computer Science");
		Mockito.when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
		Department actualDepartment = departmentService.findById(department.getId());
		assertThat(actualDepartment).isEqualTo(department);
	}
	
	@Test
	public void testDelete() {
		Department department = new Department(1,"Computer Science");
		departmentService.deleteById(department.getId());
		verify(departmentRepository,times(1)).deleteById(department.getId());
	}
	
	@Test
	public void testSave() {
		Department department = new Department(1,"Computer Science");
		departmentService.save(department);
		verify(departmentRepository,times(1)).save(department);
	}
}

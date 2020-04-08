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

import spring.boot.project.demo2.dao.AdminRepository;
import spring.boot.project.demo2.entity.Address;
import spring.boot.project.demo2.entity.Admin;
import spring.boot.project.demo2.entity.Department;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTests {

	@Mock
	private AdminRepository adminRepository;

	@InjectMocks
	private AdminServiceImpl adminService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1,"Computer Science");
		Admin admin = new Admin("vangarushanmukha@gmail.com","Vangaru","Shanmukha",
				address,department,"H.No 12-1-449/2","Lalapet","1999-06-22");
		doReturn(Arrays.asList(admin)).when(adminRepository).findAll();
		List<Admin> actualAdmins = adminService.findAll();
		assertThat(actualAdmins).isEqualTo(Arrays.asList(admin));
	}

	@Test
	public void testFindById() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1,"Computer Science");
		Admin admin = new Admin("vangarushanmukha@gmail.com","Vangaru","Shanmukha",
				address,department,"H.No 12-1-449/2","Lalapet","1999-06-22");
		Mockito.when(adminRepository.findById(admin.getEmail())).thenReturn(Optional.of(admin));
		Admin actualAdmin = adminService.findById(admin.getEmail());
		assertThat(actualAdmin).isEqualTo(admin);
	}
	
	@Test
	public void testDelete() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1,"Computer Science");
		Admin admin = new Admin("vangarushanmukha@gmail.com","Vangaru","Shanmukha",
				address,department,"H.No 12-1-449/2","Lalapet","1999-06-22");
		adminService.deleteById(admin.getEmail());
		verify(adminRepository,times(1)).deleteById(admin.getEmail());
	}
	
	@Test
	public void testSave() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Department department = new Department(1,"Computer Science");
		Admin admin = new Admin("vangarushanmukha@gmail.com","Vangaru","Shanmukha",
				address,department,"H.No 12-1-449/2","Lalapet","1999-06-22");
		adminService.save(admin);
		verify(adminRepository,times(1)).save(admin);
	}

}

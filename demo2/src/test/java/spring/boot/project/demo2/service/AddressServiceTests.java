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

import spring.boot.project.demo2.dao.AddressRepository;
import spring.boot.project.demo2.entity.Address;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTests {

	@Mock
	private AddressRepository addressRepository;

	@InjectMocks
	private AddressServiceImpl addressService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAll() {
		Address address1 = new Address(500017, "India", "Hyderabad", "Telangana");
		Address address2 = new Address(500027, "India", "Secunderabad", "Telangana");
		doReturn(Arrays.asList(address1, address2)).when(addressRepository).findAll();
		List<Address> actualAddress = addressService.findAll();
		assertThat(actualAddress).isEqualTo(Arrays.asList(address1, address2));
	}

	@Test
	public void testFindById() {
		Address address = new Address(500017, "India", "Hyderabad", "Telangana");
		Mockito.when(addressRepository.findById(address.getZipCode())).thenReturn(Optional.of(address));
		Address actualAddress = addressService.findById(address.getZipCode());
		assertThat(actualAddress).isEqualTo(address);
	}
	
	@Test
	public void testDelete() {
		Address address = new Address(500027, "India", "Secunderabad", "Telangana");
		addressService.deleteById(address.getZipCode());
		verify(addressRepository,times(1)).deleteById(address.getZipCode());
	}
	
	@Test
	public void testSave() {
		Address address = new Address(500027, "India", "Secunderabad", "Telangana");
		addressService.save(address);
		verify(addressRepository,times(1)).save(address);
	}
	
	
}

package spring.boot.project.demo2.service;

import java.util.List;

import spring.boot.project.demo2.entity.Address;

public interface AddressService {

	public List<Address> findAll();

	public Address findById(int theZipCode);

	public void save(Address theAddress);

	public void deleteById(int theZipCode);
	
}

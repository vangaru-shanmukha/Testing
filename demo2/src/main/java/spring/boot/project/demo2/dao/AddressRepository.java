package spring.boot.project.demo2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.project.demo2.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}

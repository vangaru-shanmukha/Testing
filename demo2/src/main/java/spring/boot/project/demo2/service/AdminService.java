package spring.boot.project.demo2.service;

import java.util.List;

import spring.boot.project.demo2.entity.Admin;

public interface AdminService {

	public List<Admin> findAll();

	public Admin findById(String theEmail);

	public void save(Admin theAdmin);

	public void deleteById(String theEmail);
}

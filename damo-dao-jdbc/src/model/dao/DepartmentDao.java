package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void Insert(Department obj);
	void Update(Department obj);
	void deteleById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}

package dao;

import entities.Employee;
import exceptions.EmployeeAddressException;
import exceptions.EmployeeException;

public interface EmployeeDao {

	public Employee addEmployee(Employee emp) throws EmployeeException;
	
	public Employee findEmployeeById(int empId) throws EmployeeException;
	
	public Employee deleteEmployee(int empId) throws EmployeeException;
	
	public String getEmployeeAddress(int empId) throws EmployeeException, EmployeeAddressException;
	
	public double giveBonusToEmployee(int empId, double bonus) throws EmployeeException;
	
}

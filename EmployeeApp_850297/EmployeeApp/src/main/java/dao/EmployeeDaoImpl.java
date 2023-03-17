package dao;

import entities.Employee;
import exceptions.EmployeeAddressException;
import exceptions.EmployeeException;
import jakarta.persistence.EntityManager;
import utilities.EMUtil;

public class EmployeeDaoImpl  implements EmployeeDao{

	@Override
	public Employee addEmployee(Employee emp) throws EmployeeException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		EntityManager em = EMUtil.provideEntityManger();
		em.getTransaction().begin();
		Employee p = em.merge(emp);
		em.getTransaction().commit();
		return p;
	}

	@Override
	public Employee findEmployeeById(int empId) throws EmployeeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee deleteEmployee(int empId) throws EmployeeException {
		// TODO Auto-generated method stub
				EntityManager em = EMUtil.provideEntityManger();
				em.getTransaction().begin();
				Employee emp = em.find(Employee.class, empId);
				em.getTransaction().commit();
			    if(emp != null) {
			    	em.remove(emp);
			    	return emp;
			    }else {
			    	 throw new EmployeeException("There is no employee with this id :"+empId);
			    }
	}

	@Override
	public String getEmployeeAddress(int empId) throws EmployeeException, EmployeeAddressException {
		// TODO Auto-generated method stub
		EntityManager em = EMUtil.provideEntityManger();
		em.getTransaction().begin();
		Employee emp = em.find(Employee.class, empId);
		em.getTransaction().commit();
	    if(emp != null) {
	    	
	    	return emp.getEmpAddress();
	    }else {
	    	 throw new EmployeeAddressException("There is no employee with this id :"+empId);
	    }
	}

	@Override
	public double giveBonusToEmployee(int empId, double bonus) throws EmployeeException {
		// TODO Auto-generated method stub
		EntityManager em = EMUtil.provideEntityManger();
		em.getTransaction().begin();
		Employee emp = em.find(Employee.class, empId);
		
	    if(emp != null) {
	    	emp.setEmpSalary(emp.getEmpSalary()+bonus);
	    	em.getTransaction().commit();
	    	return emp.getEmpSalary();
	    }else {
	    	 throw new EmployeeException("There is no person with this id :"+empId);
	    }
	}

}

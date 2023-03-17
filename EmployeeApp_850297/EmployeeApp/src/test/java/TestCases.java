import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dao.*;
import entities.*;
import exceptions.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import utilities.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases {

	private static double marks = 0;
	
	static EmployeeDao empDao;
	
	@Test
	@Order(1)
	void basicChecks() throws Exception {
		Assertions.assertAll(() -> {
			if(Class.forName("entities.Employee").getDeclaredFields().length >= 4 && Class.forName("entities.Employee").getDeclaredAnnotationsByType(Entity.class).length == 1)
					marks += 1;
			
			if(EmployeeDao.class.isAssignableFrom(Class.forName("dao.EmployeeDaoImpl"))) {
				empDao = (EmployeeDao) Class.forName("dao.EmployeeDaoImpl").getDeclaredConstructor().newInstance();
			}
			
			EMUtil.class.getDeclaredMethod("provideEntityManager").getReturnType().getName().equals("jakarta.persistence.EntityManager");
			
			marks += 1;
			
		});
	}
	
	@Test
	@Order(2)
	void addEmployee() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
	        Employee emp = new Employee();
	        emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Rahul");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 25000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "MH");
			
	        Employee emp1 = null;
	        if(empDao != null)
	        	emp1 = empDao.addEmployee(emp);
	        
	        if(emp1 == null) 
	        	Assertions.fail("method is returning a null value");
	        
	        if((int)emp1.getClass().getDeclaredMethod("getEmpId").invoke(emp1) == 0)
	        	Assertions.fail("method is returning a Employee object without empId");
	        
	        Employee emp2 = em.find(Employee.class, emp1.getClass().getDeclaredMethod("getEmpId").invoke(emp1));
	        if(emp2 == null || !emp2.getClass().getDeclaredMethod("getEmpName").invoke(emp2).equals(emp.getClass().getDeclaredMethod("getEmpName").invoke(emp))) 
	        	Assertions.fail("method is not inserting the employee defined data into the database correctly");
	        
	        marks += 2;
	        em.close();
		});
	}
	
	@Test
	@Order(3)
	void addEmployeeExceptions() throws Exception {
		Assertions.assertAll(() -> {
			Employee emp = new Employee();
			emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Sunil");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 30000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "GJ");
	        
            Assertions.assertDoesNotThrow(() -> empDao.addEmployee(emp), "Method is throwing exception even when it is saving an object while adding an employee");
            
            Assertions.assertThrows(EmployeeException.class,() -> empDao.addEmployee(null), "method is not throwing Person Exception, even when we are passing null objects while adding an employee");
            
            marks += 1;
		});
	}
	
	@Test
	@Order(4)
	void findEmployeeById() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
	        Employee emp = new Employee();
	        emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Ritik");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 55000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "Delhi");
	        
	        em.getTransaction().begin();
	        emp = em.merge(emp);
	        em.getTransaction().commit();
	        
	        Assertions.assertEquals((int)emp.getClass().getDeclaredMethod("getEmpId").invoke(emp), (int)empDao.findEmployeeById((int)emp.getClass().getDeclaredMethod("getEmpId").invoke(emp)).getClass().getDeclaredMethod("getEmpId").invoke(emp), "method is not finding the Employee by id correctly");
	        
	        marks += 2;
	        em.close();
		});
	}
	
	@Test
	@Order(5)
	void findEmployeeByIdException() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
			Employee emp = new Employee();
			emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Mahesh");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 105000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "UP");
	        
	        em.getTransaction().begin();
	        emp = em.merge(emp);
	        em.getTransaction().commit();
	
	        Employee finalEmployee = emp;
	        Assertions.assertDoesNotThrow(() -> {
	            empDao.findEmployeeById((int)finalEmployee.getClass().getDeclaredMethod("getEmpId").invoke(finalEmployee));
	        },"method is throwing exception even when we are passing the Employee id which is already there in the database while finding by id");
	
	        Assertions.assertThrows(EmployeeException.class,() -> empDao.findEmployeeById(-1) ,"method is not throwing Employee Exception when unknown id is Employee while finding by id");
	        
	        marks += 1;		
	        em.close();
		});
	}
	
	@Test
	@Order(6)
	void deleteEmployee() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
	        Employee emp = new Employee();
	        emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Pratik");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 76000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "Gao");
	        
	        em.getTransaction().begin();
	        emp = em.merge(emp);
	        em.getTransaction().commit();
	        
			Employee emp1 = emp;
			empDao.deleteEmployee((int)emp1.getClass().getDeclaredMethod("getEmpId").invoke(emp1));
			
			em.clear();
			Assertions.assertNull(em.find(Employee.class, emp.getClass().getDeclaredMethod("getEmpId").invoke(emp)), "Methods is not deleting the employee record correctly!");
			
			marks += 3;
			em.close();
		});
	}
	
	@Test
	@Order(7)
	void deleteEmployeeException() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
	        Employee emp = new Employee();
	        emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Pratik");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 76000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "Gao");
	        
	        em.getTransaction().begin();
	        emp = em.merge(emp);
	        em.getTransaction().commit();
			
	        Employee emp1 = emp;
			Assertions.assertDoesNotThrow(()-> {
				empDao.deleteEmployee((int)emp1.getClass().getDeclaredMethod("getEmpId").invoke(emp1));
			}, "Method is throwing exception even employee with empId is present in database");
			
			Assertions.assertThrows(EmployeeException.class, () -> empDao.deleteEmployee(-1), "Method is not throwing employee exception even for employee not present in database");
			
			marks += 1;
			em.close();
		});
	}
	
	@Test
	@Order(8)
	void getEmployeeAddress() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
	        Employee emp = new Employee();
	        emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Sumit");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 64000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "Mumbai");
	        
	        em.getTransaction().begin();
	        emp = em.merge(emp);
	        em.getTransaction().commit();
			
			Assertions.assertEquals(emp.getClass().getDeclaredMethod("getEmpAddress").invoke(emp), empDao.getEmployeeAddress((int)emp.getClass().getDeclaredMethod("getEmpId").invoke(emp)), "Method is not returning employee address for employee present in database");
			
			marks += 2;
			em.close();
		});
	}
	
	@Test
	@Order(9)
	void getEmployeeAddressException() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
	        Employee emp = new Employee();
	        emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Sagar");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 46000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "Haridwar");
	        
	        em.getTransaction().begin();
	        emp = em.merge(emp);
	        em.getTransaction().commit();
			
	        Employee emp1 = emp;
			assertDoesNotThrow(()-> empDao.getEmployeeAddress((int)emp1.getClass().getDeclaredMethod("getEmpId").invoke(emp1)), "Method is throwing exception even employee with empId is present in database");
			
			Assertions.assertThrows(EmployeeException.class, () -> empDao.getEmployeeAddress(-1), "Method is not throwing employee exception even for employee not present in database");
			
			marks += 1;

			Employee emp2 = new Employee();
	        emp2.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Sagar");
	        emp2.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 46000);
	        
	        em.getTransaction().begin();
	        emp2 = em.merge(emp2);
	        em.getTransaction().commit();
			
	        Employee emp3 = emp2;
			assertThrows(EmployeeAddressException.class, ()-> {
				empDao.getEmployeeAddress((int)emp3.getClass().getDeclaredMethod("getEmpId").invoke(emp3));
			}, "Method is not throwing employee address exception even employee address not know");
			
			marks += 1;
			em.close();
		});
	}
	
	@Test
	@Order(10)
	void giveBonusToEmployee() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
	        Employee emp = new Employee();
	        emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Rohit");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 18000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "MP");
	        
	        em.getTransaction().begin();
	        emp = em.merge(emp);
	        em.getTransaction().commit();
			
	        double bonus = 2000;
	        empDao.giveBonusToEmployee((int)emp.getClass().getDeclaredMethod("getEmpId").invoke(emp), bonus);
	        
	        em.clear();
	        Employee emp1 = em.find(Employee.class, emp.getClass().getDeclaredMethod("getEmpId").invoke(emp));
	        Assertions.assertEquals((double)emp.getClass().getDeclaredMethod("getEmpSalary").invoke(emp)+bonus, emp1.getClass().getDeclaredMethod("getEmpSalary").invoke(emp1));
	        
			marks += 3;
			em.close();
		});
	}
	
	@Test
	@Order(11)
	void giveBonusToEmployeeException() throws Exception {
		Assertions.assertAll(() -> {
			EntityManager em = (EntityManager) Class.forName("utilities.EMUtil").getDeclaredMethod("provideEntityManager").invoke(EMUtil.class);
	        Employee emp = new Employee();
	        emp.getClass().getDeclaredMethod("setEmpName", String.class).invoke(emp,"Pratik");
	        emp.getClass().getDeclaredMethod("setEmpSalary", double.class).invoke(emp, 76000);
	        emp.getClass().getDeclaredMethod("setEmpAddress", String.class).invoke(emp, "Gao");
	        
	        em.getTransaction().begin();
	        emp = em.merge(emp);
	        em.getTransaction().commit();
			
	        Employee emp1 = emp;
			Assertions.assertDoesNotThrow(()-> {
				empDao.deleteEmployee((int)emp1.getClass().getDeclaredMethod("getEmpId").invoke(emp1));
			}, "Method is throwing exception even employee with empId is present in database");
			
			Assertions.assertThrows(EmployeeException.class, () -> empDao.giveBonusToEmployee(-1,-1), "Method is not throwing employee exception even for employee not present in database");
			
			marks += 1;
			em.close();
			
		});
	}
	
	@Test
    @Order(12)
    void buildScore(){
        System.out.println("[MARKS] marks is " + marks);
    }
}

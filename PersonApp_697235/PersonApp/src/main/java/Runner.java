import java.util.Scanner;

import dao.PersonDao;
import dao.PersonDaoImpl;
import entities.Person;
import exceptions.PersonException;

public class Runner {

	public static void main(String[] args) {
            
		
		Scanner sc = new Scanner(System.in);
		
		
//		System.out.println("Enter the name of the Person");
//		String name=sc.next();
//		
//		System.out.println("Enter the name of the Salary");
//		Double salary=sc.nextDouble();
//		
//		Person p = new Person();
//		p.setName(name);
//		p.setSalary(salary);
//		
//		PersonDao dao = new PersonDaoImpl();
//		
//		try {
//			System.out.println(dao.addPerson(p));
//		} catch (PersonException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		System.out.println("Get Person by Id"); 
//		int str = sc.nextInt();
//		
//		PersonDao dao = new PersonDaoImpl();
//		
//		try {
//			System.out.println(dao.findPersonById(str));
//		} catch (PersonException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
		
		System.out.println("Delete a person by id"); 
		int str = sc.nextInt();
		
		PersonDao dao = new PersonDaoImpl();
		
		try {
			System.out.println(dao.deletePerson(str));
		} catch (PersonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

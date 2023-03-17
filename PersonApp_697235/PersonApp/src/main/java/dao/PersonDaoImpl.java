package dao;

import entities.Person;
import exceptions.PersonException;
import jakarta.persistence.EntityManager;
import utilities.EMUtil;

public class PersonDaoImpl implements PersonDao {

	@Override
	public Person addPerson(Person person) throws PersonException {
		// TODO Auto-generated method stub
		EntityManager em = EMUtil.provideEntityManger();
		em.getTransaction().begin();
		Person p = em.merge(person);
		em.getTransaction().commit();
		return p;
	}

	@Override
	public Person findPersonById(int id) throws PersonException {
		// TODO Auto-generated method stub
		EntityManager em = EMUtil.provideEntityManger();
		em.getTransaction().begin();
		Person p = em.find(Person.class, id);
		em.getTransaction().commit();
	    if(p != null) {
	    	 return p;
	    }else {
	    	 throw new PersonException("There is no person with this id :"+id);
	    }
	}

	@Override
	public Person deletePerson(int id) throws PersonException {
		// TODO Auto-generated method stub
		EntityManager em = EMUtil.provideEntityManger();
		em.getTransaction().begin();
		Person p = em.find(Person.class, id);
		em.getTransaction().commit();
	    if(p != null) {
	    	em.remove(p);
	    	return p;
	    }else {
	    	 throw new PersonException("There is no person with this id :"+id);
	    }
	}

}

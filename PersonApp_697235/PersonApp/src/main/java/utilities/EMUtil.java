package utilities;

import org.hibernate.event.spi.PersistContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMUtil {
    
	
	
	   private static EntityManagerFactory emf;
	   
	   static {
		    emf = Persistence.createEntityManagerFactory("personUnit");
	   }
	   
	   public static EntityManager provideEntityManger() {
		      return emf.createEntityManager();
	   }
}

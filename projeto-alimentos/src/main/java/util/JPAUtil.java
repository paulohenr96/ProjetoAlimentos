package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory factory=null;
	
	
	static {
			if (factory==null) {
				System.out.println("Factory JPAUTIL ESTATICO");
				factory= Persistence.createEntityManagerFactory("projeto-alimentos");
			}
	
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}

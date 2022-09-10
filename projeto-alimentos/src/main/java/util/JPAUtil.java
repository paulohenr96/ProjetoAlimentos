package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory factory=null;
	
	
	static {
		try {
			if (factory==null) {
				factory= Persistence.createEntityManagerFactory("projeto-alimentos");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}

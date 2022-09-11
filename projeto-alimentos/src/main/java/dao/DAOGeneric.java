package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.ModelUsuario;
import util.JPAUtil;

public class DAOGeneric <E>{

	public void salvar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
		
		entityManager.close();
	
	}
	
	public List<E> consultarTodos (Class<E> e){
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> list =entityManager.createQuery("from "+e.getCanonicalName()).getResultList();
		return list;
	}
	

}

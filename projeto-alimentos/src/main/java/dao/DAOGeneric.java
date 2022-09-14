package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.ModelAlimento;
import model.ModelUsuario;
import util.JPAUtil;

public class DAOGeneric<E> {

	public void salvar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();

		entityManager.close();

	}
	public ModelUsuario autentificar(ModelUsuario m) {
		
		if (contarLogin(m)==0) {
			return null;
		}
		
		EntityManager manager = JPAUtil.getEntityManager();
		
		manager.getTransaction().begin();
		
		return (ModelUsuario) manager
				.createQuery("from ModelUsuario where login=:login and senha=:senha")
				.setParameter("login", m.getLogin())
				.setParameter("senha", m.getSenha())
				.getSingleResult();
	}
	public void merge(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(entidade);
		transaction.commit();
		entityManager.close();

	}

	public List<E> consultarTodos(Class<E> e) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> list = entityManager.createQuery("from " + e.getCanonicalName()).getResultList();
		transaction.commit();
		entityManager.close();
		return list;
	}

	public List<E> consultarTodosPaginado(Class<E> e, int porPagina, int paginaAtual) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		int offset = porPagina * (paginaAtual - 1);
		int ultimoResultado = porPagina;
		transaction.begin();
		List<E> list = entityManager.createQuery("from " + e.getCanonicalName()).setFirstResult(offset)
				.setMaxResults(ultimoResultado).getResultList();
		transaction.commit();
		entityManager.close();
		return list;
	}

	public List<E> consultarNomePaginado(Class<E> e, String nome, int porPagina, int paginaAtual) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		int offset = porPagina * (paginaAtual - 1);
		int ultimoResultado = porPagina;
		transaction.begin();
		List<E> list = entityManager.createQuery("from " + e.getCanonicalName() + " where upper(nome)=upper(:name) ")
				.setParameter("name", nome).setFirstResult(offset).setMaxResults(ultimoResultado).getResultList();
		transaction.commit();
		entityManager.close();
		return list;
	}

	public Long contarTotal(Class<E> e) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Long total = (Long) entityManager.createQuery("select count(1) from " + e.getCanonicalName()).getSingleResult();
		transaction.commit();
		entityManager.close();
		return total;
	}

	public void deletarPorId(Class<E> e, Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.createQuery("delete from " + e.getCanonicalName() + " where id =" + id).executeUpdate();
		transaction.commit();
		entityManager.close();

	}

	public Long contarTotal(Class<E> e, String nome) {
		// TODO Auto-generated method stub
		EntityManager entityManager = JPAUtil.getEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Long total = (Long) entityManager.createQuery("select count(1) from " + e.getCanonicalName()+" where upper(nome)=upper(:name)").setParameter("name", nome).getSingleResult();
		transaction.commit();
		entityManager.close();
		return total;
	}
	public Long contarLogin(ModelUsuario m) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Long singleResult =(Long) manager.createQuery("select count(1) from "+m.getClass().getCanonicalName() +" where upper(login)=upper('"+m.getLogin()+"') and upper(senha)=upper('"+m.getSenha()+"')")
				.getSingleResult();
		
		
		manager.getTransaction().commit();
		manager.close();
		return singleResult;
	}
	public E buscarUsandoID(Long id,Class<E> e) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E find = entityManager.find(e, id);
		transaction.commit();
		return find;
	}

}

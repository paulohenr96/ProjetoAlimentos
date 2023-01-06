package dao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import model.ModelAlimento;
import model.ModelAlimentoConsumido;
import model.ModelAlimentoRefeicao;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;
import model.ModelUsuario;
import util.JPAUtil;

public class DAOGeneric<E> implements Serializable {

//	Método utilizado para saber se tem refeições com o alimento cadastrado nela. Ele é utilizado antes
//	de editar um alimento.
	

	public void salvar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();

		entityManager.close();

	}

	public E retornaEntidade(String sql) {
		try {
			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();

			transaction.begin();
			E find = (E) entityManager.createQuery(sql).getSingleResult();
			transaction.commit();
			entityManager.close();
			return find;
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			return null;

		}
	}

	

	public E merge(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E merge = entityManager.merge(entidade);
		transaction.commit();
		entityManager.close();
		return merge;

	}

	public E consultarPorId(Class<E> e, Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		E find = entityManager.find(e, id);
		transaction.commit();
		entityManager.close();
		return find;
	}

	public List<E> retornaListaEntidades(String sql) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		List<E> list = entityManager.createQuery(sql).getResultList();
		transaction.commit();
		entityManager.close();
		return list;
	}

	public List retornaListaEntidadesPaginadas(String sql, int offset, int porPagina) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		List<E> list = entityManager.createQuery(sql).setFirstResult(offset).setMaxResults(porPagina).getResultList();
		transaction.commit();
		entityManager.close();
		return list;
	}



	public Long retornaLong(String sql) {

		EntityManager entityManager = JPAUtil.getEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Long total = (Long) entityManager.createQuery(sql).getSingleResult();
		transaction.commit();
		entityManager.close();
		return total;
	}

	public void deletarPorId(Class<E> e, Long id) {
		String sql="delete from " + e.getCanonicalName() + " where id =" + id;
		realizarUpdate(sql);
	}

	public void realizarUpdate(String sql) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.createQuery(sql).executeUpdate();
		transaction.commit();
		entityManager.close();
	}

	
	// TODO Auto-generated method stub

}

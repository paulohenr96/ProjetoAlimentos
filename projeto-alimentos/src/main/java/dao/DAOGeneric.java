package dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
	private EntityManager entityManager = JPAUtil.getEntityManager();

	public void salvar(E entidade) {
	
		getTransaction().begin();
		getEntityManager().persist(entidade);
		
		getTransaction().commit();

	

	}

	public E retornaEntidade(String sql) {
		try {
			E find = (E) getEntityManager().createQuery(sql).getSingleResult();
			return find;
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			return null;

		}
	}

	public E merge(E entidade) {

		EntityTransaction transaction = getEntityManager().getTransaction();
		if (!getEntityManager().getTransaction().isActive()) {
			getEntityManager().getTransaction().begin();

		}
		E merge = getEntityManager().merge(entidade);
		transaction.commit();

		return merge;

	}

	public E consultarPorId(Class<E> e, Long id) {

		flush();
		E find = getEntityManager().find(e, id);
		return find;
	}

	public List<E> retornaListaEntidades(String sql) {
//		verificaEntityManager();
		
		List<E> list = (List<E>) getEntityManager().createNativeQuery(sql).getResultList();

		return list;
	}

	public List<E> retornaListaEntidadesHql(String hql) {
//		verificaEntityManager();
//	

		List<E> list = (List<E>) getEntityManager().createQuery(hql).getResultList();

		return list;
	}

	public List retornaListaEntidadesINNERJOIN(String sql) {
//		verificaEntityManager();

		List<E> list = getEntityManager().createNativeQuery(sql).getResultList();

		return list;
	}

	public List retornaListaEntidadesPaginadas(String sql, int offset, int porPagina) {

		List<E> list = getEntityManager().createQuery(sql).setFirstResult(offset).setMaxResults(porPagina)
				.getResultList();


		return list;
	}

	public Long retornaLong(String sql) {

		Long total = 0L;
		getEntityManager().clear();

		total = ((BigInteger) getEntityManager().createNativeQuery(sql).getSingleResult()).longValue();

		return total;
	}

	public Long retornaLongHql(String hql) {

		Long total = 0L;
		total = ((Long) getEntityManager().createQuery(hql).getSingleResult());


		return total;
	}

	public void deletarPorId(Class<E> e, Long id) {
		String sql = "delete from " + e.getSimpleName() + " where id =" + id;
		realizarUpdate(sql);

	}

	public void realizarUpdate(String sql) {

		EntityTransaction transaction = getEntityManager().getTransaction();
		if (!transaction.isActive()) {
			transaction.begin();

		}

		getEntityManager().createQuery(sql).executeUpdate();
		transaction.commit();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public EntityTransaction getTransaction() {
		return getEntityManager().getTransaction();
	}

	public void flush() {
		// TODO Auto-generated method stub
		if (!getTransaction().isActive()) {
			getTransaction().begin();
			
		}else {
			getTransaction().commit();
			flush();
		}
		getEntityManager().flush();
		entityManager.clear();
		getTransaction().commit();

	}

}

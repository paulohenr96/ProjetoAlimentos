package dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import model.ModelAlimento;
import model.ModelAlimentoConsumido;
import model.ModelAlimentoRefeicao;
import model.ModelConsumidoDia;
import model.ModelRefeicao;
import model.ModelUsuario;
import util.JPAUtil;

public class DAOGeneric<E> {

	public List<E> consultarAlimentosRefeicao(Long idRefeicao){
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> list = entityManager.createQuery("from " + ModelAlimentoRefeicao.class.getCanonicalName()+" where refeicao_id="+idRefeicao).getResultList();
		transaction.commit();
		entityManager.close();
		return list;
	}

	public void salvar(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();

		entityManager.close();

	}
	public ModelUsuario autentificar(ModelUsuario m) {
		
		ModelUsuario user=new ModelUsuario();
		try {
			EntityManager manager = JPAUtil.getEntityManager();
			
			manager.getTransaction().begin();
			
			user=	(ModelUsuario) manager
					.createQuery("from ModelUsuario where login=:login and senha=:senha")
					.setParameter("login", m.getLogin())
					.setParameter("senha", m.getSenha())
					.getSingleResult();	
		}catch(NoResultException e) {
			return null;
		}
		return user;
		
	}
	public void merge(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(entidade);
		transaction.commit();
		entityManager.close();

	}
	public E consultarPorId(Class<E> e,Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		E find = entityManager.find(e, id);
		transaction.commit();
		entityManager.close();
		return find;
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
	public List<E> consultarTodos(Class<E> e,Long userLogado) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> list = entityManager.createQuery("from " + e.getCanonicalName()+" where usuario_id="+userLogado).getResultList();
		transaction.commit();
		entityManager.close();
		return list;
	}
	
	public List<E> consultarTodosRefeicao(Class<E> e,Long userLogado) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> list = entityManager.createQuery("from " + e.getCanonicalName()+" where idUserLogado="+userLogado).getResultList();
		transaction.commit();
		entityManager.close();
		return list;
	}
	public List<E> consultarTodosPaginadoMacros(int porPagina, int paginaAtual,Long userLogado,String ordem,String ordenar) {
		try {
			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			int offset = porPagina * (paginaAtual - 1);
			int ultimoResultado = porPagina;
			transaction.begin();
			List<E> list = entityManager.createQuery("from " + ModelConsumidoDia.class.getCanonicalName() +" where usuario_id="+userLogado +" order by "+ordem+" "+ordenar).setFirstResult(offset)
					.setMaxResults(ultimoResultado).getResultList();
			transaction.commit();
			entityManager.close();
			return list;
		}catch (NoResultException e) {
			return null;
		}
	
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
	
	public List<E> consultarTodosPaginadoAlimentos(int porPagina, int paginaAtual,Long macrosId) {
		try {
			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			int offset = porPagina * (paginaAtual - 1);
			int ultimoResultado = porPagina;
			transaction.begin();
			List<E> list = entityManager.createQuery("from " + ModelAlimentoConsumido.class.getCanonicalName() +" where macros_id="+macrosId ).setFirstResult(offset)
					.setMaxResults(ultimoResultado).getResultList();
			transaction.commit();
			entityManager.close();
			return list;
		}catch (NoResultException e) {
			return null;
		}
	
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
	public Long contarTotalMacros(Long user) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Long total = (Long) entityManager.createQuery("select count(1) from " + ModelConsumidoDia.class.getCanonicalName()+" where usuario_id="+user).getSingleResult();
		transaction.commit();
		entityManager.close();
		return total;
	}
	public Long contarTodosAlimentosConsumidos(Class<E> e,Long macroId) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Long total = (Long) entityManager.createQuery("select count(1) from " + e.getCanonicalName() +" where macros_id="+macroId).getSingleResult();
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
	public void deletarAlimentoConsumidoPorId(Class<E> e,Long idMacros) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.createQuery("delete from "+e.getCanonicalName()+" where  macros_id =" + idMacros).executeUpdate();
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
		Long singleResult =(Long) manager.createQuery("select count(1) from "+m.getClass().getCanonicalName() +" where upper(login)=upper(:login)")
				.setParameter("login",m.getLogin())
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
	
	public ModelConsumidoDia consultarConsumoDia(Date date, Long idLogado) {
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String format = dateFormat.format(date);
			
			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			System.out.println(format);
			ModelConsumidoDia m = (ModelConsumidoDia) entityManager.createQuery("from ModelConsumidoDia where data=:data and usuario_id=:id")
					.setParameter("id",idLogado)
					.setParameter("data", date)
					.getSingleResult();
			transaction.commit();
			entityManager.close();
			return m;

		}catch (NoResultException e) {
			System.out.println("sem resultado:"+e.getMessage());
			return null;
		} 
		catch (NonUniqueResultException e) {
			System.out.println("Multiplo resultados :"+e.getMessage());

			return null;
		}

	}

	public void removerAlimentoRefeicao(Long idrefeicao) {
		// TODO Auto-generated method stub
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.createQuery("delete from "+ModelAlimentoRefeicao.class.getCanonicalName()+" where  refeicao_id =" + idrefeicao).executeUpdate();
		transaction.commit();
		entityManager.close();

	}

}

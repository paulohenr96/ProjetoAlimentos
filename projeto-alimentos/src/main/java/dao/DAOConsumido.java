package dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import model.ModelAlimentoConsumido;
import model.ModelConsumidoDia;
import model.ModelRefeicao;
import util.JPAUtil;

public class DAOConsumido extends DAOGeneric<ModelConsumidoDia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2893438841213588426L;

	public Long contarTotalMacros(Long user) {
		String sql = "select count(1) from " + ModelConsumidoDia.class.getCanonicalName() + " where usuario_id=" + user;

		return super.retornaLong(sql);
	}

	public Long contarTodosAlimentosConsumidos(Long macroId) {
		String sql = "select count(1) from " + ModelAlimentoConsumido.class.getCanonicalName() + " where macros_id="
				+ macroId;
		return super.retornaLong(sql);
	}

	public void deletarAlimentoConsumidoPorId(Long idMacros) {

		String sql = "delete from " + ModelAlimentoConsumido.class.getCanonicalName() + " where  macros_id ="
				+ idMacros;
		super.realizarUpdate(sql);

	}

	public List mediaMacros(Long userId) {
		// TODO Auto-generated method stub
		String sql = "select avg(calorias),avg(proteinas),avg(carboidrato),avg(gordura) from "
				+ ModelConsumidoDia.class.getCanonicalName() + " where usuario_id =" + userId;

		return retornaListaEntidades(sql);
	}

	public List consultarTodos(Long userLogado) {
		String sql = "from " + ModelConsumidoDia.class.getCanonicalName() + " where usuario_id=" + userLogado;

		return retornaListaEntidades(sql);
	}

	public List<ModelRefeicao> consultarRefsMacros(int porPagina, int paginaAtual, Long macrosId) {
		try {
			String sql = "from " + ModelRefeicao.class.getCanonicalName() + " where macros_id=" + macrosId;

			int offset = porPagina * (paginaAtual - 1);

			return (List<ModelRefeicao>) retornaListaEntidadesPaginadas(sql, offset, porPagina);
		} catch (NoResultException e) {
			return null;
		}
	}

	public List consultarTodosPaginadoMacros(int porPagina, int paginaAtual, Long userLogado, String ordem,
			String ordenar) {
		try {
			String sql = "from " + ModelConsumidoDia.class.getCanonicalName() + " where usuario_id=" + userLogado
					+ " order by " + ordem + " " + ordenar;
			int offset = porPagina * (paginaAtual - 1);
			return retornaListaEntidadesPaginadas(sql, offset, porPagina);
		} catch (NoResultException e) {
			return null;
		}

	}

	public ModelConsumidoDia consultarConsumoDia(Date date, Long idLogado) {
		try {

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String format = dateFormat.format(date);

			EntityManager entityManager = JPAUtil.getEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			transaction.begin();
			System.out.println(format);
			ModelConsumidoDia m = (ModelConsumidoDia) entityManager
					.createQuery("from ModelConsumidoDia where data=:data and usuario_id=:id")
					.setParameter("id", idLogado).setParameter("data", date).getSingleResult();
			transaction.commit();
			entityManager.close();
			return m;

		} catch (NoResultException e) {
			System.out.println("sem resultado:" + e.getMessage());

			return null;
		} catch (NonUniqueResultException e) {
			System.out.println("Multiplo resultados :" + e.getMessage());

			return null;
		}

	}

	public List consultarTodosPaginadoAlimentos(int porPagina, int paginaAtual, Long macrosId) {
		try {
			String sql = "from " + ModelAlimentoConsumido.class.getCanonicalName() + " where macros_id=" + macrosId;
			int offset = porPagina * (paginaAtual - 1);
			return retornaListaEntidadesPaginadas(sql, offset, porPagina);
		} catch (NoResultException e) {
			return null;
		}

	}

}

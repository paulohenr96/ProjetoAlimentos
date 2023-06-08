package dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import model.ModelAlimentoConsumido;
import model.ModelConsumidoDia;
import model.ModelRefeicao;
import model.ModelRefeicaoConsumida;
import util.JPAUtil;

public class DAOConsumido extends DAOGeneric<ModelConsumidoDia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2893438841213588426L;

	public Long contarTotalMacros(Long user) {
		String hql = "select count(u) from "+ModelConsumidoDia.class.getCanonicalName()+" u where u.usuario.id=" + user;

		return super.retornaLongHql(hql);
	}
	
	public Long contarTodosAlimentosConsumidos(Long macroId) {
		String sql = "select count(1) from " + ModelAlimentoConsumido.class.getSimpleName().toLowerCase() + " where macros_id="
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
		String sql = "select avg(calorias) as media_calorias,"
				+ " avg(proteinas) as media_proteinas,"
				+ " avg(carboidrato) as media_carboidrato,"
				+ " avg(gordura) as media_gordura from "
				+ ModelConsumidoDia.class.getSimpleName().toLowerCase()+""
						+ " where usuario_id =" + userId;


		return retornaListaEntidades(sql);
	}

	public List consultarTodos(Long userLogado) {
		String hql = "from ModelConsumidoDia u where u.usuario.id=" + userLogado;
		
		Long valor=contarTotalMacros(userLogado);
		
		if (valor.intValue()==0) {
			return null;
		}
		
		
		
		return retornaListaEntidadesHql(hql);
	}

	public List<ModelRefeicaoConsumida> consultarRefsMacros(int porPagina, int paginaAtual, Long macrosId) {
			String sql = "from " + ModelRefeicaoConsumida.class.getSimpleName()+ " m where m.macros.id=" + macrosId;

			int offset = porPagina * (paginaAtual - 1);

//			int valor=retornaLongHql("select count(u) from ModelRefeicao u WHERE u.macros.id="+macrosId).intValue();
//			
//			if (valor==0) {
//				return null;
//			}
			List<ModelRefeicaoConsumida> listaRefeicaoConsumida=(List<ModelRefeicaoConsumida>) retornaListaEntidadesPaginadas(sql, offset, porPagina);
			
			return listaRefeicaoConsumida;
		
	}

	public List consultarTodosPaginadoMacros(int porPagina, int paginaAtual, Long userLogado, String ordem,
			String ordenar) {

			
		
			String sql = "from ModelConsumidoDia u where u.usuario.id=" + userLogado
					+ " order by " + ordem + " " + ordenar;
			int offset = porPagina * (paginaAtual - 1);
			int valor= retornaLongHql("select count(m) from ModelConsumidoDia m JOIN m.usuario us WHERE us.id = " + userLogado).intValue();
			if (valor==0) {
				return null;
			}
			
			
			return retornaListaEntidadesPaginadas(sql, offset, porPagina);
		

	}

	public ModelConsumidoDia consultarConsumoDia(Date date, Long idLogado) {
		try {
			String sql="from ModelConsumidoDia where data='"+date+"' and usuario_id="+idLogado;
			
			return retornaEntidade(sql);

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
	public void addAlimento(ModelAlimentoConsumido ali,ModelConsumidoDia macros) {
	
	}
	
	

}

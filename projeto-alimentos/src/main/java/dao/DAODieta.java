package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import model.ModelDieta;
import model.ModelRefeicao;
import util.JPAUtil;

public class DAODieta extends DAOGeneric<ModelDieta> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1015982789929381574L;

	public Long contarDietas(Long idUserLogado) {
		// TODO Auto-generated method stub
		String hql = "select count(u) from " + ModelDieta.class.getSimpleName() + " u where u.idUsuario=" + idUserLogado;

		return retornaLongHql(hql);
	}
	public Long contarRefeicoesDieta(Long idDieta) {
		String hql="SELECT COUNT(u) FROM "+ModelRefeicao.class.getCanonicalName() +" u WHERE u.dieta.id="+idDieta;
		
		return retornaLongHql(hql);
		
		
	}

	public List consultarTodasDietasPorIdPaginado(Long idUserLogado, int paginaAtual, int porPagina) {
		// TODO Auto-generated method stub
		String sql = "from " + ModelDieta.class.getCanonicalName() + " where idUsuario=" + idUserLogado;
		int offset = porPagina * (paginaAtual - 1);

		return retornaListaEntidadesPaginadas(sql, offset, porPagina);
	}

	public List consultarTodasDietasPorId(Long idUserLogado) {
		// TODO Auto-generated method stub
		try {
			String sql = "from " + ModelDieta.class.getCanonicalName() + " where idusuario=" + idUserLogado;

			return retornaListaEntidades(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}

	}

	public ModelDieta consultarDietaPorId(Long id, Long userLogado) {
		String sql = "from " + ModelDieta.class.getCanonicalName() + " where id=" + id + " and idusuario=" + userLogado;

		return retornaEntidade(sql);
	}

	public void removerTodasRefeicaoDieta(Long id) {
		// TODO Auto-generated method stub
		String sql = "delete from " + ModelRefeicao.class.getCanonicalName() + " where dieta_id=" + id;
		realizarUpdate(sql);
	}

	public List todasRefsDieta(Long id) {
		String sql = "from " + ModelRefeicao.class.getCanonicalName() + " u where u.dieta.id=" + id + " order by horario";
		return retornaListaEntidadesHql(sql);
	}

	public ModelDieta consultarPorId(Long id) {
		// TODO Auto-generated method stub
		
		return consultarPorId(ModelDieta.class, id);
	}


}

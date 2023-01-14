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
		String sql = "select count(1) from " + ModelDieta.class.getCanonicalName() + " where idUsuario=" + idUserLogado;

		return retornaLong(sql);
	}
	public Long contarRefeicoesDieta(Long idDieta) {
		String sql="SELECT COUNT(1) FROM "+ModelRefeicao.class.getCanonicalName() +" WHERE dieta_id="+idDieta;
		
		return retornaLong(sql);
		
		
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
		String sql = "from " + ModelRefeicao.class.getCanonicalName() + " where dieta_id=" + id + " order by horario";
		return retornaListaEntidades(sql);
	}

	public ModelDieta consultarPorId(Long id) {
		// TODO Auto-generated method stub
		
		return consultarPorId(ModelDieta.class, id);
	}

}

package dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.ModelAlimentoConsumido;
import model.ModelAlimentoRefeicao;
import model.ModelRefeicao;
import util.JPAUtil;

public class DAORefeicao extends DAOGeneric<ModelRefeicao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2701431601624887015L;
	private String nome = "";

	public Long contarTotalRefeicoes(Long idUserLogado) {
		String sql = "select count(1) from " + ModelRefeicao.class.getSimpleName().toLowerCase() + " where dieta_id=null and idUserLogado="
				+ idUserLogado + condicaoNome();

		return retornaLong(sql);
	}

	public Long contarRefeicoesComOAlimento(Long idAlimento) {
		String sql = "select count(1) from " + ModelAlimentoRefeicao.class.getSimpleName() + " where alimento_id=" + idAlimento;

		return retornaLong(sql);
	}
	public ModelRefeicao consultarPorId(Long id) {
		return consultarPorId(ModelRefeicao.class,id);
	}
	public List refeicoesAlimentosInnerJoin(Long idAlimento) {
		String ref="ModelRefeicao";
		String ali="ModelAlimentoRefeicao";
		String sql = ""
				+ "SELECT "
				+ " "+ref+".nome as refeicao,"
				+ " count("+ref+".nome) as total"
				+ " FROM "+ali+" "
				+ " INNER JOIN  "+ref+" ON "+ref+".id ="+ali+".refeicao_id "
				+ " where "+ali+".alimento_id = "+idAlimento+" GROUP BY refeicao";
		return retornaListaEntidadesINNERJOIN(sql);
	}
	
	public List refeicoesContemAlimento(Long idAlimento) {
		
		
		
		String sql = "from "+ModelAlimentoRefeicao.class.getCanonicalName()+" where alimento_id="+idAlimento;
		
		Long valor=retornaLong("select count(1) "+sql);
		
		if (valor.intValue()==0) {
			return null;
		}
		
		return retornaListaEntidades(sql);
		
	}

	public Long contarTotalRefsDieta(Long idDieta) {
		String sql="select count(1) from " + ModelRefeicao.class.getCanonicalName() + " where dieta_id="+idDieta;
		return retornaLong(sql);
	}

	public List consultarAlimentosRefeicao(Long idRefeicao) {
		
		
		String hql = "from " + ModelAlimentoRefeicao.class.getCanonicalName() + " u where u.refeicao.id=" + idRefeicao;
		Long total = contarAlimentosRefeicao(idRefeicao);
		if (total.intValue()==0) {
			return null;
		}
		getEntityManager().clear();
		return retornaListaEntidadesHql(hql);
	}

	public void deletarRefeicoesConsumidas(Long id) {
		// TODO Auto-generated method stub
		String sql = "delete from " + ModelRefeicao.class.getCanonicalName() + " where macros_id=" + id;
		realizarUpdate(sql);
	}



	public List consultarTodosRefeicaoPaginado(int paginaAtual, int porPagina, Long userLogado) {
		String sql = "from " + ModelRefeicao.class.getCanonicalName()
				+ " where dieta_id=null and idUserLogado=" + userLogado + condicaoNome()
				+ " ORDER BY id DESC";

		int offset = porPagina * (paginaAtual - 1);

		return retornaListaEntidadesPaginadas(sql, offset, porPagina);
	}
	
	public Long contarAlimentosRefeicao(Long id) {
		
		String hql="select count(u) from ModelAlimentoRefeicao u where u.refeicao.id="+id;
		return retornaLongHql(hql);
	}
	
	public Long contarTotalRefeicoesConsumidas(Long idUserLogado, Long macros) {
		String sql = "select count(1) from " + ModelRefeicao.class.getSimpleName().toLowerCase() + " where idUserLogado="
				+ idUserLogado + " and macros_id=" + macros;

		return retornaLong(sql);
	}

	public void setNome(String nome) {

		this.nome = nome;
	}

	public String getNome() {

		return this.nome;
	}

	public String condicaoNome() {
		String condicao = "";
		if (getNome() != null && !getNome().equals("")) {
			condicao = " AND upper(nome) like upper('" + "%" + getNome() + "%" + "')";
			setNome("");
		}
		return condicao;
	}

	public void removerAlimentoRefeicao(Long idrefeicao) {
		String sql = "delete from " + ModelAlimentoRefeicao.class.getCanonicalName() + " where  refeicao_id ="
				+ idrefeicao;
		// TODO Auto-generated method stub

		realizarUpdate(sql);

	}

	public void deletarPorId(Long idRefeicao) {
		// TODO Auto-generated method stub
		
		deletarPorId(ModelRefeicao.class, idRefeicao);
		
	}
}

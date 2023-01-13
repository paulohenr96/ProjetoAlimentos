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
		String sql = "select count(1) from " + ModelRefeicao.class.getCanonicalName() + " where dieta_id=null and macros_id=null and idUserLogado="
				+ idUserLogado + condicaoNome();

		return retornaLong(sql);
	}

	public Long contarRefeicoesComOAlimento(Long idAlimento) {
		String sql = "select count(1) from " + ModelAlimentoRefeicao.class.getCanonicalName() + " where alimento_id=" + idAlimento;

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
		
		return retornaListaEntidades(sql);
		
	}

	public Long contarTotalRefsDieta(Long idDieta) {
		// TODO Auto-generated method stub
		EntityManager entityManager = JPAUtil.getEntityManager();

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Long total = (Long) entityManager
				.createQuery("select count(1) from " + ModelRefeicao.class.getCanonicalName() + " where dieta_id=:id")
				.setParameter("id", idDieta).getSingleResult();
		transaction.commit();
		entityManager.close();
		return total;
	}

	public List consultarAlimentosRefeicao(Long idRefeicao) {
		String sql = "from " + ModelAlimentoRefeicao.class.getCanonicalName() + " where refeicao_id=" + idRefeicao;

		return retornaListaEntidades(sql);
	}

	public void deletarRefeicoesConsumidas(Long id) {
		// TODO Auto-generated method stub
		String sql = "delete from " + ModelRefeicao.class.getCanonicalName() + " where macros_id=" + id;
		realizarUpdate(sql);
	}

//	public Long contarTotalRefeicoes(String nome, Long userLogado) {
//		// TODO Auto-generated method stub
//		
//		String sql="select count(1) from " + ModelRefeicao.class.getCanonicalName() + " where idUserLogado="
//				+ userLogado + condicaoNome()+")";
//		
//		
//		return retornaLong(sql);
//
//	}
//	

	public List consultarTodosRefeicaoPaginado(int paginaAtual, int porPagina, Long userLogado) {
		String sql = "from " + ModelRefeicao.class.getCanonicalName()
				+ " where dieta_id=null and macros_id=null and idUserLogado=" + userLogado + condicaoNome()
				+ " ORDER BY id DESC";

		int offset = porPagina * (paginaAtual - 1);

		return retornaListaEntidadesPaginadas(sql, offset, porPagina);
	}

	public Long contarTotalRefeicoesConsumidas(Long idUserLogado, Long macros) {
		String sql = "select count(1) from " + ModelRefeicao.class.getCanonicalName() + " where idUserLogado="
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
}

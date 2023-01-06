package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.ModelAlimento;
import util.JPAUtil;

public class DAOAlimento extends DAOGeneric<ModelAlimento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3858756771413149722L;
	private String nome = "";

	public Long contarTotalAlimentos(Long user) {

		String sql = "select count(1) from " + ModelAlimento.class.getCanonicalName() + " where iduser=" + user+condicaoNome();

		return retornaLong(sql);
	}



	public List consultarTodosPaginado(Long idUser, int porPagina, int paginaAtual) {
		String sql = "from " + ModelAlimento.class.getCanonicalName() + " where iduser=" + idUser + condicaoNome()+ " ORDER BY id DESC";
		int offset = porPagina * (paginaAtual - 1);

		return retornaListaEntidadesPaginadas(sql, offset, porPagina);
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

}

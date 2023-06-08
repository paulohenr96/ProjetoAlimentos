package dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelUsuario;
import util.JPAUtil;

public class DAOUsuario extends DAOGeneric<ModelUsuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5043658394261431363L;

	public Long contarLogin(ModelUsuario m) {
		String sql="select count(1) from " + m.getClass().getSimpleName()
				+ " where upper(login)=upper('" + m.getLogin() + "')";
		
		return retornaLong(sql);
	}
	
	
	public ModelUsuario autentificar(ModelUsuario m) {
		String sql="from ModelUsuario where login='"+m.getLogin()+"' and senha='"+m.getSenha()+"'";
		
		return retornaEntidade(sql);

	}

}

package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ContextoBean;
import dao.DAOUsuario;
import model.ModelUsuario;
import util.Constantes;
import util.Mensagem;

/**
 * Servlet implementation class ServletSenha
 */
@WebServlet("/ServletSenha")
public class ServletSenha extends ContextoBean {
	private static final long serialVersionUID = 1L;
	private DAOUsuario daoUsuario = new DAOUsuario();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletSenha() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (Constantes.EDITAR_LOGIN) {
			ModelUsuario user = super.getUserLogado(request);
			String senhaantiga = request.getParameter("senhaantiga");
			String senhaNova = request.getParameter("senhanova");
			String confirmaSenha = request.getParameter("confirmasenha");

			if (!senhaantiga.equals(user.getSenha())) {
				responderAjax(response, Mensagem.MENSAGEM_SENHA);
			} else if (!senhaNova.equals(confirmaSenha)) {
				responderAjax(response, Mensagem.MENSAGEM_CONFIRMA_SENHA);

			} else {
				user.setSenha(senhaNova);
				setUserLogado(request, daoUsuario.merge(user));
				response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);
			}
		}else {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(Mensagem.PERMISSAO);
		}
		
	}

}

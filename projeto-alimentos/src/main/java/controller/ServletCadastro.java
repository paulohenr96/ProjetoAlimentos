package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOGeneric;
import dao.DAOUsuario;
import model.ModelUsuario;
import util.Mensagem;

/**
 * Servlet implementation class ServletCadastro
 */
@WebServlet("/ServletCadastro")
public class ServletCadastro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOUsuario daoUsuario = new DAOUsuario();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCadastro() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String login = request.getParameter("login");
		ModelUsuario model = new ModelUsuario();
		model.setLogin(login);

		if (daoUsuario.contarLogin(model) > 0) {
			response.getWriter().write(Mensagem.MENSAGEM_ERRO);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String nome = request.getParameter("nome");
		String sobreNome = request.getParameter("sobrenome");
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String confirmaSenha = request.getParameter("confirmasenha");

		ModelUsuario model = new ModelUsuario();
		model.setEmail(email);
		model.setNome(nome);
		model.setSobreNome(sobreNome);
		model.setLogin(login);
		model.setSenha(senha);
		if (daoUsuario.contarLogin(model) > 0) {
			response.getWriter().write(Mensagem.MENSAGEM_ERRO);

		} else {
			daoUsuario.salvar(model);
			response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);

		}

	}

}

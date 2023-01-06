package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ContextoBean;
import dao.DAOGeneric;
import dao.DAOUsuario;
import model.ModelUsuario;
import util.Mensagem;

/**
 * Servlet implementation class ServletUsuario
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends ContextoBean {
	private static final long serialVersionUID = 1L;
	
	private DAOGeneric dao=new DAOGeneric<>();
	private DAOUsuario daoUsuario= new DAOUsuario();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

ModelUsuario user = super.getUserLogado(request);
		
		
		String login=request.getParameter("login");
		String nome=request.getParameter("nome");
		String sobrenome=request.getParameter("sobrenome");
		String email=request.getParameter("email");
		
		ModelUsuario m=new ModelUsuario();
		m.setLogin(login);
		
		if ((daoUsuario.contarLogin(m)==0 && !login.equals(user.getLogin()))) {
			user.setNome(nome);
			user.setLogin(login);
			user.setSobreNome(sobrenome);
			user.setEmail(email);
			request.getSession().setAttribute("user", daoUsuario.merge(user));
			response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);


		}else if (login.equals(user.getLogin()))
		{
			user.setNome(nome);
			user.setLogin(login);
			user.setSobreNome(sobrenome);
			user.setEmail(email);
			request.getSession().setAttribute("user", daoUsuario.merge(user));
			response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);
		}
		else{
			response.getWriter().write(Mensagem.MENSAGEM_ERRO);

		}
		
		
		
		
		
		
	
	}

}

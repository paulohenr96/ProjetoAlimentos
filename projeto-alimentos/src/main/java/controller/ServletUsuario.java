package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOGeneric;
import model.ModelUsuario;

/**
 * Servlet implementation class ServletUsuario
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DAOGeneric dao=new DAOGeneric<>();
	
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	
		System.out.println(request.getParameter("nome"));
		Long id=Long.parseLong(request.getParameter("id"));
		String login=request.getParameter("login");
		String nome=request.getParameter("nome");
		String sobrenome=request.getParameter("sobrenome");
		String email=request.getParameter("email");
		String senha=request.getParameter("senha");
		
		
		ModelUsuario user=new ModelUsuario();
		user.setNome(nome);
		user.setLogin(login);
		user.setId(id);
		user.setSobreNome(sobrenome);
		user.setEmail(email);
		user.setSenha(senha);
		
		
		dao.merge(user);
		request.getSession().setAttribute("user", user);

		request.getRequestDispatcher("principal/perfil.jsp").forward(request, response);
	}

}

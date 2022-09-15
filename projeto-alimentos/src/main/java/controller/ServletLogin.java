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
 * Servlet implementation class ServletLogin
 */
@WebServlet(urlPatterns = { "/ServletLogin", "/principal/ServletLogin" })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOGeneric<ModelUsuario> dao=new DAOGeneric<>();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletLogin() {
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

		String acao = request.getParameter("acao");
		if (acao != null && acao.equals("logout")) {
			System.out.println("logout");
			request.getSession().invalidate();
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");

		System.out.println("Login : " + login);
		System.out.println("Senha : " + senha);
		String url="";
		String msg = "";
		ModelUsuario user=new ModelUsuario();
		
		user.setLogin(login);
		user.setSenha(senha);
		System.out.println("Usuario tentando logar : "+user);
		ModelUsuario modelUsuario = dao.autentificar(user);
		if (login != null && senha != null && !login.isEmpty() && !senha.isEmpty() && modelUsuario!=null ) {
			System.out.println("Logado");
			request.getSession().setAttribute("user", modelUsuario.getLogin());
			request.getSession().setAttribute("IDLogado", modelUsuario.getId());
			System.out.println(request.getContextPath());
			
			
			request.getRequestDispatcher("/principal/paginainicial.jsp").forward(request, response);

		} else {

			msg = "Usuario e senopkpokha incorretos.";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		}
	}

}

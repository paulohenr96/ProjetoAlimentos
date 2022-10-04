package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOGeneric;
import model.ModelConsumidoDia;
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
		String msg = "";
		ModelUsuario user=new ModelUsuario();
		
		user.setLogin(login);
		user.setSenha(senha);
		ModelUsuario modelUsuario = dao.autentificar(user);
		
		String url=request.getParameter("url");
		
		System.out.println(url+" <--- Url para Autentificar");
		if (login != null && senha != null && !login.isEmpty() && !senha.isEmpty() && modelUsuario!=null ) {
			System.out.println("Logado");
			request.getSession().setAttribute("user", modelUsuario.getLogin());
			request.getSession().setAttribute("IDLogado", modelUsuario.getId());
			
			if (url==null || url.equalsIgnoreCase("null")) {
				url="/principal/paginainicial.jsp";
			}
			

			request.getRequestDispatcher(url).forward(request, response);

		} else {

			msg = "Usuario e senopkpokha incorretos.";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		}
	}

}

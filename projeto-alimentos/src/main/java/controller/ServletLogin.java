package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet(urlPatterns = { "/ServletLogin", "/principal/ServletLogin" })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		if (login != null && senha != null && login.equals("admin") && senha.equals("admin")) {
			System.out.println("Admin");
			request.getSession().setAttribute("user", "admin");
			System.out.println(request.getContextPath());
			
			
			request.getRequestDispatcher("/principal/paginainicial.jsp").forward(request, response);

		} else {
			System.out.println("Nao admin.");

			msg = "Usuario e senha incorretos.";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		}
	}

}

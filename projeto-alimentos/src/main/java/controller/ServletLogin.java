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
@WebServlet("/ServletLogin")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		
		
		System.out.println("Login : "+login);
		System.out.println("Senha : "+senha);
	
		
		if (!login.equals("admin") || !senha.equals("admin")) {
			System.out.println("Nao admin.");
			request.getSession().setAttribute("msg", "Usuario e senha incorretos.");
			request.getRequestDispatcher("index.jsp").forward(request, response);

		}
		else {
			System.out.println("Admin");
			request.getSession().setAttribute("user","admin");
			request.getRequestDispatcher("principal/paginainicial.jsp").forward(request, response);
		}
	}

}

package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ContextoBean;
import dao.DAOGeneric;
import dao.DAOUsuario;
import model.ModelConsumidoDia;
import model.ModelUsuario;
import util.JPAUtil;
import util.Mensagem;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet(urlPatterns = { "/ServletLogin", "/principal/ServletLogin", "/ServletLogin?acao=logout" })
public class ServletLogin extends ContextoBean {
	private static final long serialVersionUID = 1L;
	private DAOUsuario daoUsuario;

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
			request.getSession().setAttribute("aviso", Mensagem.LOGOUT);
			request.getRequestDispatcher("logout.jsp").forward(request, response);
			return;
		}
			request.getRequestDispatcher(request.getContextPath()+"/principal/paginainicial.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.requestEncoding(request);
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");

		String msg = "";
		ModelUsuario user = new ModelUsuario();

		user.setLogin(login);
		user.setSenha(senha);
		ModelUsuario modelUsuario = daoUsuario.autentificar(user);

		String url = request.getParameter("url");

		if (modelUsuario != null) {

			request.getSession().setAttribute("user", modelUsuario);
			System.out.println(modelUsuario);
			request.getSession().setAttribute("IDLogado", modelUsuario.getId());

			url ="/principal/paginainicial.jsp";

			request.getSession().setMaxInactiveInterval(100);
			request.getRequestDispatcher(url).forward(request, response);

		} else {

			msg = "Usuario e senha incorretos.";
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/index.jsp").forward(request, response);

		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		daoUsuario = new DAOUsuario();

		super.init(config);
	}

}

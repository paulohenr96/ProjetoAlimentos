package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOGeneric;
import model.ModelDieta;

/**
 * Servlet implementation class ServletDieta
 */
@WebServlet("/ServletDieta")
public class ServletDieta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOGeneric dao = new DAOGeneric<>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDieta() {
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

		if (acao != null && acao.equalsIgnoreCase("novadieta")) {
			String nome = request.getParameter("nome");
			String objetivo = request.getParameter("objetivo");

			ModelDieta dieta = new ModelDieta();
			dieta.setNome(nome);
			dieta.setObjetivo(objetivo);
			dao.salvar(dieta);
			List lista = dao.consultarTodos(ModelDieta.class);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);
			response.getWriter().write(json);

		} else if (acao != null && acao.equalsIgnoreCase("removerdieta")) {
			response.getWriter().write("Removendo");

		} else if (acao != null && acao.equalsIgnoreCase("todasdietas")) {

			List lista = dao.consultarTodos(ModelDieta.class);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);
			response.getWriter().write(json);
		} else if (acao != null && acao.equalsIgnoreCase("verdieta")) {
			Long id=Long.parseLong(request.getParameter("id"));
			
			ModelDieta dieta=(ModelDieta)dao.consultarPorId(ModelDieta.class, id);
			
			request.setAttribute("dieta", dieta);
			request.getRequestDispatcher("principal/consultadieta.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

}

package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOGeneric;
import model.ModelDieta;
import model.ModelRefeicao;

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
			Long id=Long.parseLong(request.getParameter("id"));
			
			
			ModelDieta dieta=(ModelDieta)dao.consultarPorId(ModelDieta.class, id);
			
			dieta.getListaRefeicoes().forEach((e)->{
				dao.removerAlimentoRefeicao(e.getId());
			});
			dao.removerRefeicaoDieta(id);
			dao.deletarPorId(ModelDieta.class, id);
			List lista = dao.consultarTodos(ModelDieta.class);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);
			response.getWriter().write(json);
		} else if (acao != null && acao.equalsIgnoreCase("todasdietas")) {

			List lista = dao.consultarTodos(ModelDieta.class);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);
			response.getWriter().write(json);
		} else if (acao != null && acao.equalsIgnoreCase("verdieta")) {
			Long id = Long.parseLong(request.getParameter("id"));

			ModelDieta dieta = (ModelDieta) dao.consultarPorId(ModelDieta.class, id);

			request.setAttribute("dieta", dieta);
			request.getRequestDispatcher("principal/consultadieta.jsp").forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("novaref")) {
			Long id = Long.parseLong(request.getParameter("id"));
			String horario = request.getParameter("hora");
			String nome = request.getParameter("nome");
//			Long idUserLogado=(Long)request.getSession().getAttribute("idLogado");
			String hora = horario.replace("-", ":");
			System.out.println(hora);

			ModelDieta dieta = (ModelDieta) dao.consultarPorId(ModelDieta.class, id);
			ModelRefeicao ref = new ModelRefeicao();

			ref.setNome(nome);

			try {
				ref.setHorario(new SimpleDateFormat("HH:mm").parse(hora));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ref.setDieta(dieta);
			dao.salvar(ref);
			Long total = dao.contarTotalRefsDieta(id);
			List<ModelRefeicao> lista = dao.todasRefsDieta(id);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);
			response.getWriter().write(json);

		}else if (acao != null && acao.equalsIgnoreCase("mostrarrefeicoes")) {
			Long id = Long.parseLong(request.getParameter("id"));

			List<ModelRefeicao> lista = dao.todasRefsDieta(id);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);
			response.getWriter().write(json);
		}
		
		
		else {
			request.getRequestDispatcher("index.jsp").forward(request, response);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

}

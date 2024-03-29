package controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bean.ContextoBean;
import dao.DAODieta;
import dao.DAOGeneric;
import dao.DAORefeicao;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;
import srv.DietaService;
import util.Constantes;
import util.Mensagem;
import util.ReportUtil;

/**
 * Servlet implementation class ServletDieta
 */
@WebServlet("/ServletDieta")
public class ServletDieta extends ContextoBean {
	private static final long serialVersionUID = 1L;
	private DAOGeneric dao = new DAOGeneric<>();
	private DAODieta daoDieta = new DAODieta();
	private DAORefeicao daoRefeicao = new DAORefeicao();
	private DietaService dietaService = new DietaService();

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
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("Servlet Dieta Metodo INIT");
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long idLogado = super.getUserLogado(request).getId();
		System.out.println("GET SERVLETDIETA");
		try {
			String acao = request.getParameter("acao");

			if (acao != null && acao.equalsIgnoreCase("novadieta")) {
				String nome = request.getParameter("nome");
				String objetivo = request.getParameter("objetivo");

				String msg = dietaService.novaDieta(nome, objetivo, getUserLogado(request));

				response.getWriter().write(msg);

			} else if (acao != null && acao.equalsIgnoreCase("removerdieta")) {
				Long id = Long.parseLong(request.getParameter("id"));

				dietaService.deletarDieta(id);

				response.getWriter().write("");
			} else if (acao != null && acao.equalsIgnoreCase("todasdietas")) {

				List lista = daoDieta.consultarTodasDietasPorId(idLogado);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(lista);
				response.getWriter().write(json);
			} else if (acao != null && acao.equalsIgnoreCase("todasdietaspaginadas")) {
				Long idUserLogado = (Long) request.getSession().getAttribute("IDLogado");
				int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
				int porpagina = 5;
				daoDieta.flush();
				List todos = daoDieta.consultarTodasDietasPorIdPaginado(idUserLogado, paginaAtual, porpagina);
				Long total = daoDieta.contarDietas(idUserLogado);

				super.realizaPaginacao(response, todos, porpagina, total);
			} else if ((!stringVazia(acao)) && acao.equalsIgnoreCase("entidadedieta")) {
				Long id = Long.parseLong(request.getParameter("id"));
				ModelDieta dieta = daoDieta.consultarPorId(id);
				List lista = new ArrayList<>();
				lista.add(dieta);
				responderAjax(response, lista);

			}

			else if (acao != null && acao.equalsIgnoreCase("verdieta")) {
				Long id = Long.parseLong(request.getParameter("id"));

				ModelDieta dieta = (ModelDieta) dao.consultarPorId(ModelDieta.class, id);
				request.setAttribute("dieta", dieta);
				request.getRequestDispatcher("principal/consultadieta.jsp").forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("novaref")) {
				Long idDieta = Long.parseLong(request.getParameter("id"));
				String horario = request.getParameter("hora");
				String nome = request.getParameter("nome");

				String msg = dietaService.adicionarRefeicao(idDieta, idLogado, nome, horario);
				response.getWriter().write(msg);
			} else if (acao != null && acao.equalsIgnoreCase("mostrarrefeicoes")) {
				Long id = Long.parseLong(request.getParameter("id"));

				List<ModelRefeicao> lista = daoDieta.todasRefsDieta(id);

				super.responderAjax(response, lista);
			} else if (acao != null && acao.equalsIgnoreCase("removerrefeicao")) {
				Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
				Long idDieta = Long.parseLong(request.getParameter("iddieta"));

				dietaService.removerRefeicao(idDieta, idLogado, idRefeicao);
				response.getWriter().write("");

			} else if (acao != null && acao.equalsIgnoreCase("imprimirdieta")) {

				Long iddieta = Long.parseLong(request.getParameter("iddieta"));
				List<ModelRefeicao> lista = (List<ModelRefeicao>) daoDieta.todasRefsDieta(iddieta);

				for (ModelRefeicao r : lista) {
					r.setListaAlimentos(daoRefeicao.consultarAlimentosRefeicao(r.getId()));
				}

				HashMap<String, Object> params = new HashMap<>();
				params.put("param_sub_report", request.getServletContext().getRealPath("relatorio") + File.separator);

				super.relatorio(response, request, params, "rel_dieta", lista);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			request.getRequestDispatcher("principal/erro401.html").forward(request, response);

		}

	}

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

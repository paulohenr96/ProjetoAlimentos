package controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOGeneric;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;
import util.ReportUtil;

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
		try {
			String acao = request.getParameter("acao");

			if (acao != null && acao.equalsIgnoreCase("novadieta")) {
				String nome = request.getParameter("nome");
				String objetivo = request.getParameter("objetivo");
				Long idUserLogado = (Long) request.getSession().getAttribute("IDLogado");

				ModelDieta dieta = new ModelDieta();
				dieta.setNome(nome);
				dieta.setObjetivo(objetivo);
				dieta.setIdUsuario(idUserLogado);

				dao.salvar(dieta);
				List lista = dao.consultarTodasDietasPorId(idUserLogado);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(lista);
				response.getWriter().write(json);

			} else if (acao != null && acao.equalsIgnoreCase("removerdieta")) {
				Long id = Long.parseLong(request.getParameter("id"));
				Long idUserLogado = (Long) request.getSession().getAttribute("IDLogado");

//				ModelDieta dieta = (ModelDieta) dao.consultarDietaPorId(id,idUserLogado);
				ModelDieta dieta = (ModelDieta) dao.consultarPorId(ModelDieta.class, id);

				dieta.getListaRefeicoes().forEach((e) -> {
					dao.removerAlimentoRefeicao(e.getId());
				});
				dao.removerTodasRefeicaoDieta(id);
				dao.deletarPorId(ModelDieta.class, id);
				List lista = dao.consultarTodos(ModelDieta.class);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(lista);
				response.getWriter().write(json);
			} else if (acao != null && acao.equalsIgnoreCase("todasdietas")) {
				Long idUserLogado = (Long) request.getSession().getAttribute("IDLogado");

				List lista = dao.consultarTodasDietasPorId(idUserLogado);

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
				Long idUserLogado = (Long) request.getSession().getAttribute("IDLogado");
				String hora = horario.replace("-", ":");
				System.out.println(hora);

				ModelDieta dieta = (ModelDieta) dao.consultarDietaPorId(id, idUserLogado);
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
				dao.merge(dieta);

				List<ModelRefeicao> lista = dao.todasRefsDieta(id);
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(lista);
				response.getWriter().write(json);

			} else if (acao != null && acao.equalsIgnoreCase("mostrarrefeicoes")) {
				Long id = Long.parseLong(request.getParameter("id"));

				List<ModelRefeicao> lista = dao.todasRefsDieta(id);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(lista);
				response.getWriter().write(json);
			} else if (acao != null && acao.equalsIgnoreCase("removerrefeicao")) {
				Long userLogado = (Long) request.getSession().getAttribute("IDLogado");
				Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
				Long idDieta = Long.parseLong(request.getParameter("iddieta"));

				ModelDieta dieta = (ModelDieta) dao.consultarDietaPorId(idDieta, userLogado);
				ModelRefeicao ref = (ModelRefeicao) dao.consultarPorId(ModelRefeicao.class, idRefeicao);

				dieta.removerRefeicao(ref);
				dao.merge(dieta);

				dao.removerAlimentoRefeicao(idRefeicao);
				dao.deletarPorId(ModelRefeicao.class, idRefeicao);

				response.getWriter().write("");

			} else if (acao != null && acao.equalsIgnoreCase("imprimirtodasdietas")) {
				Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
				List lista = dao.consultarTodasDietasPorId(idLogado);
				System.out.println(lista);
				System.out.println("ID LOGADO ------ "+idLogado);

				byte[] relatorio = new ReportUtil().geraRelatorioPdf(lista, "relatorio_dieta",
						request.getServletContext());
				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);
				// TODO Auto-generated catch block
			}else if (acao != null && acao.equalsIgnoreCase("imprimirdietasemsub")) {
				Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
				Long iddieta = Long.parseLong(request.getParameter("iddieta"));

				List lista = dao.todasRefsDieta(iddieta);
				System.out.println(lista);
				System.out.println("ID LOGADO ------ "+idLogado);

				byte[] relatorio = new ReportUtil().geraRelatorioPdf(lista, "rel_dieta",
						request.getServletContext());
				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);
				// TODO Auto-generated catch block
			}else if (acao != null && acao.equalsIgnoreCase("imprimirdieta")) {
				Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
				Long iddieta = Long.parseLong(request.getParameter("iddieta"));

				List lista = dao.todasRefsDieta(iddieta);
				System.out.println(lista);
				System.out.println("ID LOGADO ------ "+idLogado);
				HashMap<String,Object> params=new HashMap<>();
				params.put("param_sub_report", request.getServletContext().getRealPath("relatorio")+File.separator);
				byte[] relatorio = new ReportUtil().geraRelatorioPdf(lista, "rel_dieta",params,
						request.getServletContext());
				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);
				// TODO Auto-generated catch block
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getRequestDispatcher("erro401.html").forward(request, response);

		}

	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}

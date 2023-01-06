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

import bean.ContextoBean;
import dao.DAODieta;
import dao.DAOGeneric;
import dao.DAORefeicao;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;
import util.ReportUtil;

/**
 * Servlet implementation class ServletDieta
 */
@WebServlet("/ServletDieta")
public class ServletDieta extends ContextoBean {
	private static final long serialVersionUID = 1L;
	private DAOGeneric dao = new DAOGeneric<>();
	private DAODieta daoDieta=new DAODieta();
	private DAORefeicao daoRefeicao=new DAORefeicao();
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
		Long idLogado = super.getUserLogado(request).getId();
		try {
			String acao = request.getParameter("acao");

			if (acao != null && acao.equalsIgnoreCase("novadieta")) {
				String nome = request.getParameter("nome");
				String objetivo = request.getParameter("objetivo");

				ModelDieta dieta = new ModelDieta();
				dieta.setNome(nome);
				dieta.setObjetivo(objetivo);
				dieta.setIdUsuario(idLogado);

				dao.salvar(dieta);
			
				response.getWriter().write("");

			} else if (acao != null && acao.equalsIgnoreCase("removerdieta")) {
				Long id = Long.parseLong(request.getParameter("id"));

				ModelDieta dieta = (ModelDieta) dao.consultarPorId(ModelDieta.class, id);

				dieta.getListaRefeicoes().forEach((e) -> {
					daoRefeicao.removerAlimentoRefeicao(e.getId());
				});
				daoDieta.removerTodasRefeicaoDieta(id);
				dao.deletarPorId(ModelDieta.class, id);
				
				response.getWriter().write("");
			} else if (acao != null && acao.equalsIgnoreCase("todasdietas")) {

				List lista = daoDieta.consultarTodasDietasPorId(idLogado);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(lista);
				response.getWriter().write(json);
			}  else if (acao != null && acao.equalsIgnoreCase("todasdietaspaginadas")) {
				Long idUserLogado = (Long) request.getSession().getAttribute("IDLogado");
				int paginaAtual=Integer.parseInt(request.getParameter("paginaatual"));
				int porpagina=5;
				List todos = daoDieta.consultarTodasDietasPorIdPaginado(idUserLogado,paginaAtual,porpagina);
				Long total=daoDieta.contarDietas(idUserLogado);
				
				
				super.realizaPaginacao(response, todos, porpagina, total);
			}
			
			
			else if (acao != null && acao.equalsIgnoreCase("verdieta")) {
				Long id = Long.parseLong(request.getParameter("id"));

				ModelDieta dieta = (ModelDieta) dao.consultarPorId(ModelDieta.class, id);

				request.setAttribute("dieta", dieta);
				request.getRequestDispatcher("principal/consultadieta.jsp").forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("novaref")) {
				Long id = Long.parseLong(request.getParameter("id"));
				String horario = request.getParameter("hora");
				String nome = request.getParameter("nome");
				String hora = horario.replace("-", ":");
				System.out.println(hora);

				ModelDieta dieta = (ModelDieta) daoDieta.consultarDietaPorId(id, idLogado);
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

				List<ModelRefeicao> lista = daoDieta.todasRefsDieta(id);
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(lista);
				response.getWriter().write(json);

			} else if (acao != null && acao.equalsIgnoreCase("mostrarrefeicoes")) {
				Long id = Long.parseLong(request.getParameter("id"));

				List<ModelRefeicao> lista = daoDieta.todasRefsDieta(id);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(lista);
				response.getWriter().write(json);
			} else if (acao != null && acao.equalsIgnoreCase("removerrefeicao")) {
				Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
				Long idDieta = Long.parseLong(request.getParameter("iddieta"));

				ModelDieta dieta = (ModelDieta) daoDieta.consultarDietaPorId(idDieta, idLogado);
				ModelRefeicao ref = (ModelRefeicao) dao.consultarPorId(ModelRefeicao.class, idRefeicao);

				dieta.removerRefeicao(ref);
				dao.merge(dieta);

				daoRefeicao.removerAlimentoRefeicao(idRefeicao);
				dao.deletarPorId(ModelRefeicao.class, idRefeicao);

				response.getWriter().write("");

			} else if (acao != null && acao.equalsIgnoreCase("imprimirtodasdietas")) {
				List lista = daoDieta.consultarTodasDietasPorId(idLogado);
				System.out.println(lista);
				System.out.println("ID LOGADO ------ "+idLogado);

				byte[] relatorio = new ReportUtil().geraRelatorioPdf(lista, "relatorio_dieta",
						request.getServletContext());
				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);
				// TODO Auto-generated catch block
			}else if (acao != null && acao.equalsIgnoreCase("imprimirdietasemsub")) {
				Long iddieta = Long.parseLong(request.getParameter("iddieta"));

				List lista = daoDieta.todasRefsDieta(iddieta);
				System.out.println(lista);
				System.out.println("ID LOGADO ------ "+idLogado);

				byte[] relatorio = new ReportUtil().geraRelatorioPdf(lista, "rel_dieta",
						request.getServletContext());
				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);
				// TODO Auto-generated catch block
			}else if (acao != null && acao.equalsIgnoreCase("imprimirdieta")) {
				Long iddieta = Long.parseLong(request.getParameter("iddieta"));

				List lista = daoDieta.todasRefsDieta(iddieta);
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

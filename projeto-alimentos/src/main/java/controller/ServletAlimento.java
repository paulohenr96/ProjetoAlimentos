package controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.util.encoders.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import beanDTO.GraficoMacros;
import dao.DAOGeneric;
import model.ModelAlimento;
import model.ModelAlimentoConsumido;
import model.ModelAlimentoRefeicao;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;
import model.ModelUsuario;
import util.ReportUtil;

/**
 * Servlet implementation class ServletAlimento
 */
@WebServlet("/ServletAlimento")
public class ServletAlimento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOGeneric dao = new DAOGeneric();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAlimento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void paginar(HttpServletRequest request, HttpServletResponse response, String msg)
			throws ServletException, IOException {
		Long userLogado = (Long) request.getSession().getAttribute("IDLogado");
		int porPagina = 5;
		int paginaAtual = 1;
		if (request.getParameter("paginaatual") != null) {
			paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
		}

		Long total = (dao.contarTotalAlimentos(userLogado));
		if (total==0) {
			msg="Sem alimentos cadastrados.";
		}
		int totalPaginas = (int) (total % porPagina != 0 ? total / porPagina + 1 : total / porPagina);
		List<ModelAlimento> todos = dao.consultarTodosPaginado(userLogado, porPagina, paginaAtual);
		request.setAttribute("todos", todos);
		request.setAttribute("totalpaginas", totalPaginas);
		request.setAttribute("paginaatual", paginaAtual);
		request.setAttribute("msg", msg);

		request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acao = request.getParameter("acao");
		String msg = "";

		if (acao != null && acao.equalsIgnoreCase("mostrartodosalimentospaginados")) {

			paginar(request, response, "Clique no Alimento para Editar");
		} else if (acao != null && acao.equalsIgnoreCase("editar")) {
			ModelAlimento alimento = gerarAlimento(request, response);
			List consultarAlimentosEmRefeicoes = dao.consultarAlimentosEmRefeicoes(alimento.getId());
			int tamanho=consultarAlimentosEmRefeicoes.size();

			if (tamanho==0) {
				dao.merge(alimento);
				paginar(request, response, "Alimento Editado");
			}else {
				paginar(request, response, "Não foi possivel pois há "+tamanho+" refeições com este alimento.");

			}
		

		} else if (acao != null && acao.equalsIgnoreCase("deletarId")) {
			Long id = Long.parseLong(request.getParameter("idalimento"));
			List consultarAlimentosEmRefeicoes = dao.consultarAlimentosEmRefeicoes(id);
			int tamanho=consultarAlimentosEmRefeicoes.size();
			if (tamanho==0) {
				dao.deletarPorId(ModelAlimento.class, id);
				paginar(request, response, "Alimento Removido");
			}else {
				paginar(request, response, "Não foi possivel pois há "+tamanho+" refeições com este alimento.");

			}
			

		} else if (acao != null && acao.equalsIgnoreCase("pesquisaralimentos")) {
			Long userLogado = (Long) request.getSession().getAttribute("IDLogado");

			int porPagina = 5;
			String nome = request.getParameter("nome");
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			List<ModelAlimento> todos = new ArrayList<ModelAlimento>();
			Long total = 0L;
			if (nome != null) {
				todos = dao.consultarNomePaginado(ModelAlimento.class, nome, porPagina, paginaAtual,userLogado);
				total = (dao.contarTotal(ModelAlimento.class, nome,userLogado));
				System.out.println("TOTAL:"+total +"\n");
			} else {
				todos = dao.consultarTodosPaginado(userLogado, porPagina, paginaAtual);
				total = (dao.contarTotalAlimentos(userLogado));

			}

			int totalPaginas = (int) (total % porPagina != 0 ? total / porPagina + 1 : total / porPagina);

//			request.setAttribute("todos", todos);
//			request.setAttribute("totalpaginas", totalPaginas);
//			request.setAttribute("paginaatual", paginaAtual);
//			request.setAttribute("msg", "Clique no Alimento para Editar");
//			
//			request.getRequestDispatcher("/principal/refeicoes.jsp").forward(request, response);
			
			
			response.addHeader("totalPagina", "" + totalPaginas);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(todos);
			response.getWriter().write(json);
		}else if (acao != null && acao.equalsIgnoreCase("pesquisarrefeicao")) {
			String nome = request.getParameter("nome");
			Long userLogado = (Long) request.getSession().getAttribute("IDLogado");
			int porPagina=5; 
			int paginaAtual=Integer.parseInt(request.getParameter("paginaatual")); 
			
	
			
			List<ModelRefeicao> todos = new ArrayList<ModelRefeicao>();
			Long total = 0L;
			if (nome != null) {
				todos = dao.consultarRefeicaoPaginado(nome, porPagina, paginaAtual,userLogado);
				total = dao.contarTotalRefeicoes(nome,userLogado);
				System.out.println("TOTAL:"+total +"\n");
			} else {
				todos = dao.consultarTodosRefeicaoPaginado( paginaAtual, porPagina, userLogado);
				total = dao.contarTotalRefeicoes(userLogado);

			}
			int totalPaginas=(int) (total%porPagina==0?total/porPagina: 1+total/porPagina);
			
			response.addHeader("totalPagina",""+ totalPaginas);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(todos);

			response.getWriter().write(json);
			
		}else if (acao != null && acao.equalsIgnoreCase("adicionarrefeicaomacros")) {
			Long id = Long.parseLong(request.getParameter("id"));
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
			String data = request.getParameter("data");
			ModelConsumidoDia macros = dao.consultarConsumoDia(editaData(data), idLogado);
			ModelRefeicao ref=(ModelRefeicao)dao.consultarPorId(ModelRefeicao.class, id);
			if (macros != null) {
				macros.adicionarRefeicao(ref);
				dao.merge(macros);
			} else {

				macros = new ModelConsumidoDia();
				macros.setUsuario((ModelUsuario) dao.buscarUsandoID(idLogado, ModelUsuario.class));
				macros.setData(editaData(data));
				macros.adicionarRefeicao(ref);
				dao.salvar(macros);
				
				macros = dao.consultarConsumoDia(editaData(data), idLogado);

			}
			
// Não importa
			
			ref.setId(null);
			ref.setMacros(macros);
			dao.salvar(ref);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(macros);
			response.getWriter().write(json);
		}
		else if (acao != null && acao.equalsIgnoreCase("alimentoconsumido")) {
			Long id = Long.parseLong(request.getParameter("id"));
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			ModelAlimento alimento = ((ModelAlimento) dao.buscarUsandoID(id, ModelAlimento.class)).consumir(quantidade);
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
			String data = request.getParameter("data");
			ModelConsumidoDia macros = dao.consultarConsumoDia(editaData(data), idLogado);
			System.out.println(request.getParameter("data"));
			ModelAlimentoConsumido alimentoConsumido = new ModelAlimentoConsumido();
			alimentoConsumido.setIdAlimento(alimento.getId());
			alimentoConsumido.setNome(alimento.getNome());
			alimentoConsumido.setQuantidade(quantidade);

			if (macros != null) {
				macros.adicionarAlimento(alimento);
				dao.merge(macros);
				alimentoConsumido.setMacros(macros);

			} else {

				ModelConsumidoDia dia = new ModelConsumidoDia();
				dia.setUsuario((ModelUsuario) dao.buscarUsandoID(idLogado, ModelUsuario.class));
				dia.setData(editaData(data));
				dia.adicionarAlimento(alimento);
				dao.salvar(dia);
				alimentoConsumido.setMacros(dia);

			}
			dao.salvar(alimentoConsumido);
//			request.getSession().setAttribute("macros", macros);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(macros);
			response.getWriter().write(json);

		}else if (acao != null && acao.equalsIgnoreCase("consultarmacros")) {
			
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
			String data = request.getParameter("data");

			ModelConsumidoDia macros = dao.consultarConsumoDia(editaData(data), idLogado);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(macros);
			response.getWriter().write(json);


		}
		
		else if (acao != null && acao.equalsIgnoreCase("removeralimentoconsumido")) {
			Long id = Long.parseLong(request.getParameter("id"));
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
			// Alimento que será removido

			ModelAlimentoConsumido alimentoConsumido = (ModelAlimentoConsumido) dao.buscarUsandoID(id,
					ModelAlimentoConsumido.class);
			System.out.println(id);
			dao.deletarPorId(ModelAlimentoConsumido.class, alimentoConsumido.getId());

			ModelAlimento alimento = ((ModelAlimento) dao.buscarUsandoID(alimentoConsumido.getIdAlimento(),
					ModelAlimento.class)).consumir(quantidade);
			String data = request.getParameter("data");
			ModelConsumidoDia consumoDia = dao.consultarConsumoDia(editaData(data), idLogado);
			consumoDia.retirarAlimento(alimento);
			if (consumoDia.getProteinas().doubleValue()<0.1 && consumoDia.getCalorias().doubleValue()<0.1 && consumoDia.getCarboidrato().doubleValue()<0.1 && consumoDia.getGordura().doubleValue()<0.1) {
				dao.deletarPorId(ModelConsumidoDia.class, consumoDia.getId());
			}else {
				dao.merge(consumoDia);

			}
			
			System.out.println(consumoDia.getProteinas().doubleValue());

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(consumoDia);
			response.getWriter().write(json);

		}else if (acao != null && acao.equalsIgnoreCase("removerrefeicaoconsumida")) {
			Long id = Long.parseLong(request.getParameter("id"));
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
			// Alimento que será removido

			ModelRefeicao ref = (ModelRefeicao) dao.buscarUsandoID(id,
					ModelRefeicao.class);
			dao.deletarPorId(ModelRefeicao.class, ref.getId());

			String data = request.getParameter("data");
			ModelConsumidoDia consumoDia = dao.consultarConsumoDia(editaData(data), idLogado);
			consumoDia.removerRefeicao(ref);
			
			if (consumoDia.getProteinas().doubleValue()<0.1 && consumoDia.getCalorias().doubleValue()<0.1 && consumoDia.getCarboidrato().doubleValue()<0.1 && consumoDia.getGordura().doubleValue()<0.1) {
				dao.deletarPorId(ModelConsumidoDia.class, consumoDia.getId());
			}else {
				dao.merge(consumoDia);

			}

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(consumoDia);
			response.getWriter().write(json);

		}
		
		
		else if (acao != null && acao.equalsIgnoreCase("historico")) {
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");
			int paginaAtual = 1;
			int porPagina = Integer.parseInt(request.getParameter("porpagina"));
			String ordenar = request.getParameter("asc");
			System.out.println(ordenar + "-------");
			String ordem = request.getParameter("ordem");
			if (request.getParameter("paginaatual") != null) {
				paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			}

			List<ModelConsumidoDia> lista = dao.consultarTodosPaginadoMacros(porPagina, paginaAtual, idLogado, ordem,
					ordenar);

			Long total = (dao.contarTotalMacros(idLogado));
			int totalPaginas = (int) (total % porPagina != 0 ? total / porPagina + 1 : total / porPagina);

			response.addHeader("totalPagina", "" + total);

			System.out.println(lista);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);
			response.getWriter().write(json);
		} else if (acao != null && acao.equalsIgnoreCase("mostrargrafico")) {
			String macro = request.getParameter("macro");
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");

			List<ModelConsumidoDia> lista = dao.consultarTodos(ModelConsumidoDia.class, idLogado);

			lista.sort(new Comparator<ModelConsumidoDia>() {

				@Override
				public int compare(ModelConsumidoDia o1, ModelConsumidoDia o2) {
					// TODO Auto-generated method stub
					return o1.getData().after(o2.getData()) ? 1 : -1;
				}
			});

			GraficoMacros graficoMacros = new GraficoMacros();

			lista.forEach(e -> {

				graficoMacros.getListaCalorias().add(e.getCalorias());
				graficoMacros.getListaData().add(e.getData());
				graficoMacros.getListaProteinas().add(e.getProteinas());
				graficoMacros.getListaCarboidratos().add(e.getCarboidrato());
				graficoMacros.getListaGorduras().add(e.getGordura());

			});

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(graficoMacros);
			response.getWriter().write(json);

		} else if (acao != null && acao.equalsIgnoreCase("limparmacros")) {
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");

			String data = request.getParameter("data");

			ModelConsumidoDia consumoDia = dao.consultarConsumoDia(editaData(data), idLogado);
			consumoDia.setCalorias(new BigDecimal(0));
			consumoDia.setProteinas(new BigDecimal(0));
			consumoDia.setCarboidrato(new BigDecimal(0));
			consumoDia.setGordura(new BigDecimal(0));
			dao.deletarAlimentoConsumidoPorId(ModelAlimentoConsumido.class, consumoDia.getId());
			dao.deletarRefeicoesConsumidas(consumoDia.getId());
			dao.deletarPorId(ModelConsumidoDia.class,consumoDia.getId());

			response.getWriter().write("");

		} else if (acao != null && acao.equalsIgnoreCase("alimentosmodal")) {
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");

			int porPagina = 5;
			int paginaAtual = 1;
			if (request.getParameter("paginaatual") != null) {
				paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			}
			String data = request.getParameter("data");
			ModelConsumidoDia macros = dao.consultarConsumoDia(editaData(data), idLogado);
			if (macros != null) {
				Long macroId = macros.getId();
				Long total = (dao.contarTodosAlimentosConsumidos(ModelAlimentoConsumido.class, macroId));
				int totalPaginas = (int) (total % porPagina != 0 ? total / porPagina + 1 : total / porPagina);

				List<ModelAlimentoConsumido> consultarTodosPaginadoAlimentos = dao
						.consultarTodosPaginadoAlimentos(porPagina, paginaAtual, macroId);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(consultarTodosPaginadoAlimentos);
				response.addHeader("totalPagina", "" + total);
				response.getWriter().write(json);
			} else {
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(new ArrayList());

				response.getWriter().write(json);

				response.getWriter().write("");

			}

		}else if (acao != null && acao.equalsIgnoreCase("refeicoesmodal")) {
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");

			int porPagina = 5;
			int paginaAtual = 1;
			if (request.getParameter("paginaatual") != null) {
				paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			}
			String data = request.getParameter("data");
			ModelConsumidoDia macros = dao.consultarConsumoDia(editaData(data), idLogado);
			if (macros != null) {
				Long macroId = macros.getId();
				Long total = (dao.contarTotalRefeicoesConsumidas(idLogado,macroId));
				int totalPaginas = (int) (total % porPagina != 0 ? total / porPagina + 1 : total / porPagina);

				List consultarRefsMacros = dao.consultarRefsMacros(porPagina, paginaAtual, macroId);

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(consultarRefsMacros);
				response.addHeader("totalPagina", "" + total);
				response.getWriter().write(json);
			} else {
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(new ArrayList());

				response.getWriter().write(json);

				response.getWriter().write("");

			}

		}
		
		
		else if (acao != null && acao.equalsIgnoreCase("ImprimirRelatorioMacrosPDF")) {
			Long idLogado = (Long) request.getSession().getAttribute("IDLogado");

			List<ModelConsumidoDia> lista = dao.consultarTodos(ModelConsumidoDia.class, idLogado);

			lista.sort(new Comparator<ModelConsumidoDia>() {

				@Override
				public int compare(ModelConsumidoDia o1, ModelConsumidoDia o2) {
					// TODO Auto-generated method stub
					return o2.getData().after(o1.getData()) ? 1 : -1;
				}

			});

			HashMap<String, Object> params = new HashMap<String, Object>();

			params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("Consumidos") + File.separator);
			try {
				byte[] relatorio = new ReportUtil().geraRelatorioPdf(lista, "rel_alimentos_jsp_5", params,
						request.getServletContext());
				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (acao != null && acao.equalsIgnoreCase("novarefeicao")) {
			String nome = request.getParameter("nome");
			Long userLogado = (Long) request.getSession().getAttribute("IDLogado");
			ModelRefeicao refeicao = new ModelRefeicao();

			refeicao.setNome(nome);
			refeicao.setIdUserLogado(userLogado);

			dao.salvar(refeicao);

			response.getWriter().write("Refeição " + nome);

		} else if (acao != null && acao.equalsIgnoreCase("todasrefeicoes")) {
			Long userLogado = (Long) request.getSession().getAttribute("IDLogado");
			int porPagina=Integer.parseInt(request.getParameter("porpagina")); 
			int paginaAtual=Integer.parseInt(request.getParameter("paginaatual")); 
			Long total=dao.contarTotalRefeicoes(userLogado);
			int totalPaginas=(int) (total%porPagina==0?total/porPagina: 1+total/porPagina);
			
			
			List<ModelRefeicao> lista = dao.consultarTodosRefeicaoPaginado( paginaAtual, porPagina, userLogado);

			
			response.addHeader("totalPaginas",""+ totalPaginas);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);

			response.getWriter().write(json);

		} else if (acao != null && acao.equalsIgnoreCase("consultarrefeicao")) {

			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
			ModelRefeicao ref = (ModelRefeicao) dao.consultarPorId(ModelRefeicao.class, idRefeicao);
			request.setAttribute("ref", ref);
			request.getRequestDispatcher("principal/consultarefeicao.jsp").forward(request, response);
		}else if (acao != null && acao.equalsIgnoreCase("mediamacros")) {
			Long userLogado = (Long) request.getSession().getAttribute("IDLogado");

			List mediaMacros = dao.mediaMacros(userLogado);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(mediaMacros);
			response.getWriter().write(json);
			
			
		}
		
		else if (acao != null && acao.equalsIgnoreCase("pesquisaralimentorefeicao")) {
			Long userLogado=(Long)request.getSession().getAttribute("IDLogado");

			int porPagina=Integer.parseInt(request.getParameter("porpagina"));
			int paginaAtual=Integer.parseInt(request.getParameter("paginaatual"));
			String nome=request.getParameter("nome");
			
			List<ModelAlimento> lista = new ArrayList<ModelAlimento>();
			System.out.println(nome+"------------");
			Long total=0L;
			if(nome==null || nome.equalsIgnoreCase("null")) {
				lista=dao.consultarTodosPaginado(userLogado,porPagina,paginaAtual);
				total=dao.contarTotalAlimentos(userLogado);
			}else {
				lista=dao.consultarNomePaginado(ModelAlimento.class,nome,porPagina,paginaAtual,userLogado);
				total = dao.contarTotal(ModelAlimento.class, nome,userLogado);

			}
			
			
			int totalPaginas = (int) (total % porPagina != 0 ? total / porPagina + 1 : total / porPagina);

			response.addHeader("totalPagina", "" + total);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);

			response.getWriter().write(json);

		}else if (acao != null && acao.equalsIgnoreCase("adicionaralimentorefeicao")) {
			Long id=Long.parseLong(request.getParameter("id"));
			Long idRefeicao=Long.parseLong(request.getParameter("idrefeicao"));

			double quantidade=Double.parseDouble(request.getParameter("quantidade"));
			
			ModelAlimento alimento=(ModelAlimento)dao.consultarPorId(ModelAlimento.class,id);
			ModelRefeicao ref=(ModelRefeicao)dao.consultarPorId(ModelRefeicao.class,idRefeicao);
			ModelAlimentoRefeicao ali=new ModelAlimentoRefeicao();
			ali.setAlimento(alimento);
			ali.setQuantidade(quantidade);
			ali.setRefeicao(ref);
			System.out.println(ali.getAlimento().getNome());
			ref.addAlimento(ali);
			if (ref.getDieta()!=null) {
				ModelDieta dieta = ref.getDieta();
				dieta.adicionarRefeicao(ref);
				dao.merge(dieta);
			}
			dao.merge(ref);
			dao.salvar(ali);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(ref);
			response.getWriter().write(json);

		}else if (acao != null && acao.equalsIgnoreCase("informacaodarefeicao")) {
			Long idRefeicao=Long.parseLong(request.getParameter("idrefeicao"));
			ModelRefeicao ref=(ModelRefeicao)dao.consultarPorId(ModelRefeicao.class, idRefeicao);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(ref);
			response.getWriter().write(json);

		}
		else if (acao != null && acao.equalsIgnoreCase("alimentosrefeicao")) {
			Long idRefeicao=Long.parseLong(request.getParameter("idrefeicao"));
			
			
			List lista = dao.consultarAlimentosRefeicao(idRefeicao);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(lista);
			response.getWriter().write(json);
			
		}else if (acao != null && acao.equalsIgnoreCase("removeralimentorefeicao")) {
			Long idAlimento=Long.parseLong(request.getParameter("idalimento"));
			Long idRefeicao=Long.parseLong(request.getParameter("idrefeicao"));

			
			ModelAlimentoRefeicao ali=(ModelAlimentoRefeicao)dao.consultarPorId(ModelAlimentoRefeicao.class, idAlimento);
			ModelRefeicao ref=(ModelRefeicao)dao.consultarPorId(ModelRefeicao.class, idRefeicao);
			if (ali!=null)
			{
				ref.removeralimento(ali);
				dao.merge(ref);
				dao.deletarPorId(ModelAlimentoRefeicao.class, idAlimento);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(ref);
			response.getWriter().write(json);

		}else if (acao != null && acao.equalsIgnoreCase("removerrefeicao")) {
			Long idrefeicao=Long.parseLong(request.getParameter("idrefeicao"));
			
			
			dao.removerAlimentoRefeicao(idrefeicao);
			dao.deletarPorId(ModelRefeicao.class, idrefeicao);
			response.getWriter().write("");

		}else if (acao != null && acao.equalsIgnoreCase("addrefeicao")) {
			Long idLogado=(Long)request.getSession().getAttribute("IDLogado");
			Long idRefeicao=Long.parseLong(request.getParameter("idRefeicao"));
			String data = request.getParameter("data");

			ModelConsumidoDia macros = dao.consultarConsumoDia(editaData(data), idLogado);

			ModelRefeicao ref=(ModelRefeicao)dao.consultarPorId(ModelRefeicao.class, idRefeicao);
			
		}
			
			

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		dao.salvar(gerarAlimento(request, response));
		request.setAttribute("msg", "Alimento Adicionado.");
		request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);
	}

	public ModelAlimento gerarAlimento(HttpServletRequest request, HttpServletResponse response) {
		String nome = request.getParameter("nome");
		double porcao = Double.parseDouble(request.getParameter("porcao"));
		double caloria = Double.parseDouble(request.getParameter("caloria"));
		double proteina = Double.parseDouble(request.getParameter("proteina"));
		double carboidrato = Double.parseDouble(request.getParameter("carboidrato"));
		double gordura = Double.parseDouble(request.getParameter("gordura"));
		
		ModelAlimento modelAlimento = new ModelAlimento();

		modelAlimento.setCaloria(new BigDecimal(caloria));
		modelAlimento.setCarboidrato(new BigDecimal(carboidrato));
		modelAlimento.setGordura(new BigDecimal(gordura));
		modelAlimento.setPorcao(porcao);
		modelAlimento.setNome(nome);
		modelAlimento.setProteina(new BigDecimal(proteina));
		modelAlimento.setIdUser((Long)request.getSession().getAttribute("IDLogado"));
		return modelAlimento;
	}

	public Date editaData(String format) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//		String format = dateFormat.format(data);
		Date data2 = null;
		try {
			data2 = dateFormat.parse(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data2;
	}
}

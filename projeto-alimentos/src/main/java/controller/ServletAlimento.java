package controller;

import java.awt.PageAttributes;
import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import bean.ContextoBean;
import beanDTO.ConsumidoDTO;
import beanDTO.GraficoMacros;
import dao.DAOAlimento;
import dao.DAOAlimentoConsumido;
import dao.DAOConsumido;
import dao.DAODieta;
import dao.DAOGeneric;
import dao.DAORefeicao;
import model.ModelAlimento;
import model.ModelAlimentoConsumido;
import model.ModelAlimentoRefeicao;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;
import model.ModelRefeicaoConsumida;
import model.ModelUsuario;
import srv.AlimentoService;
import srv.ConsumidoService;
import srv.DietaService;
import srv.RefeicaoService;
import util.Constantes;
import util.Mensagem;

/**
 * Servlet implementation class ServletAlimento
 */
@WebServlet("/ServletAlimento")
public class ServletAlimento extends ContextoBean {
	private static final long serialVersionUID = 1L;
	private DAOAlimento daoAlimento = new DAOAlimento();
	private DAORefeicao daoRefeicao = new DAORefeicao();
	private DAOConsumido daoConsumido = new DAOConsumido();
	private DAOGeneric<ModelRefeicaoConsumida> daoRefeicaoConsumida = new DAOGeneric<ModelRefeicaoConsumida>();
	private DietaService dietaService = new DietaService();
	private RefeicaoService refeicaoService = new RefeicaoService();
	private ConsumidoService consumidoService = new ConsumidoService();
	private AlimentoService alimentoService=new AlimentoService();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAlimento() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelUsuario userLogado = super.getUserLogado(request);
		Long idLogado = userLogado.getId();
		// TODO Auto-generated method stub
		String acao = request.getParameter("acao");
		String msg = "";

		super.requestEncoding(request);
		

		if (acao != null && acao.equalsIgnoreCase("editar")) {
			ModelAlimento alimento = gerarAlimento(request, response);
			
			 List<Object[]> list = alimentoService.editarAlimento(alimento);
			responderAjax(response, list);
		} else if (acao != null && acao.equalsIgnoreCase("alimentoscadastrados")) {
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));

			int porpagina = 5;
			long total = daoAlimento.contarTotalAlimentos(idLogado);
			List<ModelAlimento> todos = daoAlimento.consultarTodosPaginado(idLogado, porpagina, paginaAtual);
			super.realizaPaginacao(response, todos, porpagina, total);
		}

		else if (acao != null && acao.equalsIgnoreCase("deletarId")) {
			Long id = Long.parseLong(request.getParameter("idalimento"));
			Long tamanho = daoRefeicao.contarRefeicoesComOAlimento(id);
			int porpagina = 5;

			if (tamanho == 0) {
				daoAlimento.deletarPorId(ModelAlimento.class, id);
				responderAjax(response, "");

			} else {
				List<Object[]> listaInnerJoin = daoRefeicao.refeicoesAlimentosInnerJoin(id);
				int quantidadeDeRefeicoes = listaInnerJoin.size();

				responderAjax(response, listaInnerJoin);
			}

		} else if (acao != null && acao.equalsIgnoreCase("pesquisaralimentos")) {

			int porPagina = 5;
			String nome = request.getParameter("nome");
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			List<ModelAlimento> todos = new ArrayList<ModelAlimento>();
			Long total = 0L;
			if (nome != null) {
				daoAlimento.setNome(nome);

			}
			todos = daoAlimento.consultarTodosPaginado(idLogado, porPagina, paginaAtual);
			total = daoAlimento.contarTotalAlimentos(idLogado);
			super.realizaPaginacao(response, todos, porPagina, total);

		} else if (acao != null && acao.equalsIgnoreCase("pesquisarrefeicao")) {
			String nome = request.getParameter("nome");
			int porPagina = 5;
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));

			List<ModelRefeicao> todos = new ArrayList<ModelRefeicao>();
			Long total = 0L;
			if (nome != null) {
				daoRefeicao.setNome(nome);

			}
			todos = daoRefeicao.consultarTodosRefeicaoPaginado(paginaAtual, porPagina, idLogado);

			total = daoRefeicao.contarTotalRefeicoes(idLogado);
			
			super.realizaPaginacao(response, todos, porPagina, total);

		} else if (acao != null && acao.equalsIgnoreCase("adicionarrefeicaomacros")) {
			Long id = Long.parseLong(request.getParameter("id"));
			String data = request.getParameter("data");
			
			String mensagem = consumidoService.consumirRefeicao(userLogado, data, id);
			
			response.getWriter().write(mensagem);

		} else if (acao != null && acao.equalsIgnoreCase("alimentoconsumido")) {
			Long id = Long.parseLong(request.getParameter("id"));
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			ModelAlimento alimento = ((ModelAlimento) daoAlimento.consultarPorId(ModelAlimento.class, id));
			String data = request.getParameter("data");
			
			String mensagem=consumidoService.consumirAlimento(userLogado, quantidade, alimento, data);
			response.getWriter().write(mensagem);


		} else if (acao != null && acao.equalsIgnoreCase("consultarmacros")) {

			String data = request.getParameter("data");

			ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			super.responderAjax(response, macros);

		}

		else if (acao != null && acao.equalsIgnoreCase("removeralimentoconsumido")) {
			Long id = Long.parseLong(request.getParameter("id"));
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			String data = request.getParameter("data");

			 ConsumidoDTO consumidoDTO = consumidoService.removerAlimento(quantidade,userLogado,data,id);
			
			
			super.responderAjax(response, consumidoDTO);

		} else if (acao != null && acao.equalsIgnoreCase("removerrefeicaoconsumida")) {
			Long id = Long.parseLong(request.getParameter("id"));
			// Alimento que será removido

			String data = request.getParameter("data");
			consumidoService.removerRefeicao(id, userLogado, data);
			super.responderAjax(response, null);

		}

		else if (acao != null && acao.equalsIgnoreCase("historico")) {
			int paginaAtual = 1;
			int porPagina = Integer.parseInt(request.getParameter("porpagina"));
			String ordenar = request.getParameter("asc");
			String ordem = request.getParameter("ordem");
			if (request.getParameter("paginaatual") != null) {
				paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			}

			List<ModelConsumidoDia> todos = daoConsumido.consultarTodosPaginadoMacros(porPagina, paginaAtual, idLogado,
					ordem, ordenar);
			List<ConsumidoDTO> todosDto=new ArrayList<>();
			todos.forEach((t)->{
				todosDto.add(new ConsumidoDTO(t.getData(),t.getCalorias(),t.getProteinas()
						,t.getCarboidrato(),t.getGordura()));
			});
			Long total = (daoConsumido.contarTotalMacros(idLogado));
			super.realizaPaginacao(response, todosDto, porPagina, total);

		} else if (acao != null && acao.equalsIgnoreCase("mostrargrafico")) {
			String macro = request.getParameter("macro");

			List<ModelConsumidoDia> lista = daoConsumido.consultarTodos(idLogado);

			GraficoMacros graficoMacros = new GraficoMacros();
			if (lista != null) {
				lista.sort(new Comparator<ModelConsumidoDia>() {

					@Override
					public int compare(ModelConsumidoDia o1, ModelConsumidoDia o2) {
						// TODO Auto-generated method stub
						return o1.getData().after(o2.getData()) ? 1 : -1;
					}
				});
				for (ModelConsumidoDia e : lista) {
					graficoMacros.getListaCalorias().add(e.getCalorias());
					graficoMacros.getListaData().add(e.getData());
					graficoMacros.getListaProteinas().add(e.getProteinas());
					graficoMacros.getListaCarboidratos().add(e.getCarboidrato());
					graficoMacros.getListaGorduras().add(e.getGordura());
				}

			}

			super.responderAjax(response, graficoMacros);

		} else if (acao != null && acao.equalsIgnoreCase("limparmacros")) {

			String data = request.getParameter("data");

			String mensagem = consumidoService.limpar(data,userLogado);
			responderAjax(response, mensagem);


		} else if (acao != null && acao.equalsIgnoreCase("alimentosmodal")) {

			int porPagina = 5;
			int paginaAtual = 1;
			if (request.getParameter("paginaatual") != null) {
				paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			}
			String data = request.getParameter("data");
			ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			if (macros != null) {
				Long macroId = macros.getId();
				Long total = daoConsumido.contarTodosAlimentosConsumidos(macroId);

				List<ModelAlimentoConsumido> todos = daoConsumido.consultarTodosPaginadoAlimentos(porPagina,
						paginaAtual, macroId);

				super.realizaPaginacao(response, todos, porPagina, total);
				todos.clear();
			} else {
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(new ArrayList());

				response.getWriter().write(json);

				response.getWriter().write("");

			}

		} else if (acao != null && acao.equalsIgnoreCase("refeicoesmodal")) {

			int porPagina = 5;
			int paginaAtual = 1;
			if (request.getParameter("paginaatual") != null) {
				paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			}
			String data = request.getParameter("data");
			ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			if (macros != null) {
				Long macroId = macros.getId();
				String hql = "select count(u) from ModelRefeicaoConsumida u where u.macros.id=" + macroId;
				Long total = daoRefeicaoConsumida.retornaLongHql(hql);

				if (total.intValue() != 0) {
					List<ModelRefeicaoConsumida> todos = daoConsumido.consultarRefsMacros(porPagina, paginaAtual,
							macroId);

					super.realizaPaginacao(response, todos, porPagina, total);
					todos.clear();
				} else {
					responderAjax(response, "");
				}

			} else {
				responderAjax(response, "");

			}

		}

		else if (acao != null && acao.equalsIgnoreCase("ImprimirRelatorioMacrosPDF")) {

			List<ModelConsumidoDia> lista = daoConsumido.consultarTodos(idLogado);
			for (ModelConsumidoDia m : lista) {
				m.setUsuario(userLogado);
			}
			lista.sort(new Comparator<ModelConsumidoDia>() {

				@Override
				public int compare(ModelConsumidoDia o1, ModelConsumidoDia o2) {
					// TODO Auto-generated method stub
					return o2.getData().after(o1.getData()) ? 1 : -1;
				}

			});
			HashMap<String, Object> params = new HashMap<String, Object>();

			params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio") + File.separator);
			params.put("PARAM_FOTO", request.getServletContext().getRealPath("assets") + File.separator + "img"
					+ File.separator + "user-1.png");

			super.relatorio(response, request, params, "rel_alimentos_jsp", lista);
			lista.clear();
		} else if (acao != null && acao.equalsIgnoreCase("novarefeicao")) {
			String nome = request.getParameter("nome");
			ModelRefeicao refeicao = new ModelRefeicao();
			Long total = daoRefeicao.contarTotalRefeicoes(idLogado);
			if (total == Constantes.VALOR_MAXIMO_REFEICOES) {
				response.getWriter().write(Mensagem.MENSAGEM_ERRO);

			} else {
				refeicaoService.salvar(idLogado, nome);
				response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);
			}

		} else if (acao != null && acao.equalsIgnoreCase("todasrefeicoes")) {
			int porPagina = Integer.parseInt(request.getParameter("porpagina"));
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			Long total = daoRefeicao.contarTotalRefeicoes(idLogado);

			List<ModelRefeicao> todos = daoRefeicao.consultarTodosRefeicaoPaginado(paginaAtual, porPagina, idLogado);

			super.realizaPaginacao(response, todos, porPagina, total);
			todos.clear();

		} else if (acao != null && acao.equalsIgnoreCase("consultarrefeicao")) {

			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
			ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, idRefeicao);
			request.setAttribute("ref", ref);
			request.getRequestDispatcher("principal/consultarefeicao.jsp").forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("mediamacros")) {

			List mediaMacros = daoConsumido.mediaMacros(idLogado);
			super.responderAjax(response, mediaMacros);
			mediaMacros.clear();

		}

		else if (acao != null && acao.equalsIgnoreCase("pesquisaralimentorefeicao")) {

			int porPagina = Integer.parseInt(request.getParameter("porpagina"));
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			String nome = request.getParameter("nome");

			List<ModelAlimento> todos = new ArrayList<ModelAlimento>();
			Long total = 0L;
			if (nome != null || !nome.equalsIgnoreCase("null")) {
				daoAlimento.setNome(nome);

			}
			todos = daoAlimento.consultarTodosPaginado(idLogado, porPagina, paginaAtual);
			total = daoAlimento.contarTotalAlimentos(idLogado);

			super.realizaPaginacao(response, todos, porPagina, total);

		} else if (acao != null && acao.equalsIgnoreCase("adicionaralimentorefeicao")) {
			Long id = Long.parseLong(request.getParameter("id"));
			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
			DAOGeneric<ModelAlimentoRefeicao> dao = new DAOGeneric<ModelAlimentoRefeicao>();
			double quantidade = Double.parseDouble(request.getParameter("quantidade"));

			ModelAlimento alimento = (ModelAlimento) daoAlimento.consultarPorId(ModelAlimento.class, id);
			ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, idRefeicao);
			
			ref = refeicaoService.adicionarAlimento(ref, quantidade, alimento);
			if (ref.getDieta() != null) {
				dietaService.adicionarAlimentoRefeicao(ref.getDieta(), quantidade, alimento);
			}
			super.responderAjax(response, ref);

		} else if (acao != null && acao.equalsIgnoreCase("informacaodarefeicao")) {
			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
			ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, idRefeicao);
			super.responderAjax(response, ref);

		} else if (acao != null && acao.equalsIgnoreCase("alimentosrefeicao")) {
			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));

			List<ModelAlimentoRefeicao> lista = new ArrayList<ModelAlimentoRefeicao>();
			lista = daoRefeicao.consultarAlimentosRefeicao(idRefeicao);
			super.responderAjax(response, lista);

		} else if (acao != null && acao.equalsIgnoreCase("removeralimentorefeicao")) {
			Long idAlimento = Long.parseLong(request.getParameter("idalimento"));
			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
			int quantidadeRetirar = Integer.parseInt(request.getParameter("quantidade"));

			DAOGeneric<ModelAlimentoRefeicao> dao = new DAOGeneric<ModelAlimentoRefeicao>();
			ModelAlimentoRefeicao ali = (ModelAlimentoRefeicao) dao.consultarPorId(ModelAlimentoRefeicao.class,
					idAlimento);
			ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, idRefeicao);
			
			if (ali != null) {
				ref = refeicaoService.removerAlimento(ali, ref, quantidadeRetirar);
				if (ref.getDieta() != null) {
					dietaService.removerAlimentoRefeicao(ref, ali, quantidadeRetirar);
				}
			}

			responderAjax(response, "");

		} else if (acao != null && acao.equalsIgnoreCase("removerrefeicao")) {
			Long idrefeicao = Long.parseLong(request.getParameter("idrefeicao"));

			String hql = "select count(u) from ModelRefeicaoConsumida u where u.refeicao.id=" + idrefeicao;
			Long total = daoRefeicaoConsumida.retornaLongHql(hql);
			daoRefeicao.removerAlimentoRefeicao(idrefeicao);
			daoRefeicaoConsumida.realizarUpdate("delete from ModelRefeicaoConsumida where refeicao_id=" + idrefeicao);
			daoRefeicao.deletarPorId(ModelRefeicao.class, idrefeicao);
			response.getWriter().write("");

		} 

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

//		request.setAttribute("msg", "Alimento Adicionado.");
//		request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);
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
		modelAlimento.setIdUser(super.getUserLogado(request).getId());
		if (request.getParameter("id") != null) {
			long id = Long.parseLong(request.getParameter("id"));
			modelAlimento.setId(id);
		}
		return modelAlimento;
	}

}

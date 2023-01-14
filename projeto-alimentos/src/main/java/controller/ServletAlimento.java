package controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import bean.ContextoBean;
import beanDTO.GraficoMacros;
import dao.DAOAlimento;
import dao.DAOAlimentoConsumido;
import dao.DAOConsumido;
import dao.DAOGeneric;
import dao.DAORefeicao;
import model.ModelAlimento;
import model.ModelAlimentoConsumido;
import model.ModelAlimentoRefeicao;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;
import model.ModelUsuario;
import net.sf.jasperreports.compilers.ConstantExpressionEvaluation;
import util.Constantes;
import util.Mensagem;

/**
 * Servlet implementation class ServletAlimento
 */
@WebServlet("/ServletAlimento")
public class ServletAlimento extends ContextoBean {
	private static final long serialVersionUID = 1L;
	private DAOGeneric dao = new DAOGeneric();
	private DAOAlimento daoAlimento = new DAOAlimento();
	private DAORefeicao daoRefeicao = new DAORefeicao();
	private DAOConsumido daoConsumido = new DAOConsumido();
	private DAOAlimentoConsumido daoAlimentoConsumido = new DAOAlimentoConsumido();

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
		Long idLogado = super.getUserLogado(request).getId();
		// TODO Auto-generated method stub
		String acao = request.getParameter("acao");
		String msg = "";
		super.requestEncoding(request);
		if (acao != null && acao.equalsIgnoreCase("editar")) {
			ModelAlimento alimento = gerarAlimento(request, response);
			Long tamanho = daoRefeicao.contarRefeicoesComOAlimento(alimento.getId());

			if (tamanho == 0) {
				daoAlimento.merge(alimento);
				responderAjax(response, "");
			} else {
				List<Object[]> listaInnerJoin = daoRefeicao.refeicoesAlimentosInnerJoin(alimento.getId());
				int quantidadeDeRefeicoes = listaInnerJoin.size();

				responderAjax(response, listaInnerJoin);
			}

		} else if (acao != null && acao.equalsIgnoreCase("alimentoscadastrados")) {
			int porpagina = 5;
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));

			long total = daoAlimento.contarTotalAlimentos(idLogado);
			List todos = daoAlimento.consultarTodosPaginado(idLogado, porpagina, paginaAtual);
			super.realizaPaginacao(response, todos, porpagina, total);
		}

		else if (acao != null && acao.equalsIgnoreCase("deletarId")) {
			Long id = Long.parseLong(request.getParameter("idalimento"));
			Long tamanho = daoRefeicao.contarRefeicoesComOAlimento(id);
			int porpagina = 5;

			if (tamanho == 0) {
				dao.deletarPorId(ModelAlimento.class, id);
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
			ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			ModelRefeicao ref = (ModelRefeicao) dao.consultarPorId(ModelRefeicao.class, id);
			if (macros!=null && (daoRefeicao.contarTotalRefeicoesConsumidas(idLogado, macros.getId())==Constantes.VALOR_MAXIMO_REFEICOES_CONSUMIDAS) ) {
					response.getWriter().write(Mensagem.VALOR_MAXIMO);
			}
			else {
				if (macros != null) {

					macros.adicionarRefeicao(ref);
					dao.merge(macros);

				} else {

					macros = new ModelConsumidoDia();
					macros.setUsuario((ModelUsuario) dao.consultarPorId(ModelUsuario.class, idLogado));
					macros.setData(super.editaData(data));
					macros.adicionarRefeicao(ref);
					dao.salvar(macros);

					macros = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);

				}

				ref.setId(null);
				ref.setMacros(macros);
				dao.salvar(ref);

				response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);
			}
			
			
		} else if (acao != null && acao.equalsIgnoreCase("alimentoconsumido")) {
			Long id = Long.parseLong(request.getParameter("id"));
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			ModelAlimento alimento = ((ModelAlimento) daoAlimento.consultarPorId(ModelAlimento.class, id))
					.consumir(quantidade);
			String data = request.getParameter("data");
			ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			
			boolean contem = false;
			if (macros !=null && (daoConsumido.contarTodosAlimentosConsumidos(macros.getId())==Constantes.VALOR_MAXIMO_ALIMENTOS_CONSUMIDOS)) {
				response.getWriter().write(Mensagem.VALOR_MAXIMO);
			}
			else {
				if (macros != null) {
					for (ModelAlimentoConsumido ali : macros.getListaAlimentos()) {
						System.out.println(ali.getIdAlimento() + " " + alimento.getId());
						if (ali.getIdAlimento().intValue() == alimento.getId().intValue()) {
							ali.setQuantidade(ali.getQuantidade() + quantidade);
							dao.merge(ali);
							macros.adicionarAlimento(alimento);
							daoConsumido.merge(macros);
							contem = true;
							break;
						}
					}
				}
				

				if (!contem) {
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
						dia.setUsuario((ModelUsuario) dao.consultarPorId(ModelUsuario.class, idLogado));
						dia.setData(super.editaData(data));
						dia.adicionarAlimento(alimento);
						dao.salvar(dia);
						alimentoConsumido.setMacros(dia);

					}
					dao.salvar(alimentoConsumido);
				}


				response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);
			}
			
		} else if (acao != null && acao.equalsIgnoreCase("consultarmacros")) {

			String data = request.getParameter("data");

			ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			super.responderAjax(response, macros);

		}

		else if (acao != null && acao.equalsIgnoreCase("removeralimentoconsumido")) {
			Long id = Long.parseLong(request.getParameter("id"));
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			// Alimento que será removido

			ModelAlimentoConsumido alimentoConsumido = (ModelAlimentoConsumido) daoAlimentoConsumido
					.consultarPorId(ModelAlimentoConsumido.class, id);

			alimentoConsumido.setQuantidade(alimentoConsumido.getQuantidade() - quantidade);
			if (alimentoConsumido.getQuantidade() <= 0) {
				dao.deletarPorId(ModelAlimentoConsumido.class, alimentoConsumido.getId());

			} else {
				dao.merge(alimentoConsumido);
			}
			ModelAlimento alimento = ((ModelAlimento) daoAlimento.consultarPorId(ModelAlimento.class,
					alimentoConsumido.getIdAlimento())).consumir(quantidade);
			String data = request.getParameter("data");
			ModelConsumidoDia consumoDia = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			consumoDia.retirarAlimento(alimento);
			if (consumoDia.getProteinas().doubleValue() < 0.1 && consumoDia.getCalorias().doubleValue() < 0.1
					&& consumoDia.getCarboidrato().doubleValue() < 0.1 && consumoDia.getGordura().doubleValue() < 0.1) {
				dao.deletarPorId(ModelConsumidoDia.class, consumoDia.getId());
			} else {
				dao.merge(consumoDia);

			}

			super.responderAjax(response, consumoDia);

		} else if (acao != null && acao.equalsIgnoreCase("removerrefeicaoconsumida")) {
			Long id = Long.parseLong(request.getParameter("id"));
			// Alimento que será removido

			ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, id);
			dao.deletarPorId(ModelRefeicao.class, ref.getId());

			String data = request.getParameter("data");
			ModelConsumidoDia consumoDia = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			consumoDia.removerRefeicao(ref);

			if (consumoDia.getProteinas().doubleValue() < 0.1 && consumoDia.getCalorias().doubleValue() < 0.1
					&& consumoDia.getCarboidrato().doubleValue() < 0.1 && consumoDia.getGordura().doubleValue() < 0.1) {
				dao.deletarPorId(ModelConsumidoDia.class, consumoDia.getId());
			} else {
				dao.merge(consumoDia);

			}

			super.responderAjax(response, consumoDia);

		}

		else if (acao != null && acao.equalsIgnoreCase("historico")) {
			int paginaAtual = 1;
			int porPagina = Integer.parseInt(request.getParameter("porpagina"));
			String ordenar = request.getParameter("asc");
			System.out.println(ordenar + "-------");
			String ordem = request.getParameter("ordem");
			if (request.getParameter("paginaatual") != null) {
				paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			}

			List<ModelConsumidoDia> todos = daoConsumido.consultarTodosPaginadoMacros(porPagina, paginaAtual, idLogado,
					ordem, ordenar);

			Long total = (daoConsumido.contarTotalMacros(idLogado));
			super.realizaPaginacao(response, todos, porPagina, total);

		} else if (acao != null && acao.equalsIgnoreCase("mostrargrafico")) {
			String macro = request.getParameter("macro");

			List<ModelConsumidoDia> lista = daoConsumido.consultarTodos(idLogado);

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

			super.responderAjax(response, graficoMacros);

		} else if (acao != null && acao.equalsIgnoreCase("limparmacros")) {

			String data = request.getParameter("data");

			ModelConsumidoDia consumoDia = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			if (consumoDia != null) {
				consumoDia.setCalorias(new BigDecimal(0));
				consumoDia.setProteinas(new BigDecimal(0));
				consumoDia.setCarboidrato(new BigDecimal(0));
				consumoDia.setGordura(new BigDecimal(0));
				daoConsumido.deletarAlimentoConsumidoPorId(consumoDia.getId());
				daoRefeicao.deletarRefeicoesConsumidas(consumoDia.getId());
				daoConsumido.deletarPorId(ModelConsumidoDia.class, consumoDia.getId());
				responderAjax(response, Mensagem.MENSAGEM_SUCESSO);
			} else {
				responderAjax(response, "");
			}

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
				Long total = daoRefeicao.contarTotalRefeicoesConsumidas(idLogado, macroId);

				List todos = daoConsumido.consultarRefsMacros(porPagina, paginaAtual, macroId);

				super.realizaPaginacao(response, todos, porPagina, total);

			} else {
				responderAjax(response, "");

			}

		}

		else if (acao != null && acao.equalsIgnoreCase("ImprimirRelatorioMacrosPDF")) {

			List<ModelConsumidoDia> lista = daoConsumido.consultarTodos(idLogado);

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
			System.out.println(params.get("PARAM_FOTO"));
			System.out.println(params.get("PARAM_SUB_REPORT"));

			super.relatorio(response, request, params, "rel_alimentos_jsp", lista);
		} else if (acao != null && acao.equalsIgnoreCase("novarefeicao")) {
			String nome = request.getParameter("nome");
			ModelRefeicao refeicao = new ModelRefeicao();
			Long total = daoRefeicao.contarTotalRefeicoes(idLogado);
			if (total == Constantes.VALOR_MAXIMO_REFEICOES) {
				response.getWriter().write(Mensagem.MENSAGEM_ERRO);

			} else {
				refeicao.setNome(nome);
				refeicao.setIdUserLogado(idLogado);

				dao.salvar(refeicao);
				response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);
			}

		} else if (acao != null && acao.equalsIgnoreCase("todasrefeicoes")) {
			int porPagina = Integer.parseInt(request.getParameter("porpagina"));
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			Long total = daoRefeicao.contarTotalRefeicoes(idLogado);

			List<ModelRefeicao> todos = daoRefeicao.consultarTodosRefeicaoPaginado(paginaAtual, porPagina, idLogado);

			super.realizaPaginacao(response, todos, porPagina, total);

		} else if (acao != null && acao.equalsIgnoreCase("consultarrefeicao")) {

			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
			ModelRefeicao ref = (ModelRefeicao) dao.consultarPorId(ModelRefeicao.class, idRefeicao);
			request.setAttribute("ref", ref);
			request.getRequestDispatcher("principal/consultarefeicao.jsp").forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("mediamacros")) {
			Long userLogado = super.getUserLogado(request).getId();

			List mediaMacros = daoConsumido.mediaMacros(userLogado);
			super.responderAjax(response, mediaMacros);

		}

		else if (acao != null && acao.equalsIgnoreCase("pesquisaralimentorefeicao")) {
			Long userLogado = super.getUserLogado(request).getId();

			int porPagina = Integer.parseInt(request.getParameter("porpagina"));
			int paginaAtual = Integer.parseInt(request.getParameter("paginaatual"));
			String nome = request.getParameter("nome");

			List<ModelAlimento> todos = new ArrayList<ModelAlimento>();
			Long total = 0L;
			if (nome != null || !nome.equalsIgnoreCase("null")) {
				daoAlimento.setNome(nome);

			}
			todos = daoAlimento.consultarTodosPaginado(userLogado, porPagina, paginaAtual);
			total = daoAlimento.contarTotalAlimentos(userLogado);

			super.realizaPaginacao(response, todos, porPagina, total);

		} else if (acao != null && acao.equalsIgnoreCase("adicionaralimentorefeicao")) {
			Long id = Long.parseLong(request.getParameter("id"));
			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));

			double quantidade = Double.parseDouble(request.getParameter("quantidade"));

			ModelAlimento alimento = (ModelAlimento) dao.consultarPorId(ModelAlimento.class, id);
			ModelRefeicao ref = (ModelRefeicao) dao.consultarPorId(ModelRefeicao.class, idRefeicao);
			boolean contem = false;
			for (ModelAlimentoRefeicao ali : ref.getListaAlimentos()) {
				if (ali.getAlimento().getId().intValue() == alimento.getId().intValue()) {
					ali.setQuantidade(ali.getQuantidade() + quantidade);
					dao.merge(ali);

					contem = true;
				}
			}
			ModelAlimentoRefeicao ali = new ModelAlimentoRefeicao();
			ali.setAlimento(alimento);
			ali.setQuantidade(quantidade);
			ali.setRefeicao(ref);
			if (!contem) {
				dao.salvar(ali);

			}
			ref.addAlimento(ali);
			if (ref.getDieta() != null) {
				ModelDieta dieta = ref.getDieta();
				ModelRefeicao refaux = new ModelRefeicao();
				refaux.addAlimento(ali);

				dieta.adicionarRefeicao(refaux);
				dao.merge(dieta);
			}
			dao.merge(ref);
			super.responderAjax(response, ref);

		} else if (acao != null && acao.equalsIgnoreCase("informacaodarefeicao")) {
			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
			ModelRefeicao ref = (ModelRefeicao) daoRefeicao.consultarPorId(ModelRefeicao.class, idRefeicao);
			super.responderAjax(response, ref);

		} else if (acao != null && acao.equalsIgnoreCase("alimentosrefeicao")) {
			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));

			List lista = daoRefeicao.consultarAlimentosRefeicao(idRefeicao);
			super.responderAjax(response, lista);

		} else if (acao != null && acao.equalsIgnoreCase("removeralimentorefeicao")) {
			Long idAlimento = Long.parseLong(request.getParameter("idalimento"));
			Long idRefeicao = Long.parseLong(request.getParameter("idrefeicao"));
			int quantidadeRetirar = Integer.parseInt(request.getParameter("quantidade"));
			ModelAlimentoRefeicao ali = (ModelAlimentoRefeicao) dao.consultarPorId(ModelAlimentoRefeicao.class,
					idAlimento);
			ModelRefeicao ref = (ModelRefeicao) dao.consultarPorId(ModelRefeicao.class, idRefeicao);
			if (ali != null) {
				System.out.println("Não é nulo.");
				Double quantidadeAtual = ali.getQuantidade();
				Double quantidadeNova = quantidadeAtual - quantidadeRetirar;
				ali.setQuantidade(quantidadeRetirar);

				ref.removeralimento(ali);
				if (ref.getDieta() != null) {
					ModelDieta dieta = ref.getDieta();
					dieta.removerRefeicao(ref);

					dao.merge(verificaDieta(dieta));

				}
				if (refInvalida(ref)) {
					ref.setCalorias(BigDecimal.ZERO);
					ref.setProteinas(BigDecimal.ZERO);
					ref.setCarboidratos(BigDecimal.ZERO);
					ref.setGorduras(BigDecimal.ZERO);

				}

				dao.merge(ref);
				if (quantidadeNova.intValue() <= 0) {

					dao.deletarPorId(ModelAlimentoRefeicao.class, idAlimento);

				}

				else {
					ali.setQuantidade(quantidadeNova);

					dao.merge(ali);
				}
			}

			responderAjax(response, "");

		} else if (acao != null && acao.equalsIgnoreCase("removerrefeicao")) {
			Long idrefeicao = Long.parseLong(request.getParameter("idrefeicao"));

			daoRefeicao.removerAlimentoRefeicao(idrefeicao);
			dao.deletarPorId(ModelRefeicao.class, idrefeicao);
			response.getWriter().write("");

		} else if (acao != null && acao.equalsIgnoreCase("addrefeicao")) {
			Long idRefeicao = Long.parseLong(request.getParameter("idRefeicao"));
			String data = request.getParameter("data");

			ModelConsumidoDia macros = daoConsumido.consultarConsumoDia(super.editaData(data), idLogado);
			Long total = daoRefeicao.contarTotalRefeicoesConsumidas(idLogado, macros.getId());

			if (total == Constantes.VALOR_MAXIMO_REFEICOES_CONSUMIDAS) {
				response.getWriter().write(Mensagem.VALOR_MAXIMO);

			} else {
				ModelRefeicao ref = (ModelRefeicao) dao.consultarPorId(ModelRefeicao.class, idRefeicao);
				response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);

			}

		} else if (acao != null && acao.equalsIgnoreCase("novoalimento")) {
			super.requestEncoding(request);
			Long total = daoAlimento.contarTotalAlimentos(idLogado);
			if (total == Constantes.VALOR_MAXIMO_ALIMENTOS) {
				response.getWriter().write(Mensagem.MENSAGEM_ERRO);
			} else {
				daoAlimento.salvar(gerarAlimento(request, response));
				response.getWriter().write(Mensagem.MENSAGEM_SUCESSO);
			}

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

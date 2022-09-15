package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOGeneric;
import model.ModelAlimento;
import model.ModelConsumidoDia;
import model.ModelUsuario;

/**
 * Servlet implementation class ServletAlimento
 */
@WebServlet("/ServletAlimento")
public class ServletAlimento extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DAOGeneric dao=new DAOGeneric();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAlimento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acao = request.getParameter("acao");
		String msg="";
		
		if (acao!=null && acao.equalsIgnoreCase("mostrartodosalimentospaginados")) {
			int porPagina=5;
			int paginaAtual=Integer.parseInt(request.getParameter("paginaatual"));
			Long total =(dao.contarTotal(ModelAlimento.class)) ;
			int totalPaginas=(int) (total%porPagina!=0 ? total/porPagina+1 : total/porPagina); 
			List<ModelAlimento> todos = dao.consultarTodosPaginado(ModelAlimento.class,porPagina,paginaAtual);

			request.setAttribute("todos", todos);
			request.setAttribute("totalpaginas", totalPaginas);
			request.setAttribute("paginaatual", paginaAtual);
			request.setAttribute("msg","Clique no Alimento para Editar");
	
			request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);

		}
		else if (acao!=null && acao.equalsIgnoreCase("editar")) {
			System.out.println("Editar");
			String nome = request.getParameter("nome");
			

			double porcao =Double.parseDouble(request.getParameter("porcao"));
			double caloria =Double.parseDouble(request.getParameter("caloria"));
			double proteina =Double.parseDouble(request.getParameter("proteina"));
			double carboidrato =Double.parseDouble(request.getParameter("carboidrato"));
			double gordura =Double.parseDouble(request.getParameter("gordura"));
			Long id=Long.parseLong(request.getParameter("id"));
			
			ModelAlimento modelAlimento=new ModelAlimento();		
			modelAlimento.setId(id);
			modelAlimento.setCaloria(caloria);
			modelAlimento.setCarboidrato(carboidrato);
			modelAlimento.setGordura(gordura);
			modelAlimento.setPorcao(porcao);
			modelAlimento.setNome(nome);
			modelAlimento.setProteina(proteina);

			dao.merge(modelAlimento);
			
			int porPagina=5;
			int paginaAtual=1;
			Long total =(dao.contarTotal(ModelAlimento.class)) ;
			int totalPaginas=(int) (total%porPagina!=0 ? total%porPagina : total%porPagina+1); 
			List<ModelAlimento> todos = dao.consultarTodosPaginado(ModelAlimento.class,porPagina,paginaAtual);
			
			request.setAttribute("todos", todos);
			request.setAttribute("totalpaginas", totalPaginas);
			request.setAttribute("paginaatual", paginaAtual);
			request.setAttribute("msg","Alimento Editado");

			request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);

		}else if (acao!=null && acao.equalsIgnoreCase("deletarId")) {
			Long id=Long.parseLong(request.getParameter("idalimento"));
			dao.deletarPorId(ModelAlimento.class, id);
			
			int porPagina=5;
			int paginaAtual=1;
			Long total =(dao.contarTotal(ModelAlimento.class)) ;
			int totalPaginas=(int) (total%porPagina!=0 ? total%porPagina : total%porPagina+1); 
			List<ModelAlimento> todos = dao.consultarTodosPaginado(ModelAlimento.class,porPagina,paginaAtual);
			
			request.setAttribute("todos", todos);
			request.setAttribute("totalpaginas", totalPaginas);
			request.setAttribute("paginaatual", paginaAtual);
			request.setAttribute("msg","Alimento Removido");
			
			request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);

		}else if (acao!=null && acao.equalsIgnoreCase("pesquisaralimentos")) {
			int porPagina=5;
			String nome=request.getParameter("nome");
			int paginaAtual=Integer.parseInt(request.getParameter("paginaatual"));
			List<ModelAlimento> todos=new ArrayList<ModelAlimento>();
			Long total=0L;
			if (nome!=null) {
				todos = dao.consultarNomePaginado(ModelAlimento.class,nome,porPagina,paginaAtual);
				 total =(dao.contarTotal(ModelAlimento.class,nome)) ;
			}
			else {
				todos= dao.consultarTodosPaginado(ModelAlimento.class,porPagina,paginaAtual);
				 total =(dao.contarTotal(ModelAlimento.class)) ;

			}
			
			int totalPaginas=(int) (total%porPagina!=0 ? total/porPagina+1 : total/porPagina); 

			request.setAttribute("todos", todos);
			request.setAttribute("totalpaginas", totalPaginas);
			request.setAttribute("paginaatual", paginaAtual);
			request.setAttribute("msg","Clique no Alimento para Editar");
	
			request.getRequestDispatcher("/principal/refeicoes.jsp").forward(request, response);
		}else if (acao!=null && acao.equalsIgnoreCase("alimentoconsumido")) {
			Long id=Long.parseLong(request.getParameter("id"));
			int quantidade=Integer.parseInt(request.getParameter("quantidade"));
			ModelAlimento alimento =((ModelAlimento) dao.buscarUsandoID(id,ModelAlimento.class)).consumir(quantidade);
			
			
			ModelConsumidoDia dia=new ModelConsumidoDia();
			Long idLogado=(Long)request.getSession().getAttribute("IDLogado");
			
			
			dia.setUsuario((ModelUsuario)dao.buscarUsandoID(idLogado, ModelUsuario.class));
			Date data=new Date();
			dia.setData(data);
			dia.adicionarAlimento(alimento);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String format = dateFormat.format(data);
			Date data2=null;

			try {
				data2=dateFormat.parse(format);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelConsumidoDia consumoDia = dao.consultarConsumoDia(data2,idLogado);

			
			if (consumoDia!=null) {
				System.out.println("Ja tem !!!!!");
				consumoDia.adicionarAlimento(alimento);
				dao.merge(consumoDia);
			}else {
				dao.salvar(dia);

			}
			response.getWriter().write(alimento.toString());

		} else if (acao!=null && acao.equalsIgnoreCase("removeralimentoconsumido")) {
			Long id=Long.parseLong(request.getParameter("id"));
			int quantidade=Integer.parseInt(request.getParameter("quantidade"));
			ModelAlimento alimento =((ModelAlimento) dao.buscarUsandoID(id,ModelAlimento.class)).consumir(quantidade);
			ModelConsumidoDia dia=new ModelConsumidoDia();
			Long idLogado=(Long)request.getSession().getAttribute("IDLogado");
			
			
			dia.setUsuario((ModelUsuario)dao.buscarUsandoID(idLogado, ModelUsuario.class));
			Date data=new Date();
			dia.setData(data);
			dia.adicionarAlimento(alimento);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String format = dateFormat.format(data);
			Date data2=null;

			try {
				data2=dateFormat.parse(format);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelConsumidoDia consumoDia = dao.consultarConsumoDia(data2,idLogado);
			consumoDia.retirarAlimento(alimento);
			
			dao.merge(consumoDia);
//
			response.getWriter().write("oi");

			
		}
		
		

	

	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		
		
		String nome = request.getParameter("nome");
		double porcao =Double.parseDouble(request.getParameter("porcao"));
		double caloria =Double.parseDouble(request.getParameter("caloria"));
		double proteina =Double.parseDouble(request.getParameter("proteina"));
		double carboidrato =Double.parseDouble(request.getParameter("carboidrato"));
		double gordura =Double.parseDouble(request.getParameter("gordura"));


		ModelAlimento modelAlimento=new ModelAlimento();		
		
		
		modelAlimento.setCaloria(caloria);
		modelAlimento.setCarboidrato(carboidrato);
		modelAlimento.setGordura(gordura);
		modelAlimento.setPorcao(porcao);
		modelAlimento.setNome(nome);
		modelAlimento.setProteina(proteina);
		
		
		dao.salvar(modelAlimento);
		request.setAttribute("msg","Alimento Adicionado.");

		request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);
	}

}

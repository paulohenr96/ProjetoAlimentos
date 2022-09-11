package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOGeneric;
import model.ModelAlimento;

/**
 * Servlet implementation class ServletAlimento
 */
@WebServlet("/ServletAlimento")
public class ServletAlimento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		DAOGeneric<ModelAlimento> dao=new DAOGeneric<ModelAlimento>();

		
		if (acao!=null && acao.equalsIgnoreCase("mostrartodosalimentos")) {
			
			List<ModelAlimento> todos = dao.consultarTodos(ModelAlimento.class);
			System.out.println(todos);
			request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);

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
		DAOGeneric<ModelAlimento> dao=new DAOGeneric<ModelAlimento>();
		
		
		modelAlimento.setCaloria(caloria);
		modelAlimento.setCarboidrato(carboidrato);
		modelAlimento.setGordura(gordura);
		modelAlimento.setPorcao(porcao);
		modelAlimento.setNome(nome);
		modelAlimento.setProteina(proteina);
		
		
		dao.salvar(modelAlimento);
		request.getRequestDispatcher("/principal/alimentos.jsp").forward(request, response);
	}

}

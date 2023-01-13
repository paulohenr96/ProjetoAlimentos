package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ContextoBean;
import dao.DAORefeicao;
import model.ModelRefeicao;
import model.ModelUsuario;

/**
 * Servlet implementation class ServletRefeicao
 */
@WebServlet("/ServletRefeicao")
public class ServletRefeicao extends ContextoBean {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRefeicao() {
        super();
        // TODO Auto-generated constructor stub
    }
    DAORefeicao daoRefeicao=new DAORefeicao();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acao=request.getParameter("acao");
		
		if (stringEquivalente(acao, "imprimir")) {
			
			ModelUsuario userLogado = super.getUserLogado(request);
			Long idRefeicao=Long.parseLong(request.getParameter("id"));
			ModelRefeicao refeicao = daoRefeicao.consultarPorId( idRefeicao);
			HashMap<String,Object> params= new HashMap<String, Object>();
			
			params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio")+File.separator);
			
			List<ModelRefeicao> lista = new ArrayList<ModelRefeicao>();
			lista.add(refeicao);
			super.relatorio(response, request, params, "report_refeicao", lista);
			
		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

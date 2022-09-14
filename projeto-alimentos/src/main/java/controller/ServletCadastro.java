package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOGeneric;
import model.ModelUsuario;

/**
 * Servlet implementation class ServletCadastro
 */
@WebServlet("/ServletCadastro")
public class ServletCadastro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCadastro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String nome = request.getParameter("nome");
		String sobreNome = request.getParameter("sobrenome");
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String confirmaSenha = request.getParameter("confirmasenha");
		
		
		if (!senha.equals(confirmaSenha)) {
			request.getRequestDispatcher("cadastrar.jsp").forward(request, response);
		}else {
			ModelUsuario model=new ModelUsuario();
			model.setEmail(email);
			model.setNome(nome);
			model.setSobreNome(sobreNome);
			model.setLogin(login);
			model.setSenha(senha);
			DAOGeneric<ModelUsuario> dao=new DAOGeneric<ModelUsuario>();
			dao.salvar(model);
			request.getRequestDispatcher("index.jsp").forward(request, response);

		}
		
		
		
		
		
		
		
		
		

	}

}

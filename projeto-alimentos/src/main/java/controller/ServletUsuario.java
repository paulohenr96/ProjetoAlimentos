package controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;

import bean.ContextoBean;
import dao.DAOGeneric;
import dao.DAOUsuario;
import model.ModelUsuario;
import util.Constantes;
import util.Mensagem;

/**
 * Servlet implementation class ServletUsuario
 */
@MultipartConfig
@WebServlet("/ServletUsuario")
public class ServletUsuario extends ContextoBean {
	private static final long serialVersionUID = 1L;

	private DAOGeneric dao = new DAOGeneric<>();
	private DAOUsuario daoUsuario = new DAOUsuario();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao=request.getParameter("acao");	
				
				if (acao!=null && acao.equalsIgnoreCase("removerimagem")) {
					ModelUsuario userLogado = super.getUserLogado(request);
					userLogado.setExtensaoFoto(null);
					userLogado.setFoto(null);
					
					
					setUserLogado(request, daoUsuario.merge(userLogado));
					responderAjax(response, "Imagem Removida");
				}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		ModelUsuario user = super.getUserLogado(request);

		String login = request.getParameter("login");
		String nome = request.getParameter("nome");
		String sobrenome = request.getParameter("sobrenome");
		String email = request.getParameter("email");
		String extensao = "";
		String encodeBase64String = "";
		ModelUsuario m = new ModelUsuario();

		Part part = request.getPart("imagem");
		if (Constantes.EDITAR_LOGIN) {
			m.setLogin(login);

		}else {
			m.setLogin(user.getLogin());
		}
		if (daoUsuario.contarLogin(m) == 0 || login.equals(user.getLogin())) {
			user.setNome(nome);
			user.setLogin(login);
			user.setSobreNome(sobrenome);
			user.setEmail(email);

			if (part != null && part.getSize() > 0) {
				try {
					System.out.println("Entrou");
					String[] split = part.getContentType().split("\\/");
					if (!split[0].equals("image")) {
						System.out.println("Não é imagem");

					} else {
						extensao = split[1];
						InputStream inputStream = part.getInputStream();
						encodeBase64String = Base64.encodeBase64String(inputStream.readAllBytes());
						user.setExtensaoFoto(extensao);
						user.setFoto(encodeBase64String);
						
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			
			request.getSession().setAttribute("user", daoUsuario.merge(user));
		} else {

		}

		request.getRequestDispatcher("principal/perfil.jsp").forward(request, response);

	}

}

package bean;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.ModelAlimento;
import model.ModelUsuario;

public class ContextoBean extends HttpServlet implements Serializable {
	
	
	
	public ModelUsuario getUserLogado(HttpServletRequest request) {
		
		
		return (ModelUsuario) request.getSession().getAttribute("user");
	}

	public void realizaPaginacao(HttpServletResponse response, List todos, int porPagina, Long total)  {
		// TODO Auto-generated method stub
		try {
			int totalPaginas = (int) (total % porPagina != 0 ? total / porPagina + 1 : total / porPagina);
			response.addHeader("totalPagina", "" + totalPaginas);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(todos);
			response.getWriter().write(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
			
	
	
	
}

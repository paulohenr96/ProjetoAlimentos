package filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOGeneric;
import model.ModelUsuario;
import util.JPAUtil;

/**
 * Servlet Filter implementation class FiltroLogin
 */
@WebFilter(urlPatterns = { "/principal/*","/ServletAlimento","/ServletDieta","/ServletLogin" })
public class FiltroLogin extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public FiltroLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpSession session = servletRequest.getSession();
		String urlParaAutentificar = servletRequest.getServletPath();
		// pass the request along the filter chain
		 Object attribute = session.getAttribute("user");
		 if (attribute==null && !urlParaAutentificar.contains("/ServletLogin") && !urlParaAutentificar.contains("/registrar.jsp") ) {
			
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		else {
			
			
			
			chain.doFilter(request, response);

		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

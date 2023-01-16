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

/**
 * Servlet Filter implementation class FiltroLogin
 */
@WebFilter(urlPatterns = { "/principal/*","/ServletAlimento","/ServletDieta" })
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
		 if (attribute==null && !urlParaAutentificar.contains("/ServletLogin") && !urlParaAutentificar.contains("/registrar.jsp")) {
			 System.out.println(urlParaAutentificar+"-----------");
			request.getRequestDispatcher("/index.jsp?url="+urlParaAutentificar).forward(request, response);
			return;
		}
		else {
			HttpServletResponse httpres = (HttpServletResponse) response;
			httpres.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			httpres.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			httpres.setDateHeader("Expires", 0); // Proxies.
			
			
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

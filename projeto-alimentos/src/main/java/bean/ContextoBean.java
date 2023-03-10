package bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.ModelAlimento;
import model.ModelConsumidoDia;
import model.ModelDieta;
import model.ModelRefeicao;
import model.ModelUsuario;
import util.ReportUtil;

public class ContextoBean extends HttpServlet implements Serializable {
	private static final String USUARIO_LOGADO = "user";
	private static final String TOTAL_PAGINAS = "totalPagina";

	public ModelUsuario getUserLogado(HttpServletRequest request) {

		return (ModelUsuario) request.getSession().getAttribute(USUARIO_LOGADO);
	}

	public void setUserLogado(HttpServletRequest request,ModelUsuario user) {
		
		request.getSession().setAttribute(USUARIO_LOGADO, user);
		
	}
	public ContextoBean() {
		// TODO Auto-generated constructor stub
		System.out.println("Contexto Bean");

	}

	public void realizaPaginacao(HttpServletResponse response, List todos, int porPagina, Long total) {
		// TODO Auto-generated method stub
		try {
			int totalPaginas = (int) (total % porPagina != 0 ? total / porPagina + 1 : total / porPagina);
			response.addHeader(TOTAL_PAGINAS, "" + totalPaginas);
			response.setCharacterEncoding("UTF-8");
			responderAjax(response, todos);
			todos=null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void requestEncoding(HttpServletRequest request) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void responderAjax(HttpServletResponse response, Object o) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json="";
			json = mapper.writeValueAsString(o);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void relatorio(HttpServletResponse response, HttpServletRequest request, HashMap<String, Object> params,
			String nomejasper, List lista) {
		// TODO Auto-generated method stub

		try {
			byte[] relatorio = new ReportUtil().geraRelatorioPdf(lista, nomejasper, params,
					request.getServletContext());
			response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
			response.getOutputStream().write(relatorio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date editaData(String format) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//		String format = dateFormat.format(data);
		Date data2 = null;
		try {
			data2 = dateFormat.parse(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data2;
	}

	public String tratamentoImagem(Part part, String extensao, String encodeBase64String) {
		try {
			String[] split = part.getContentType().split("\\/");
			if (!split[0].equals("image")) {
				System.out.println("Não é imagem");

			} else {
				extensao = split[1];
				InputStream inputStream = part.getInputStream();
				encodeBase64String = Base64.encodeBase64String(inputStream.readAllBytes());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return encodeBase64String;
	}
	
	public boolean stringVazia(String str) {
		return str==null || str.equals("") || str.isEmpty();
	}
	public boolean stringEquivalente(String str, String str2) {
		
		return (!stringVazia(str)) && str.equalsIgnoreCase(str2);
	}
	public  boolean valorInvalido(BigDecimal valor) {
		return (valor.compareTo(BigDecimal.ZERO)==-1) ;
		
	}
	
	public boolean refInvalida(ModelRefeicao ref) {
		return (valorInvalido(ref.getCalorias()) || valorInvalido(ref.getProteinas())
				|| valorInvalido(ref.getCarboidratos())|| valorInvalido(ref.getGorduras()));
			
	
	}
	public boolean dietaInvalida(ModelDieta dieta) {
		return (valorInvalido(dieta.getTotalCalorias()) || valorInvalido(dieta.getTotalProteinas())
				|| valorInvalido(dieta.getTotalCarboidratos())|| valorInvalido(dieta.getTotalGorduras()));
			
	
	}
	public ModelDieta verificaDieta(ModelDieta dieta) {
		if (dietaInvalida(dieta) || dieta.getListaRefeicoes().size()==1) {
			dieta.setTotalCalorias(BigDecimal.ZERO);
			dieta.setTotalProteinas(BigDecimal.ZERO);
			dieta.setTotalCarboidratos(BigDecimal.ZERO);
			dieta.setTotalGorduras(BigDecimal.ZERO);
		}
		return dieta;
	}
}

package util;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
public class ReportUtil implements Serializable
{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	
public byte[] geraRelatorioPdf(List listaDados,String nomeRelatorio,HashMap<String,Object> params,ServletContext servletContext) throws Exception {
		
		JRBeanCollectionDataSource dataSource=  new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper=servletContext.getRealPath("relatorio")+File.separator+nomeRelatorio+".jasper";
		
		JasperPrint impressora=JasperFillManager.fillReport(caminhoJasper, params, dataSource);
		
		return JasperExportManager.exportReportToPdf(impressora);
	}
	public byte[] geraRelatorioPdf(List listaDados,String nomeRelatorio,ServletContext servletContext) throws Exception {
		
		JRBeanCollectionDataSource dataSource=  new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper=servletContext.getRealPath("relatorio")+File.separator+nomeRelatorio+".jasper";
		
		JasperPrint impressora=JasperFillManager.fillReport(caminhoJasper, new HashMap(), dataSource);
		
		return JasperExportManager.exportReportToPdf(impressora);
	}
}
